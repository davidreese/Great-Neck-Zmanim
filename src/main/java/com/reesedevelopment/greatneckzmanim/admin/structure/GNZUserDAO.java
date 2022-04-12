package com.reesedevelopment.greatneckzmanim.admin.structure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

@Repository
@Transactional
public class GNZUserDAO extends JdbcDaoSupport implements GNZSaveable<GNZUser> {

    @Autowired
    public GNZUserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public GNZUser findUserAccount(String userName) {
        System.out.println("findUserAccount called");
        // Select .. from App_User u Where u.User_Name = ?
        String sql = GNZUserMapper.BASE_SQL + " WHERE u.USERNAME = ? ";

        Object[] params = new Object[] { userName };
        GNZUserMapper mapper = new GNZUserMapper();

        try {
            GNZUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<GNZUser> getAll() {
        String sql = "SELECT ID, USERNAME, ENCRYPTED_PASSWORD FROM ACCOUNT";

        GNZUserMapper mapper = new GNZUserMapper();

        List<Map<String, Object>> userMaps = this.getJdbcTemplate().queryForList(sql);

        List<GNZUser> users = new ArrayList<GNZUser>();

//        iterate through the list and create an GNZUser object for each row
        for (Map<String, Object> userMap : userMaps) {
            users.add(mapper.mapRow(userMap));
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

        return users;
    }

    public boolean save(GNZUser user) {
        String sql = String.format("INSERT INTO ACCOUNT VALUES ('%s', '%s', '%s', '%s', '%s', '%d')", user.getId(), user.getUsername(), user.getEmail(), user.getEncryptedPassword(), user.getOrganizationId(), user.getRoleId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}