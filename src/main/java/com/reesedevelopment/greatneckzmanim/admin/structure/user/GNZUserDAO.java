package com.reesedevelopment.greatneckzmanim.admin.structure.user;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
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

    public GNZUser findByName(String username) {
        String sql = GNZUserMapper.BASE_SQL + " WHERE u.USERNAME = ? ";

        Object[] params = new Object[] { username };
        GNZUserMapper mapper = new GNZUserMapper();

        try {
            GNZUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public GNZUser findById(String id) {
        String sql = GNZUserMapper.BASE_SQL + " WHERE u.ID = ? ";

        Object[] params = new Object[] { id };
        GNZUserMapper mapper = new GNZUserMapper();

        try {
            GNZUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<GNZUser> getAll() {
        String sql = "SELECT ID, USERNAME, EMAIL, ENCRYPTED_PASSWORD, ROLE_ID, ORGANIZATION_ID FROM ACCOUNT";

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

    @Override
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

    @Override
    public boolean delete(GNZUser objectToDelete) {
        String sql = String.format("DELETE FROM ACCOUNT WHERE ID='%s'", objectToDelete.getId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(GNZUser objectToUpdate) {
        String sql = String.format("UPDATE ACCOUNT SET USERNAME='%s', EMAIL='%s', ORGANIZATION_ID='%s', ROLE_ID='%s' WHERE ID='%s'", objectToUpdate.getUsername(), objectToUpdate.getEmail(), objectToUpdate.getOrganizationId(), objectToUpdate.getRoleId(), objectToUpdate.getId());

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}