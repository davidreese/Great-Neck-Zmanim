package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
import com.reesedevelopment.greatneckzmanim.admin.structure.location.Location;
import com.reesedevelopment.greatneckzmanim.admin.structure.location.LocationMapper;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.Organization;
import com.reesedevelopment.greatneckzmanim.admin.structure.organization.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
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
        String sql = String.format("DELETE FROM MINYAN WHERE ID = '%s'", objectToDelete.getId());

        // TODO: USE PREP STATEMENT
        try {
            this.getConnection().createStatement()
            .execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Minyan objectToUpdate) {
        String sql = String.format("UPDATE MINYAN SET " +
                "TYPE = '%s', " +
                "LOCATION_ID = '%s', " +
                "ORGANIZATION_ID = '%s', " +
                "ENABLED = %b, " +
                "START_TIME_1 = '%s', " +
                "START_TIME_2 = '%s', " +
                "START_TIME_3 = '%s', " +
                "START_TIME_4 = '%s', " +
                "START_TIME_5 = '%s', " +
                "START_TIME_6 = '%s', " +
                "START_TIME_7 = '%s', " +
                "START_TIME_RC = '%s', " +
                "START_TIME_CH = '%s', " +
                "START_TIME_CHRC = '%s', " +
                "START_TIME_YT = '%s', " +
                "NOTES = '%s', " +
                "NUSACH = '%s' " +
                "WHERE ID = '%s'",
                objectToUpdate.getMinyanTypeString(),
                objectToUpdate.getLocationId(),
                objectToUpdate.getOrganizationId(),
                objectToUpdate.isEnabled(),
                objectToUpdate.getStartTime1(),
                objectToUpdate.getStartTime2(),
                objectToUpdate.getStartTime3(),
                objectToUpdate.getStartTime4(),
                objectToUpdate.getStartTime5(),
                objectToUpdate.getStartTime6(),
                objectToUpdate.getStartTime7(),
                objectToUpdate.getStartTimeRC(),
                objectToUpdate.getStartTimeCH(),
                objectToUpdate.getStartTimeCHRC(),
                objectToUpdate.getStartTimeYT(),
                objectToUpdate.getNotes(),
                objectToUpdate.getNusachString(),
                objectToUpdate.getId()
        );

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
