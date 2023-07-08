package com.example.vims.service;

import com.example.vims.dto.VehicleRequestDto;
import com.example.vims.dto.VehicleResponseDto;
import com.example.vims.exception.VehicleNotFoundException;
import com.example.vims.model.Vehicle;
import com.example.vims.repo.VehicleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    VehicleRepo vehicleRepo;
    @Override
    public List<Vehicle> getAllVehicle() {
        return vehicleRepo.findAll();
    }

    @Override
    public String saveVehicle(VehicleRequestDto vehicleRequestDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleMake(vehicleRequestDto.getVehicleMake());
        vehicle.setVehicleModel(vehicleRequestDto.getVehicleModel());
        vehicle.setVehicleYear(vehicleRequestDto.getVehicleYear());
        Vehicle vehicleFromDb =  vehicleRepo.save(vehicle);
        return "Inserted successfully in DB";
    }

    @Override
    public Vehicle getVehicleById(int id) throws VehicleNotFoundException {
        return vehicleRepo.findById(id).orElseThrow(()->new VehicleNotFoundException("VEHICLE_NOT_FOUND"));
    }

    @Override
    public Vehicle updateVehicle(int id, Vehicle vehicle) throws VehicleNotFoundException {
        Vehicle vehicleFromDb = vehicleRepo.findById(id).orElseThrow(()->new VehicleNotFoundException("VEHICLE_NOT_FOUND"));
            vehicleFromDb.setVehicleMake(vehicle.getVehicleMake());
            vehicleFromDb.setVehicleModel(vehicle.getVehicleModel());
            vehicleFromDb.setVehicleYear(vehicle.getVehicleYear());
            vehicleRepo.save(vehicleFromDb);
            return vehicleFromDb;
    }

    @Override
    public VehicleResponseDto deleteVehicle(int id) {
        Vehicle vehicleFromDb = vehicleRepo.findById(id).orElseThrow(()->new VehicleNotFoundException("VEHICLE_NOT_FOUND"));
        vehicleRepo.delete(vehicleFromDb);
        return new VehicleResponseDto(vehicleFromDb);
    }
}
