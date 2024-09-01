package org.novaride.novaridebookingservice.controllers;

import org.novaride.novaridebookingservice.dto.CreateBookingRequestDto;
import org.novaride.novaridebookingservice.dto.CreateBookingResponseDto;
import org.novaride.novaridebookingservice.dto.UpdateDriverRequestDto;
import org.novaride.novaridebookingservice.dto.UpdateDriverResponseDto;
import org.novaride.novaridebookingservice.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingRequestDto createBookingRequestDto) {
        return new ResponseEntity<>(bookingService.createBooking(createBookingRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public  ResponseEntity<UpdateDriverResponseDto>updateReview(@RequestBody UpdateDriverRequestDto updateDriverRequestDto, @PathVariable Long bookingId ){
        return new ResponseEntity<UpdateDriverResponseDto>(bookingService.updateDriver(updateDriverRequestDto, bookingId), HttpStatus.OK);
    }
}
