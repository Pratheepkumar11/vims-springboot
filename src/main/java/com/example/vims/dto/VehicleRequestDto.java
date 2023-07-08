package com.example.vims.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VehicleRequestDto {

    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;


}
