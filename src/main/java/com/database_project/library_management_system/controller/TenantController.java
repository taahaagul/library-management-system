package com.database_project.library_management_system.controller;

import com.database_project.library_management_system.entity.Tenant;
import com.database_project.library_management_system.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody Tenant tenant) {
        Tenant savedTenant = tenantService.save(tenant);
        return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(@PathVariable int id) {
        Tenant tenant = tenantService.findById(id);

        if (tenant != null) {
            return ResponseEntity.ok(tenant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tenant> updateTenant(@PathVariable int id, @RequestBody Tenant tenant) {
        tenant.setTenantId(id);

        try {
            Tenant updatedTenant = tenantService.update(tenant);
            return ResponseEntity.ok(updatedTenant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable int id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
