package com.database_project.library_management_system.controller;

import com.database_project.library_management_system.entity.House;
import com.database_project.library_management_system.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping
    public ResponseEntity<?> createHouse(@RequestBody House house) {
        try {
            House savedHouse = houseService.save(house);
            return new ResponseEntity<>(savedHouse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<House> getAllHouses() {
        return houseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getHouseById(@PathVariable int id) {
        House house = houseService.findById(id);

        if (house != null) {
            return ResponseEntity.ok(house);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable int id, @RequestBody House house) {
        house.setHouseId(id);

        try {
            House updatedHouse = houseService.update(house);
            return ResponseEntity.ok(updatedHouse);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("valid for update")) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable int id) {

        houseService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
