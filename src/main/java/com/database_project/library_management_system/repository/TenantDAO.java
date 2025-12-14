package com.database_project.library_management_system.repository;

import com.database_project.library_management_system.entity.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TenantDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TenantDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class TenantRowMapper implements RowMapper<Tenant> {
        @Override
        public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Tenant(
                    rs.getInt("tenant_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("email")
            );
        }
    }

    public int save(Tenant tenant) {
        String sql = "INSERT INTO Tenants (first_name, last_name, phone_number, email) VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                tenant.getFirstName(),
                tenant.getLastName(),
                tenant.getPhoneNumber(),
                tenant.getEmail()
        );
    }

    public Tenant findById(int id) {
        String sql = "SELECT * FROM Tenants WHERE tenant_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new TenantRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Tenant> findAll() {
        String sql = "SELECT * FROM Tenants";

        return jdbcTemplate.query(sql, new TenantRowMapper());
    }

    public int update(Tenant tenant) {
        String sql = "UPDATE Tenants SET first_name = ?, last_name = ?, phone_number = ?, email = ? WHERE tenant_id = ?";

        return jdbcTemplate.update(sql,
                tenant.getFirstName(),
                tenant.getLastName(),
                tenant.getPhoneNumber(),
                tenant.getEmail(),
                tenant.getTenantId()
        );
    }

    public int delete(int id) {
        String sql = "DELETE FROM Tenants WHERE tenant_id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
