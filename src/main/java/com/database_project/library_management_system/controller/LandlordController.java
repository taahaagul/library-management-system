package com.database_project.library_management_system.controller;

import com.database_project.library_management_system.entity.Landlord;
import com.database_project.library_management_system.service.LandlordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/landlords")
public class LandlordController {

    private final LandlordService landlordService;

    public LandlordController(LandlordService landlordService) {
        this.landlordService = landlordService;
    }

    @PostMapping
    public ResponseEntity<Landlord> createLandlord(@RequestBody Landlord landlord) {
        Landlord savedLandlord = landlordService.save(landlord);

        return new ResponseEntity<>(savedLandlord, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Landlord> getAllLandlords() {

        return landlordService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Landlord> getLandlordById(@PathVariable int id) {
        Landlord landlord = landlordService.findById(id);

        if (landlord != null) {
            return ResponseEntity.ok(landlord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Landlord> updateLandlord(@PathVariable int id, @RequestBody Landlord landlord) {
        landlord.setLandlordId(id);

        try {
            Landlord updatedLandlord = landlordService.update(landlord);
            return ResponseEntity.ok(updatedLandlord);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable int id) {
        landlordService.delete(id);

        return ResponseEntity.noContent().build();
    }
}