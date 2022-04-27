package com.reesedevelopment.greatneckzmanim.admin.structure.organization;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GNZOrganizationMapper implements RowMapper<GNZOrganization>, Serializable {

    public static final String BASE_SQL = "SELECT u.ID, u.NAME, u.ADDRESS, u.SITE_URI FROM ORGANIZATION u ";

    @Override
    public GNZOrganization mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("ID");
        String name = rs.getString("NAME");
        String address = rs.getString("ADDRESS");

        URI siteURI = null;
        try {
            String siteURIString = rs.getString("SITE_URI");
            if (siteURIString != null && !siteURIString.isEmpty()) {
                siteURI = new URI(siteURIString);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new GNZOrganization(id, name, address, siteURI);
    }

    public GNZOrganization mapRow(Map<String, Object> m) {
        String id = (String) m.get("ID");
        String name = (String) m.get("NAME");
        String address = (String) m.get("ADDRESS");

        URI siteURI = null;
        try {
            String siteURIString = (String) m.get("SITE_URI");
            if (siteURIString != null && !siteURIString.isEmpty()) {
                siteURI = new URI(siteURIString);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new GNZOrganization(id, name, address, siteURI);
    }
}