package com.database_project.library_management_system.repository;

import com.database_project.library_management_system.entity.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class LeaseDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LeaseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class LeaseRowMapper implements RowMapper<Lease> {
        @Override
        public Lease mapRow(ResultSet rs, int rowNum) throws SQLException {

            return new Lease(
                    rs.getInt("lease_id"),
                    rs.getInt("house_id"),
                    rs.getInt("tenant_id"),
                    rs.getObject("start_date", LocalDate.class),
                    rs.getObject("end_date", LocalDate.class),
                    rs.getBigDecimal("deposit_amount"),
                    rs.getString("status")
            );
        }
    }

    public int save(Lease lease) {
        String sql = "INSERT INTO Leases (house_id, tenant_id, start_date, end_date, deposit_amount, status) VALUES (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                lease.getHouseId(),
                lease.getTenantId(),
                lease.getStartDate(),
                lease.getEndDate(),
                lease.getDepositAmount(),
                lease.getStatus()
        );
    }

    public Lease findById(int id) {
        String sql = "SELECT * FROM Leases WHERE lease_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new LeaseRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Lease> findAll() {
        String sql = "SELECT * FROM Leases";

        return jdbcTemplate.query(sql, new LeaseRowMapper());
    }

    public int update(Lease lease) {
        String sql = "UPDATE Leases SET house_id = ?, tenant_id = ?, start_date = ?, end_date = ?, deposit_amount = ?, status = ? WHERE lease_id = ?";

        return jdbcTemplate.update(sql,
                lease.getHouseId(),
                lease.getTenantId(),
                lease.getStartDate(),
                lease.getEndDate(),
                lease.getDepositAmount(),
                lease.getStatus(),
                lease.getLeaseId()
        );
    }

    public int delete(int id) {
        String sql = "DELETE FROM Leases WHERE lease_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public Lease findActiveLeaseByHouseId(int houseId) {
        String sql = "SELECT * FROM Leases WHERE house_id = ? AND status = 'ACTIVE' AND end_date >= CURRENT_DATE()";

        try {
            return jdbcTemplate.queryForObject(sql, new LeaseRowMapper(), houseId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
