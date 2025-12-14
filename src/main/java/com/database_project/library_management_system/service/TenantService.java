package com.database_project.library_management_system.service;

import com.database_project.library_management_system.entity.Tenant;
import com.database_project.library_management_system.repository.TenantDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantDAO tenantDAO;

    public TenantService(TenantDAO tenantDAO) {
        this.tenantDAO = tenantDAO;
    }

    public Tenant save(Tenant tenant) {
        tenantDAO.save(tenant);
        return tenant;
    }

    public Tenant findById(int id) {
        return tenantDAO.findById(id);
    }

    public List<Tenant> findAll() {
        return tenantDAO.findAll();
    }

    public Tenant update(Tenant tenant) {
        if (tenant.getTenantId() == null || tenantDAO.findById(tenant.getTenantId()) == null) {

            throw new IllegalArgumentException("Tenant ID must be valid for update.");
        }

        tenantDAO.update(tenant);
        return tenant;
    }

    public void delete(int id) {
        tenantDAO.delete(id);
    }
}
