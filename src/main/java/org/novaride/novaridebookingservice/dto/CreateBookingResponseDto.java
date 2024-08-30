package org.novaride.novaridebookingservice.dto;

import lombok.*;
import org.novaride.modelentity.models.Driver;


import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponseDto {
    private Long bookingId;

    private String bookingStatus;

    private Optional<Driver>driver;
}
