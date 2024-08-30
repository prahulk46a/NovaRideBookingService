package org.novaride.novaridebookingservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearByDriverRequestDto {
    Double latitude;
    Double longitude;
}
