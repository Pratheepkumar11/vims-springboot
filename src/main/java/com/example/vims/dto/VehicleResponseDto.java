package com.example.vims.dto;

import com.example.vims.model.Vehicle;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class VehicleResponseDto {

    private int vehicleId;
    private String vehicleMake;
    private String vehicleModel;
    private String vehicleYear;

    public VehicleResponseDto(Vehicle vehicle) {
        this.setVehicleId(vehicle.getVehicleId());
        this.setVehicleMake(vehicle.getVehicleMake());
        this.setVehicleModel(vehicle.getVehicleModel());
        this.setVehicleYear(vehicle.getVehicleYear());
    }


}
