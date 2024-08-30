package org.novaride.novaridebookingservice.service;

import org.novaride.modelentity.models.Booking;
import org.novaride.modelentity.models.BookingStatus;
import org.novaride.modelentity.models.Driver;
import org.novaride.modelentity.models.Passenger;
import org.novaride.novaridebookingservice.dto.CreateBookingRequestDto;
import org.novaride.novaridebookingservice.dto.CreateBookingResponseDto;
import org.novaride.novaridebookingservice.dto.DriverLocationDto;
import org.novaride.novaridebookingservice.dto.NearByDriverRequestDto;
import org.novaride.novaridebookingservice.repository.BookingRepository;
import org.novaride.novaridebookingservice.repository.PassengerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    PassengerRepository passengerRepository;
    BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private static final String LOCATION_SERVICE="http://localhost:7477";

    public BookingServiceImpl(PassengerRepository passengerRepository, BookingRepository bookingRepository) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
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

        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request, DriverLocationDto[].class);

        if(result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
            });
        }

        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();

    }
}
