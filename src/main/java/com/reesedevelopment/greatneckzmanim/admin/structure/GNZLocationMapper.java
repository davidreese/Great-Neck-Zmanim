package com.reesedevelopment.greatneckzmanim.admin.structure;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GNZLocationMapper implements RowMapper<GNZLocation>, Serializable {

    public static final String BASE_SQL = "SELECT l.ID, l.NAME, l.ORGANIZATION_ID FROM LOCATION l ";

    @Override
    public GNZLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("ID");
        String name = rs.getString("NAME");
        String organizationId = rs.getString("ORGANIZATION_ID");

        return new GNZLocation(id, name, organizationId);
    }

    public GNZLocation mapRow(Map<String, Object> m) {
        String id = (String) m.get("ID");
        String name = (String) m.get("NAME");
        String organizationId = (String) m.get("ORGANIZATION_ID");

        return new GNZLocation(id, name, organizationId);
    }
}