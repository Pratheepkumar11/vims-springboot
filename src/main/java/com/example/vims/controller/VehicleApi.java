package com.example.vims.controller;

import com.example.vims.dto.VehicleRequestDto;
import com.example.vims.dto.VehicleResponseDto;
import com.example.vims.model.Vehicle;
import com.example.vims.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/v1")

public class VehicleApi {

    @Autowired
    VehicleService vehicleService;


    @Autowired
    Environment environment;

    //http://localhost:8090/api/v1/vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getVehicles(){
        List<Vehicle> vehicleList = vehicleService.getAllVehicle();
        if(!vehicleList.isEmpty()){
            return new ResponseEntity<>(vehicleList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(vehicleList,HttpStatus.NO_CONTENT);
        }
    }
    //http://localhost:8090/api/v1/vehicles
    @PostMapping("/vehicles")
    public ResponseEntity<String> addVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto) {
        //Inserted successfully
        String msg = vehicleService.saveVehicle(vehicleRequestDto);
        if (null != msg && msg.contains("Inserted successfully")) {
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8090/api/v1/vehicles/id
    @PutMapping("vehicles/{id}")
    public ResponseEntity<Vehicle> updateVehicleById(@PathVariable int id,@Valid @RequestBody Vehicle vehicle){
        Vehicle vehicleFromDB = vehicleService.updateVehicle(id,vehicle);
        if(null!=vehicleFromDB){
            return  new ResponseEntity<>(vehicleFromDB,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //http://localhost:8090/api/v1/vehicles/id
    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int id){

        Vehicle findedVehicle =  vehicleService.getVehicleById(id);
        if(null!=findedVehicle){
            return new ResponseEntity<>(findedVehicle, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //http://localhost:8090/api/v1/vehicles/id
    @DeleteMapping("/vehicles/{id}")
    public VehicleResponseDto deleteById(@PathVariable int id) {
        return  vehicleService.deleteVehicle(id);

    }

}
