package org.novaride.novaridebookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverLocationDto {
    String driverId;
    Double longitude;
    Double latitude;
}
