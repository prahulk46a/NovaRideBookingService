package org.novaride.novaridebookingservice.service;

import org.novaride.modelentity.models.Booking;
import org.novaride.modelentity.models.BookingStatus;
import org.novaride.modelentity.models.Driver;
import org.novaride.modelentity.models.Passenger;
import org.novaride.novaridebookingservice.api.LocationServiceApi;
import org.novaride.novaridebookingservice.api.SocketApi;
import org.novaride.novaridebookingservice.dto.*;
import org.novaride.novaridebookingservice.repository.BookingRepository;
import org.novaride.novaridebookingservice.repository.PassengerRepository;
import org.novaride.novaridebookingservice.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    private final DriverRepository driverRepository;
    PassengerRepository passengerRepository;
    BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    private  final LocationServiceApi locationServiceApi;

    private final SocketApi socketApi;

    //This will be handled by service discovery
    //private static final String LOCATION_SERVICE="http://localhost:7477";


    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository, LocationServiceApi locationServiceApi, DriverRepository driverRepository, SocketApi socketApi) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
        this.driverRepository = driverRepository;
        this.socketApi=socketApi;
    }


    @Override
    public CreateBookingResponseDto createBooking(CreateBookingRequestDto bookingDetails) {
        //Firstly Booking will be created
        Optional<Passenger> passenger = passengerRepository.findById(bookingDetails.getPassengerId());
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation())
//                .endLocation(bookingDetails.getEndLocation())
                .passenger(passenger.get())
                .build();
        Booking newBooking = bookingRepository.save(booking);

        // make an api call to location service to fetch nearby drivers

        NearByDriverRequestDto request = NearByDriverRequestDto.builder()
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .build();

        processNearbyDriversAsync(request,bookingDetails.getPassengerId(),newBooking.getId());
        //ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request, DriverLocationDto[].class);

//        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
//            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
//            driverLocations.forEach(driverLocationDto -> {
//                System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
//            });
//        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();



    }

    @Override
    public UpdateBookingResponseDto updateDriver(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId) {
        System.out.println(updateBookingRequestDto.getDriverId().get());
        Optional<Driver> driver = driverRepository.findById(updateBookingRequestDto.getDriverId().get());
        System.out.println(driver.isPresent());
        bookingRepository.updateBookingStatusAndDriverById(bookingId, BookingStatus.SCHEDULED,driver.get());
        Optional<Booking>booking= bookingRepository.findById(bookingId);

        return UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .status(booking.get().getBookingStatus())
                .build();
    }

    private void processNearbyDriversAsync(NearByDriverRequestDto requestDto,Long passengerId, Long bookingId) {
        Call<DriverLocationDto[]> call = locationServiceApi.getNearByDriver(requestDto);
        //Async way to handle rest api call using retrofit
        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                    //here we will get all nearby drivers from location service and then after that
                    //we will send request to all drivers whether they want to accept request.
                    //for that we will make async call to socket service

                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
                    });

                    try {
                        //async call to socket service
                        raiseRideRequestAsync(RideRequestDto.builder().passengerId(passengerId).bookingId(bookingId).build());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    System.out.println("Request failed" + response.message());
                }


            }


            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //async call to socket service to confirm booking from drivers end and assign driver
    private void raiseRideRequestAsync(RideRequestDto requestDto) throws IOException {
        Call<Boolean> call = socketApi.raiseRideRequest(requestDto);
        //Async way to handle rest api call using retrofit
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.isSuccessful());
                System.out.println(response.message());
                if (response.isSuccessful() && response.body() != null) {
                    Boolean result = response.body();
                    //here when response is true that means driver accept request then that response should be updated in booking table
                    //for that we will async/sync call to booking service
                    System.out.println("Driver response is" + result.toString());

                } else {
                    System.out.println("Request for ride failed" + response.message());
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
