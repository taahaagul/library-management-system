package com.database_project.library_management_system.repository;

import com.database_project.library_management_system.entity.Landlord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LandlordDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LandlordDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class LandlordRowMapper implements RowMapper<Landlord> {
        @Override
        public Landlord mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Landlord(
                    rs.getInt("landlord_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            );
        }
    }

    public int save(Landlord landlord) {
        String sql = "INSERT INTO Landlords (first_name, last_name, phone_number, email) VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                landlord.getFirstName(),
                landlord.getLastName(),
                landlord.getPhoneNumber(),
                landlord.getEmail()
        );
    }

    public Landlord findById(int id) {
        String sql = "SELECT * FROM Landlords WHERE landlord_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new LandlordRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Landlord> findAll() {
        String sql = "SELECT * FROM Landlords";

        return jdbcTemplate.query(sql, new LandlordRowMapper());
    }

    public int update(Landlord landlord) {
        String sql = "UPDATE Landlords SET first_name = ?, last_name = ?, phone_number = ?, email = ? WHERE landlord_id = ?";

        return jdbcTemplate.update(sql,
                landlord.getFirstName(),
                landlord.getLastName(),
                landlord.getPhoneNumber(),
                landlord.getEmail(),
                landlord.getLandlordId()
        );
    }

    public int delete(int id) {
        String sql = "DELETE FROM Landlords WHERE landlord_id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
