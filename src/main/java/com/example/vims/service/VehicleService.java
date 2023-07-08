package com.example.vims.service;

import com.example.vims.dto.VehicleRequestDto;
import com.example.vims.dto.VehicleResponseDto;
import com.example.vims.exception.VehicleNotFoundException;
import com.example.vims.model.Vehicle;

import java.util.List;

public interface VehicleService {

    List<Vehicle> getAllVehicle();
    String saveVehicle(VehicleRequestDto vehicleRequestDto);
    Vehicle getVehicleById(int id)throws VehicleNotFoundException;
    Vehicle updateVehicle(int id, Vehicle vehicle)throws VehicleNotFoundException;
    VehicleResponseDto deleteVehicle(int id);
}
