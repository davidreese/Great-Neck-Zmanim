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
        String sql = MinyanMapper.BASE_SQL + " WHERE m.ID = ?";
        PreparedStatement preparedStatement = null;
        MinyanMapper mapper = new MinyanMapper();
        ResultSet resultSet = null;
    
        try {
            preparedStatement = this.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
    
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapper.mapRow(resultSet, resultSet.getRow());
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        String insertString = "INSERT INTO MINYAN " +
                "(ID, TYPE, LOCATION_ID, ORGANIZATION_ID, ENABLED, START_TIME_1, START_TIME_2, START_TIME_3, START_TIME_4, START_TIME_5, START_TIME_6, START_TIME_7, START_TIME_RC, START_TIME_CH, START_TIME_CHRC, START_TIME_YT, NOTES, NUSACH) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertMinyan = null;
    
        try {
            insertMinyan = this.getConnection().prepareStatement(insertString);
            insertMinyan.setString(1, objectToSave.getId());
            insertMinyan.setString(2, objectToSave.getMinyanTypeString());
            insertMinyan.setString(3, objectToSave.getLocationId());
            insertMinyan.setString(4, objectToSave.getOrganizationId());
            insertMinyan.setBoolean(5, objectToSave.isEnabled());
            insertMinyan.setString(6, objectToSave.getStartTime1());
            insertMinyan.setString(7, objectToSave.getStartTime2());
            insertMinyan.setString(8, objectToSave.getStartTime3());
            insertMinyan.setString(9, objectToSave.getStartTime4());
            insertMinyan.setString(10, objectToSave.getStartTime5());
            insertMinyan.setString(11, objectToSave.getStartTime6());
            insertMinyan.setString(12, objectToSave.getStartTime7());
            insertMinyan.setString(13, objectToSave.getStartTimeRC());
            insertMinyan.setString(14, objectToSave.getStartTimeCH());
            insertMinyan.setString(15, objectToSave.getStartTimeCHRC());
            insertMinyan.setString(16, objectToSave.getStartTimeYT());
            insertMinyan.setString(17, objectToSave.getNotes());
            insertMinyan.setString(18, objectToSave.getNusachString());
    
            int affectedRows = insertMinyan.executeUpdate();
            return affectedRows > 0;
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (insertMinyan != null) {
                    insertMinyan.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
