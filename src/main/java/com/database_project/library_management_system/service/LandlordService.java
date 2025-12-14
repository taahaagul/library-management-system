package com.database_project.library_management_system.service;

import com.database_project.library_management_system.entity.Landlord;
import com.database_project.library_management_system.repository.LandlordDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandlordService {

    private final LandlordDAO landlordDAO;

    public LandlordService(LandlordDAO landlordDAO) {
        this.landlordDAO = landlordDAO;
    }

    public Landlord save(Landlord landlord) {
        landlordDAO.save(landlord);
        return landlord;
    }

    public List<Landlord> findAll() {
        return landlordDAO.findAll();
    }

    public Landlord findById(int id) {
        return landlordDAO.findById(id);
    }

    public Landlord update(Landlord landlord) {
        if (landlord.getLandlordId() == null || landlordDAO.findById(landlord.getLandlordId()) == null) {
            throw new IllegalArgumentException("Landlord ID must be valid for update.");
        }

        landlordDAO.update(landlord);
        return landlord;
    }

    public void delete(int id) {
        landlordDAO.delete(id);
    }
}
