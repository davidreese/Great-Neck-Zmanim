package com.reesedevelopment.greatneckzmanim.admin.users;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class GNZUserMapper implements RowMapper<GNZUser>, Serializable {

    public static final String BASE_SQL = "SELECT u.ID, u.USERNAME, u.EMAIL, u.ENCRYPTED_PASSWORD, u.ORGANIZATION_ID, u.ROLE_ID FROM ACCOUNT u ";

    @Override
    public GNZUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        String id = rs.getString("ID");
        String username = rs.getString("USERNAME");
        String email = rs.getString("EMAIL");
        String encrytedPassword = rs.getString("ENCRYPTED_PASSWORD");
        String orgId = rs.getString("ORGANIZATION_ID");
        Integer role = rs.getInt("ROLE_ID");

        return new GNZUser(id, username, email, encrytedPassword, orgId, role);
    }

    public GNZUser mapRow(Map<String, Object> m) {

        String id = (String) m.get("ID");
        String username = (String) m.get("NAME");
        String email = (String) m.get("EMAIL");
        String encrytedPassword = (String) m.get("ENCRYPTED_PASSWORD");
        String orgId = (String) m.get("ORGANIZATION_ID");
        Integer role = (Integer) m.get("ROLE_ID");

        return new GNZUser(id, username, email, encrytedPassword, orgId, role);
    }

}