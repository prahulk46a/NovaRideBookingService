package org.novaride.novaridebookingservice.service;

import org.novaride.novaridebookingservice.dto.CreateBookingRequestDto;
import org.novaride.novaridebookingservice.dto.CreateBookingResponseDto;
import org.novaride.novaridebookingservice.dto.UpdateBookingRequestDto;
import org.novaride.novaridebookingservice.dto.UpdateBookingResponseDto;

public interface BookingService {
    public CreateBookingResponseDto createBooking(CreateBookingRequestDto booking);

    public UpdateBookingResponseDto updateDriver(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId);
}
