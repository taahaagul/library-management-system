package com.database_project.library_management_system.service;

import com.database_project.library_management_system.entity.House;
import com.database_project.library_management_system.entity.Lease;
import com.database_project.library_management_system.entity.Tenant;
import com.database_project.library_management_system.repository.HouseDAO;
import com.database_project.library_management_system.repository.LeaseDAO;
import com.database_project.library_management_system.repository.TenantDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final HouseDAO houseDAO;
    private final TenantDAO tenantDAO;
    private final LeaseDAO leaseDAO;

    public RentalService(HouseDAO houseDAO, TenantDAO tenantDAO, LeaseDAO leaseDAO) {
        this.houseDAO = houseDAO;
        this.tenantDAO = tenantDAO;
        this.leaseDAO = leaseDAO;
    }

    @Transactional
    public Lease startNewLease(Lease newLease) {
        if (newLease.getHouseId() == null || newLease.getTenantId() == null ||
                newLease.getStartDate() == null || newLease.getEndDate() == null) {
            throw new IllegalArgumentException("Lease must contain House ID, Tenant ID, Start Date, and End Date.");
        }

        House house = houseDAO.findById(newLease.getHouseId());
        Tenant tenant = tenantDAO.findById(newLease.getTenantId());

        if (house == null) {
            throw new IllegalArgumentException("House with ID " + newLease.getHouseId() + " not found.");
        }
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant with ID " + newLease.getTenantId() + " not found.");
        }

        if (!house.getIsAvailable()) {
            Lease activeLease = leaseDAO.findActiveLeaseByHouseId(newLease.getHouseId());
            if (activeLease != null) {
                throw new IllegalStateException("House is currently occupied with an active lease (ID: " + activeLease.getLeaseId() + ").");
            }
        }

        if (newLease.getStartDate().isBefore(LocalDate.now()) || newLease.getEndDate().isBefore(newLease.getStartDate())) {
            throw new IllegalArgumentException("Start date cannot be in the past, and End date must be after Start date.");
        }

        newLease.setStatus("ACTIVE");
        leaseDAO.save(newLease);

        house.setIsAvailable(false);
        houseDAO.update(house);

        return newLease;
    }

    @Transactional
    public Lease terminateLease(int leaseId) {
        Lease lease = leaseDAO.findById(leaseId);

        if (lease == null) {
            throw new IllegalArgumentException("Lease with ID " + leaseId + " not found.");
        }

        if (lease.getStatus().equals("EXPIRED")) {
            throw new IllegalStateException("Lease is already expired.");
        }

        lease.setStatus("EXPIRED");
        leaseDAO.update(lease);

        House house = houseDAO.findById(lease.getHouseId());
        if (house != null) {
            house.setIsAvailable(true);
            houseDAO.update(house);
        }

        return lease;
    }

    public Lease findLeaseById(int id) {
        return leaseDAO.findById(id);
    }

    public List<Lease> findAllLeases() {
        return leaseDAO.findAll();
    }
}
