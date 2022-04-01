package com.reesedevelopment.greatneckzmanim.admin.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppRoleDAO extends JdbcDaoSupport {

    @Autowired
    public AppRoleDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNames(Integer userId) {
        String sql = "SELECT r.NAME " + " FROM User_Role ur, APP_ROLE r " + " WHERE ur.ID = r.ID AND ur.ID = ? ";

        Object[] params = new Object[] { userId };

        List<String> roles = this.getJdbcTemplate().queryForList(sql, params, String.class);

        System.out.println("Roles: " + roles);

        return roles;
    }

}