package com.example.vims.service;

import com.example.vims.dto.VehicleRequestDto;
import com.example.vims.dto.VehicleResponseDto;
import com.example.vims.model.Vehicle;
import com.example.vims.repo.VehicleRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepo vehicleRepo;
    @InjectMocks
    private VehicleServiceImpl vehicleService;

    Vehicle vehicle;

    VehicleRequestDto vehicleRequestDto;

    VehicleResponseDto vehicleResponseDto;

    @BeforeEach
    public void setup(){
         vehicle = Vehicle.builder()
                .vehicleId(1)
                .vehicleMake("Ramesh")
                .vehicleModel("Fadatare")
                .vehicleYear("ramesh@gmail.com")
                .build();
        vehicleRequestDto = VehicleRequestDto.builder()
                .vehicleMake("Ramesh")
                .vehicleModel("Fadatare")
                .vehicleYear("ramesh@gmail.com")
                .build();
        vehicleResponseDto = VehicleResponseDto.builder()
                .vehicleId(1)
                .vehicleMake("Ramesh")
                .vehicleModel("Fadatare")
                .vehicleYear("ramesh@gmail.com")
                .build();
    }

    @Test
    void getAllVehicle()
    {
        given(vehicleRepo.findAll()).willReturn(List.of(vehicle,vehicle));
        List<Vehicle> employeeList =vehicleService.getAllVehicle();
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    @Test
    void getVehicle()
    {
        when(vehicleRepo.findById(1)).thenReturn(Optional.ofNullable(vehicle));
        Vehicle vehicle1 = vehicleService.getVehicleById(1);
        Assertions.assertThat(vehicle1).isNotNull();
    }

    @Test
    void saveVehicle()
    {
        String vehicle1 = vehicleService.saveVehicle(vehicleRequestDto);
        Assertions.assertThat(vehicle1).isNotNull();
    }

    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        vehicleRepo.save(vehicle);
        when(vehicleRepo.findById(1)).thenReturn(Optional.ofNullable(vehicle));
        vehicleService.deleteVehicle(vehicle.getVehicleId());
        Optional<Vehicle> vehicle2 = vehicleRepo.findById(vehicle.getVehicleId());
        assertThat(vehicle2).isNotEmpty();

    }

    @Test
    void UpdateVehicle()
    {
        when(vehicleRepo.findById(1)).thenReturn(Optional.ofNullable(vehicle));
        when(vehicleRepo.save(vehicle)).thenReturn(vehicle);
        Vehicle updateReturn = vehicleService.updateVehicle(1,vehicle);
        assertThat(updateReturn.getVehicleMake()).isEqualTo("Ramesh");
        Assertions.assertThat(updateReturn).isNotNull();
    }










}