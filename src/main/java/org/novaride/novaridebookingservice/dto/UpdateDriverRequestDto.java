package org.novaride.novaridebookingservice.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDriverRequestDto {
    private String status;
    private Optional<Long> driverId;
}
