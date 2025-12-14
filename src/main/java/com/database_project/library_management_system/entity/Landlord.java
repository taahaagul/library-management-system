package com.database_project.library_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Landlord {

    private Integer landlordId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
