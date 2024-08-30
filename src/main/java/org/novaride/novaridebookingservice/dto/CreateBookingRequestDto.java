package org.novaride.novaridebookingservice.dto;

import lombok.*;
import org.novaride.modelentity.models.ExactLocation;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequestDto {
    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;
}
