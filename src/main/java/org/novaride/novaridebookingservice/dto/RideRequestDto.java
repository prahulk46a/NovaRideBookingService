package org.novaride.novaridebookingservice.dto;

import lombok.*;
import org.novaride.modelentity.models.ExactLocation;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideRequestDto {
    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;

    private List<Long>driverIds;//Some of the response of booking should pass to update those booking details

    private Long bookingId;
}
