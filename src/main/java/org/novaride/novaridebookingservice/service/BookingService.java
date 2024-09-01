package org.novaride.novaridebookingservice.service;

import org.novaride.modelentity.models.Booking;
import org.novaride.novaridebookingservice.dto.CreateBookingRequestDto;
import org.novaride.novaridebookingservice.dto.CreateBookingResponseDto;
import org.novaride.novaridebookingservice.dto.UpdateDriverRequestDto;
import org.novaride.novaridebookingservice.dto.UpdateDriverResponseDto;

public interface BookingService {
    public CreateBookingResponseDto createBooking(CreateBookingRequestDto booking);

    public UpdateDriverResponseDto updateDriver(UpdateDriverRequestDto updateDriverRequestDto,Long bookingId);
}
