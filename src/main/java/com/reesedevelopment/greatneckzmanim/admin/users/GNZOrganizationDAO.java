package com.reesedevelopment.greatneckzmanim.admin.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GNZOrganizationDAO extends JdbcDaoSupport {

    @Autowired
    public GNZOrganizationDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    /*
    public AppUser findOrganization(String userName) {
        System.out.println("findUserAccount called");
        // Select .. from App_User u Where u.User_Name = ?
        String sql = AppUserMapper.BASE_SQL + " WHERE u.NAME = ? ";

        Object[] params = new Object[] { userName };
        AppUserMapper mapper = new AppUserMapper();

        try {
            AppUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    */

    public List<GNZOrganization> findAll() {
        String sql = "SELECT * FROM ORGANIZATION";

        GNZOrganizationMapper mapper = new GNZOrganizationMapper();

        List<Map<String, Object>> orgMaps = this.getJdbcTemplate().queryForList(sql);

        List<GNZOrganization> organizations = new ArrayList<>();

//        iterate through the list and create an GNZUser object for each row
        for (Map<String, Object> orgMap : orgMaps) {
            organizations.add(mapper.mapRow(orgMap));
        }

//        System.out.println(users);
        /*
        try {
//            Class.forName("org.h2.Driver");
//            String url = "jdbc:h2:file:./data/demo";
//            Connection con = DriverManager.getConnection(url);
            Statement stmt = this.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("rs = " + rs.getString("NAME"));
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */


//        List<Map<String, Object>> users = this.getJdbcTemplate().queryForList(sql, mapper);

//        mapper.mapRow()

//        System.out.println("Users: " + users);

        return organizations;
    }
}