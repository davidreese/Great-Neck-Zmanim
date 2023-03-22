package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
import com.reesedevelopment.greatneckzmanim.admin.structure.location.Location;
import com.reesedevelopment.greatneckzmanim.admin.structure.location.LocationMapper;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.Organization;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class MinyanDAO extends JdbcDaoSupport implements GNZSaveable<Minyan> {

    @Autowired
    public MinyanDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public Minyan findById(String id) {
        String sql = MinyanMapper.BASE_SQL + " WHERE m.ID = ? ";

        Object[] params = new Object[] { id };
        MinyanMapper mapper = new MinyanMapper();

        try {
            Minyan minyanInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return minyanInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Minyan> getAll() {
        String sql = "SELECT * FROM MINYAN";

        MinyanMapper mapper = new MinyanMapper();

        List<Map<String, Object>> minyanMaps = this.getJdbcTemplate().queryForList(sql);

        List<Minyan> minyanim = new ArrayList<>();

        for (Map<String, Object> minyanMap : minyanMaps) {
            minyanim.add(mapper.mapRow(minyanMap));
        }

        return minyanim;
    }

    public List<Minyan> getEnabled() {
        String sql = "SELECT * FROM MINYAN WHERE ENABLED = 1";

        MinyanMapper mapper = new MinyanMapper();

        List<Map<String, Object>> minyanMaps = this.getJdbcTemplate().queryForList(sql);

        List<Minyan> minyanim = new ArrayList<>();

        for (Map<String, Object> minyanMap : minyanMaps) {
            minyanim.add(mapper.mapRow(minyanMap));
        }

        return minyanim;
    }

    @Override
    public boolean save(Minyan objectToSave) {
        String sql = String.format("INSERT INTO MINYAN " +
                "(ID, TYPE, LOCATION_ID, ORGANIZATION_ID, ENABLED, START_TIME_1, START_TIME_2, START_TIME_3, START_TIME_4, START_TIME_5, START_TIME_6, START_TIME_7, START_TIME_RC, START_TIME_CH, START_TIME_CHRC, START_TIME_YT, NOTES, NUSACH) " +
                "VALUES ('%s', '%s', '%s', '%s', %b, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                objectToSave.getId(),
                objectToSave.getMinyanTypeString(),
                objectToSave.getLocationId(),
                objectToSave.getOrganizationId(),
                objectToSave.isEnabled(),
                objectToSave.getStartTime1(),
                objectToSave.getStartTime2(),
                objectToSave.getStartTime3(),
                objectToSave.getStartTime4(),
                objectToSave.getStartTime5(),
                objectToSave.getStartTime6(),
                objectToSave.getStartTime7(),
                objectToSave.getStartTimeRC(),
                objectToSave.getStartTimeCH(),
                objectToSave.getStartTimeCHRC(),
                objectToSave.getStartTimeYT(),
                objectToSave.getNotes(),
                objectToSave.getNusachString()
        );

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Minyan objectToDelete) {
        String deleteString = "DELETE FROM MINYAN WHERE ID = ?";
        PreparedStatement deleteMinyan = null;
    
        try {
            deleteMinyan = this.getConnection().prepareStatement(deleteString);
            deleteMinyan.setString(1, objectToDelete.getId());
    
            int affectedRows = deleteMinyan.executeUpdate();
            return affectedRows > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (deleteMinyan != null) {
                    deleteMinyan.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    

    @Override
    public boolean update(Minyan objectToUpdate) {
        String updateString = "UPDATE MINYAN SET " +
                "TYPE = ?, " +
                "LOCATION_ID = ?, " +
                "ORGANIZATION_ID = ?, " +
                "ENABLED = ?, " +
                "START_TIME_1 = ?, " +
                "START_TIME_2 = ?, " +
                "START_TIME_3 = ?, " +
                "START_TIME_4 = ?, " +
                "START_TIME_5 = ?, " +
                "START_TIME_6 = ?, " +
                "START_TIME_7 = ?, " +
                "START_TIME_RC = ?, " +
                "START_TIME_CH = ?, " +
                "START_TIME_CHRC = ?, " +
                "START_TIME_YT = ?, " +
                "NOTES = ?, " +
                "NUSACH = ? " +
                "WHERE ID = ?";
        PreparedStatement updateMinyan = null;
    
        try {
            updateMinyan = this.getConnection().prepareStatement(updateString);
            
            updateMinyan.setString(1, objectToUpdate.getMinyanTypeString());
            updateMinyan.setString(2, objectToUpdate.getLocationId());
            updateMinyan.setString(3, objectToUpdate.getOrganizationId());
            updateMinyan.setBoolean(4, objectToUpdate.isEnabled());
            updateMinyan.setString(5, objectToUpdate.getStartTime1());
            updateMinyan.setString(6, objectToUpdate.getStartTime2());
            updateMinyan.setString(7, objectToUpdate.getStartTime3());
            updateMinyan.setString(8, objectToUpdate.getStartTime4());
            updateMinyan.setString(9, objectToUpdate.getStartTime5());
            updateMinyan.setString(10, objectToUpdate.getStartTime6());
            updateMinyan.setString(11, objectToUpdate.getStartTime7());
            updateMinyan.setString(12, objectToUpdate.getStartTimeRC());
            updateMinyan.setString(13, objectToUpdate.getStartTimeCH());
            updateMinyan.setString(14, objectToUpdate.getStartTimeCHRC());
            updateMinyan.setString(15, objectToUpdate.getStartTimeYT());
            updateMinyan.setString(16, objectToUpdate.getNotes());
            updateMinyan.setString(17, objectToUpdate.getNusachString());
            updateMinyan.setString(18, objectToUpdate.getId());
    
            int affectedRows = updateMinyan.executeUpdate();
            return affectedRows > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (updateMinyan != null) {
                    updateMinyan.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public List<Minyan> findMatching(String organizationId){
        String sql = MinyanMapper.BASE_SQL + " WHERE m.ORGANIZATION_ID = ? ";

        Object[] params = new Object[] { organizationId };
        MinyanMapper mapper = new MinyanMapper();

        try {
            List<Minyan> minyanInfo = this.getJdbcTemplate().query(sql, params, mapper);
            return minyanInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Minyan> findEnabledMatching(String organizationId) {
        String sql = MinyanMapper.BASE_SQL + " WHERE m.ORGANIZATION_ID = ? AND m.ENABLED = 1";

        Object[] params = new Object[] { organizationId };
        MinyanMapper mapper = new MinyanMapper();

        try {
            List<Minyan> minyanInfo = this.getJdbcTemplate().query(sql, params, mapper);
            return minyanInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
