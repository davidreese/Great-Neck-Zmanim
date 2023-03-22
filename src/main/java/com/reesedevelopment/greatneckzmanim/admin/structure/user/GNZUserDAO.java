package com.reesedevelopment.greatneckzmanim.admin.structure.user;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        GNZUserMapper mapper = new GNZUserMapper();
    
        try {
            statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, username);
    
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapper.mapRow(resultSet, resultSet.getRow());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    @Override
    public GNZUser findById(String id) {
        String sql = GNZUserMapper.BASE_SQL + " WHERE u.ID = ? ";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        GNZUserMapper mapper = new GNZUserMapper();
    
        try {
            preparedStatement = this.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();
    
            if (rs.next()) {
                return mapper.mapRow(rs, rs.getRow());
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        return users;
    }

    @Override
    public boolean save(GNZUser user) {
        PreparedStatement insertStatement = null;
        String sql = "INSERT INTO ACCOUNT VALUES (?, ?, ?, ?, ?, ?)";
    
        try {
            insertStatement = this.getConnection().prepareStatement(sql);
    
            insertStatement.setString(1, user.getId());
            insertStatement.setString(2, user.getUsername());
            insertStatement.setString(3, user.getEmail());
            insertStatement.setString(4, user.getEncryptedPassword());
            insertStatement.setString(5, user.getOrganizationId());
            insertStatement.setInt(6, user.getRoleId().intValue());
    
            insertStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (insertStatement != null) {
                    insertStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    @Override
    public boolean delete(GNZUser objectToDelete) {
        String deleteString = "DELETE FROM ACCOUNT " + "WHERE ID = ?";

        PreparedStatement deleteAccount = null;

        try {
            deleteAccount = this.getConnection().prepareStatement(deleteString);
    
            deleteAccount.setString(1, objectToDelete.getId());

            deleteAccount.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (deleteAccount != null) {
                    deleteAccount.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(GNZUser objectToUpdate) {
        PreparedStatement updateAccount = null;

        try {
            String updateString = "UPDATE ACCOUNT SET USERNAME = ?, EMAIL = ?, ENCRYPTED_PASSWORD = ?, ORGANIZATION_ID = ?, ROLE_ID = ? WHERE ID = ?";
    
            updateAccount = this.getConnection().prepareStatement(updateString);

        updateAccount.setString(1, objectToUpdate.getUsername());
        updateAccount.setString(2, objectToUpdate.getEmail());
        updateAccount.setString(3, objectToUpdate.getEncryptedPassword());
        updateAccount.setString(4, objectToUpdate.getOrganizationId());
        updateAccount.setInt(5, objectToUpdate.getRoleId().intValue());
        updateAccount.setString(6, objectToUpdate.getId());
        
            updateAccount.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (updateAccount != null) {
                    updateAccount.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}