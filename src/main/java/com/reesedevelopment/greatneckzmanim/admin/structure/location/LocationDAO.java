package com.reesedevelopment.greatneckzmanim.admin.structure.location;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class LocationDAO extends JdbcDaoSupport implements GNZSaveable<Location> {

    @Autowired
    public LocationDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

//    @Override
    public Location findByName(String name) {
//        TODO: DEAL WITH DUPLICATE NAMES
        String sql = LocationMapper.BASE_SQL + " WHERE l.NAME = ? ";

        Object[] params = new Object[] { name };
        LocationMapper mapper = new LocationMapper();

        try {
            Location locationInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return locationInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Location findById(String id) {
        String sql = LocationMapper.BASE_SQL + " WHERE l.ID = ? ";

        Object[] params = new Object[] { id };
        LocationMapper mapper = new LocationMapper();

        try {
            Location locationInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return locationInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Location> findMatching(String organizationId) {
        String sql = LocationMapper.BASE_SQL + " WHERE l.ORGANIZATION_ID = ? ";

        Object[] params = new Object[] { organizationId };
        LocationMapper mapper = new LocationMapper();

        try {
            List<Location> locationInfo = this.getJdbcTemplate().query(sql, params, mapper);
            return locationInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Location> getAll() {
        String sql = "SELECT * FROM LOCATION";

        LocationMapper mapper = new LocationMapper();

        List<Map<String, Object>> locationMaps = this.getJdbcTemplate().queryForList(sql);

        List<Location> locations = new ArrayList<>();

//        iterate through the list and create an GNZUser object for each row
        for (Map<String, Object> locationMap : locationMaps) {
            locations.add(mapper.mapRow(locationMap));
        }

        return locations;
    }

    @Override
    public boolean save(Location location) {
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
    public boolean delete(Location objectToDelete) {
        String sql = String.format("DELETE FROM LOCATION WHERE ID='%s'", objectToDelete.getId());
// TODO: USE PREP STATEMENT
        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Location locationToUpdate) {
        String sql = String.format("UPDATE LOCATION SET NAME='%s' WHERE ID='%s'", locationToUpdate.getName(), locationToUpdate.getId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
