package com.database_project.library_management_system.repository;

import com.database_project.library_management_system.entity.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HouseDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HouseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class HouseRowMapper implements RowMapper<House> {
        public House mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new House(
                    rs.getInt("house_id"),
                    rs.getInt("landlord_id"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("zip_code"),
                    rs.getBigDecimal("monthly_rent"),
                    rs.getInt("number_of_rooms"),
                    rs.getBoolean("is_available"),
                    rs.getString("description")
            );
        }
    }

    public int save(House house) {
        String sql = "INSERT INTO Houses (landlord_id, address, city, zip_code, monthly_rent, number_of_rooms, is_available, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                house.getLandlordId(),
                house.getAddress(),
                house.getCity(),
                house.getZipCode(),
                house.getMonthlyRent(),
                house.getNumberOfRooms(),
                house.getIsAvailable(),
                house.getDescription()
        );
    }

    public House findById(int id) {
        String sql = "SELECT * FROM Houses WHERE house_id = ?";

        return jdbcTemplate.queryForObject(sql, new HouseRowMapper(), id);
    }

    public List<House> findAll() {
        String sql = "SELECT * FROM Houses";

        return jdbcTemplate.query(sql, new HouseRowMapper());
    }

    public int update(House house) {
        String sql = "UPDATE Houses SET landlord_id = ?, address = ?, city = ?, zip_code = ?, monthly_rent = ?, number_of_rooms = ?, is_available = ?, description = ? WHERE house_id = ?";

        return jdbcTemplate.update(sql,
                house.getLandlordId(),
                house.getAddress(),
                house.getCity(),
                house.getZipCode(),
                house.getMonthlyRent(),
                house.getNumberOfRooms(),
                house.getIsAvailable(),
                house.getDescription(),
                house.getHouseId()
        );
    }

    public int delete(int id) {
        String sql = "DELETE FROM Houses WHERE house_id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
