package com.database_project.library_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    private Integer tenantId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
