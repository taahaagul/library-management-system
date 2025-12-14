package com.database_project.library_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lease {

    private Integer leaseId;
    private Integer houseId;
    private Integer tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal depositAmount;
    private String status;
}
