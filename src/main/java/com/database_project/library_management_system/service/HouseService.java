package com.database_project.library_management_system.service;

import com.database_project.library_management_system.entity.House;
import com.database_project.library_management_system.repository.HouseDAO;
import com.database_project.library_management_system.repository.LandlordDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseDAO houseDAO;
    private final LandlordDAO landlordDAO;

    public HouseService(HouseDAO houseDAO, LandlordDAO landlordDAO) {
        this.houseDAO = houseDAO;
        this.landlordDAO = landlordDAO;
    }

    public House save(House house) {
        if (house.getLandlordId() == null || landlordDAO.findById(house.getLandlordId()) == null) {
            throw new IllegalArgumentException("Invalid Landlord ID. House must be associated with an existing Landlord.");
        }

        houseDAO.save(house);
        return house;
    }

    public List<House> findAll() {
        return houseDAO.findAll();
    }

    public House findById(int id) {
        return houseDAO.findById(id);
    }

    public House update(House house) {
        if (house.getHouseId() == null || houseDAO.findById(house.getHouseId()) == null) {
            throw new IllegalArgumentException("House ID must be valid for update.");
        }

        if (house.getLandlordId() != null && landlordDAO.findById(house.getLandlordId()) == null) {
            throw new IllegalArgumentException("Cannot update house: New Landlord ID is invalid.");
        }

        houseDAO.update(house);
        return house;
    }

    public void delete(int id) {
        houseDAO.delete(id);
    }
}
