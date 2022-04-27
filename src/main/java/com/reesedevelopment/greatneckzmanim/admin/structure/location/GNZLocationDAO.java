package com.reesedevelopment.greatneckzmanim.admin.structure.location;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class GNZLocationDAO extends JdbcDaoSupport implements GNZSaveable<GNZLocation> {

    @Autowired
    public GNZLocationDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public GNZLocation findByName(String name) {
//        TODO: DEAL WITH DUPLICATE NAMES
        String sql = GNZLocationMapper.BASE_SQL + " WHERE l.NAME = ? ";

        Object[] params = new Object[] { name };
        GNZLocationMapper mapper = new GNZLocationMapper();

        try {
            GNZLocation locationInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return locationInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public GNZLocation findById(String id) {
        String sql = GNZLocationMapper.BASE_SQL + " WHERE l.ID = ? ";

        Object[] params = new Object[] { id };
        GNZLocationMapper mapper = new GNZLocationMapper();

        try {
            GNZLocation locationInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return locationInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<GNZLocation> findMatching(String organizationId) {
        String sql = GNZLocationMapper.BASE_SQL + " WHERE l.ORGANIZATION_ID = ? ";

        Object[] params = new Object[] { organizationId };
        GNZLocationMapper mapper = new GNZLocationMapper();

        try {
            List<GNZLocation> locationInfo = this.getJdbcTemplate().query(sql, params, mapper);
            return locationInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<GNZLocation> getAll() {
        String sql = "SELECT * FROM LOCATION";

        GNZLocationMapper mapper = new GNZLocationMapper();

        List<Map<String, Object>> locationMaps = this.getJdbcTemplate().queryForList(sql);

        List<GNZLocation> locations = new ArrayList<>();

//        iterate through the list and create an GNZUser object for each row
        for (Map<String, Object> locationMap : locationMaps) {
            locations.add(mapper.mapRow(locationMap));
        }

        return locations;
    }

    @Override
    public boolean save(GNZLocation location) {
        String sql = String.format("INSERT INTO LOCATION VALUES ('%s', '%s', '%s')", location.getId(), location.getName(), location.getOrganizationId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(GNZLocation objectToDelete) {
        String sql = String.format("DELETE FROM LOCATION WHERE ID='%s'", objectToDelete.getId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(GNZLocation locationToUpdate) {
        String sql = String.format("UPDATE LOCATION SET NAME='%s' WHERE ID='%s'", locationToUpdate.getName(), locationToUpdate.getId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
