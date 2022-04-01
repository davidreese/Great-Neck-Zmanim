package com.reesedevelopment.greatneckzmanim.admin.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AppUserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL = "SELECT u.User_Id, u.User_Name, u.Encryted_Password FROM App_User u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        Integer userId = rs.getInt("User_Id");
        String userName = rs.getString("User_Name");
        String encrytedPassword = rs.getString("Encryted_Password");

        return new AppUser(userId, userName, encrytedPassword);
    }

}