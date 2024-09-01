package org.novaride.novaridebookingservice.dto;

import lombok.*;
import org.novaride.modelentity.models.BookingStatus;
import org.novaride.modelentity.models.Driver;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDriverResponseDto {
    private Long bookingId;
    private BookingStatus status;
    private Optional<Driver> driver;
}
