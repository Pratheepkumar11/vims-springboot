package com.example.vims.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "vehicles", schema = "public")

public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int vehicleId;

    @Column(name="make")
    @NotBlank(message = "{make.name.blank}")
    @Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*" ,message="{make.name.valid}")
    String vehicleMake;

    @Column(name="model")
    @NotBlank(message = "{model.name.blank}")
    @Pattern(regexp = "[A-Za-z]+( [A-Za-z]+)*" ,message="{model.name.valid}")
    String vehicleModel;

    @Column(name="year")
    @NotNull(message = "{year.name.blank}")
    @Pattern(regexp = "^\\d{4}$" ,message="{year.name.valid}")
    String vehicleYear;


}
