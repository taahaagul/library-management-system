package com.database_project.library_management_system.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_HOUSE_SQL =
            "CREATE TABLE IF NOT EXISTS Houses (" +
                    "    house_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    landlord_id INT," +
                    "    address VARCHAR(255) NOT NULL," +
                    "    city VARCHAR(100) NOT NULL," +
                    "    zip_code VARCHAR(10) NOT NULL," +
                    "    monthly_rent DECIMAL(10, 2) NOT NULL," +
                    "    number_of_rooms INT," +
                    "    is_available BOOLEAN NOT NULL DEFAULT TRUE," +
                    "    description TEXT," +
                    "    FOREIGN KEY (landlord_id) REFERENCES Landlords(landlord_id)" +
                    ")";

    private static final String CREATE_LANDLORD_SQL =
            "CREATE TABLE IF NOT EXISTS Landlords (" +
                    "    landlord_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    first_name VARCHAR(100) NOT NULL," +
                    "    last_name VARCHAR(100) NOT NULL," +
                    "    phone_number VARCHAR(20)," +
                    "    email VARCHAR(100) UNIQUE NOT NULL" +
                    ")";

    private static final String CREATE_TENANT_SQL =
            "CREATE TABLE IF NOT EXISTS Tenants (" +
                    "    tenant_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    first_name VARCHAR(100) NOT NULL," +
                    "    last_name VARCHAR(100) NOT NULL," +
                    "    phone_number VARCHAR(20)," +
                    "    email VARCHAR(100) UNIQUE NOT NULL" +
                    ")";

    private static final String CREATE_LEASE_SQL =
            "CREATE TABLE IF NOT EXISTS Leases (" +
                    "    lease_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    house_id INT NOT NULL," +
                    "    tenant_id INT NOT NULL," +
                    "    start_date DATE NOT NULL," +
                    "    end_date DATE NOT NULL," +
                    "    deposit_amount DECIMAL(10, 2)," +
                    "    status ENUM('ACTIVE', 'EXPIRED', 'PENDING') NOT NULL," +
                    "    FOREIGN KEY (house_id) REFERENCES Houses(house_id)," +
                    "    FOREIGN KEY (tenant_id) REFERENCES Tenants(tenant_id)" +
                    ")";



    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Database Schema Initialization Started ---");
        try {
            jdbcTemplate.execute(CREATE_LANDLORD_SQL);
            jdbcTemplate.execute(CREATE_HOUSE_SQL);
            jdbcTemplate.execute(CREATE_TENANT_SQL);
            jdbcTemplate.execute(CREATE_LEASE_SQL);

            System.out.println("Database tables created successfully or already exist.");
        } catch (Exception e) {
            System.err.println("!! ATTENTION: Error occurred during database schema creation. " +
                    "Check your SQL syntax and database connection details. !!");
            System.err.println("Error Detail: " + e.getMessage());
            throw new RuntimeException("Schema initialization failed.", e);
        }
        System.out.println("----------------------------------------------");
    }
}