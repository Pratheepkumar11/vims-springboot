package com.example.vims.controller;


import com.example.vims.controller.VehicleApi;
import com.example.vims.dto.VehicleRequestDto;
import com.example.vims.dto.VehicleResponseDto;
import com.example.vims.exception.VehicleNotFoundException;
import com.example.vims.model.Vehicle;
import com.example.vims.repo.VehicleRepo;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.vims.service.VehicleService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(VehicleApi.class)
@ActiveProfiles("test")
public class VehicleApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VehicleService vehicleService;

    @Autowired
    ObjectMapper mapper;

    Vehicle RECORD_1 = new Vehicle(1,"Rayven Yor", "23", "Cebu Philippines");
    Vehicle RECORD_2 = new Vehicle(2, "David Landup", "27", "New York USA");
    Vehicle RECORD_3 = new Vehicle(3, "Jane Doe", "31", "New York USA");

    @Test
    public void testgetAllDetailsShouldReturn200OK() throws Exception {
        List<Vehicle> vehicles = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        Mockito.when(vehicleService.getAllVehicle()).thenReturn(vehicles);
        String requestBody = mapper.writeValueAsString(vehicles);
        mockMvc.perform(get("/api/v1/vehicles").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testgetAllDetailsShouldReturnNotFound() throws Exception {
        vehicleService.getAllVehicle();
        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testGetShouldReturn200OK() throws Exception {

        Vehicle vehicle = new Vehicle(1,"fgdf","fgdf","2");
        Mockito.when(vehicleService.getVehicleById(1)).thenReturn(vehicle);
        mockMvc.perform(get("/api/v1/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("vehicleMake", Matchers.is("fgdf")))
                .andDo(print());
    }

    @Test
    public void testGetShouldReturn4NotFound() throws Exception {
        Mockito.when(vehicleService.getVehicleById(2)).thenThrow(VehicleNotFoundException.class);
        mockMvc.perform(get("/api/v1/vehicles/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }



    @Test
    public void testAddShouldReturn201Created() throws Exception {
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("fgdf","fgdf","2456");
        Mockito.when(vehicleService.saveVehicle(Mockito.any(VehicleRequestDto.class))).thenReturn("Inserted successfully in DB");
        String requestBody = mapper.writeValueAsString(vehicleRequestDto);
        mockMvc.perform(post("/api/v1/vehicles").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testAddShouldReturn500Created() throws Exception {
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto("fgdf","fg","2");
        Mockito.when(vehicleService.saveVehicle(vehicleRequestDto)).thenReturn("Inserted successfully in DB");
        String requestBody = mapper.writeValueAsString(vehicleRequestDto);
        mockMvc.perform(post("/api/v1/vehicles").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andDo(print());

    }

    @Test
    public void testUpdateShouldReturn200OK() throws Exception {
        Vehicle vehicle = new Vehicle(1,"fgdf","rthf","2456");
        Mockito.when(vehicleService.updateVehicle(Mockito.any(Integer.class),Mockito.any(Vehicle.class))).thenReturn(vehicle);
        String requestBody = mapper.writeValueAsString(vehicle);
        mockMvc.perform(put("/api/v1/vehicles/1").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vehicleMake", is("fgdf")))
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Vehicle vehicle = new Vehicle(2,"fgdf","rthf","2456");
        Mockito.when(vehicleService.updateVehicle(2,vehicle)).thenThrow(VehicleNotFoundException.class);
        String requestBody = mapper.writeValueAsString(vehicle);
        mockMvc.perform(put("/api/v1/vehicles/1").contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn200OK() throws Exception {

        VehicleResponseDto vehicleResponseDto = new VehicleResponseDto(1,"fgdf","fgdf","2");
        Mockito.when(vehicleService.deleteVehicle(1)).thenReturn(vehicleResponseDto);
        mockMvc.perform(delete("/api/v1/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("vehicleMake", Matchers.is("fgdf")))
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn400OK() throws Exception {
        Mockito.when(vehicleService.deleteVehicle(2)).thenThrow(VehicleNotFoundException.class);
        mockMvc.perform(get("/api/v1/vehicles/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
