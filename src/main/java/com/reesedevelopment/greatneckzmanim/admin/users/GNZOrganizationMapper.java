package com.reesedevelopment.greatneckzmanim.admin.users;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GNZOrganizationMapper implements RowMapper<GNZOrganization>, Serializable {

    public static final String BASE_SQL = "SELECT u.ID, u.NAME, u.ENCRYPTED_PASSWORD FROM APP_USER u ";

    @Override
    public GNZOrganization mapRow(ResultSet rs, int rowNum) throws SQLException {

        String id = rs.getString("ID");
        String name = rs.getString("NAME");
        String address = rs.getString("ADDRESS");

        return new GNZOrganization(id, name, address);
    }

    public GNZOrganization mapRow(Map<String, Object> m) {

        String id = (String) m.get("ID");
        String name = (String) m.get("NAME");
        String address = (String) m.get("ADDRESS");

        return new GNZOrganization(id, name, address);
    }

}