package com.database_project.library_management_system.controller;

import com.database_project.library_management_system.entity.Lease;
import com.database_project.library_management_system.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final RentalService rentalService;

    public LeaseController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/start")
    public ResponseEntity<?> startLease(@RequestBody Lease lease) {
        try {
            Lease startedLease = rentalService.startNewLease(lease);
            return new ResponseEntity<>(startedLease, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/terminate/{id}")
    public ResponseEntity<Lease> terminateLease(@PathVariable int id) {
        try {
            Lease terminatedLease = rentalService.terminateLease(id);
            return ResponseEntity.ok(terminatedLease);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Lease> getAllLeases() {
        return rentalService.findAllLeases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lease> getLeaseById(@PathVariable int id) {
        Lease lease = rentalService.findLeaseById(id);

        if (lease != null) {
            return ResponseEntity.ok(lease);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
