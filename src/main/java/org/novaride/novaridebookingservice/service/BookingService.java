package org.novaride.novaridebookingservice.service;

import org.novaride.modelentity.models.Booking;
import org.novaride.novaridebookingservice.dto.CreateBookingRequestDto;
import org.novaride.novaridebookingservice.dto.CreateBookingResponseDto;

public interface BookingService {
    public CreateBookingResponseDto createBooking(CreateBookingRequestDto booking);
}
