package com.database_project.library_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {

    private Integer houseId;
    private Integer landlordId;
    private String address;
    private String city;
    private String zipCode;
    private BigDecimal monthlyRent;
    private Integer numberOfRooms;
    private Boolean isAvailable;
    private String description;
}
