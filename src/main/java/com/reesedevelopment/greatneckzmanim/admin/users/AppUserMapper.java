package com.reesedevelopment.greatneckzmanim.admin.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AppUserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL = "SELECT u.ID, u.NAME, u.ENCRYPTED_PASSWORD FROM App_User u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        Integer userId = rs.getInt("ID");
        String userName = rs.getString("NAME");
        String encrytedPassword = rs.getString("ENCRYPTED_PASSWORD");

        return new AppUser(userId, userName, encrytedPassword);
    }

}