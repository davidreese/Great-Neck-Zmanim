package com.reesedevelopment.greatneckzmanim.admin.structure.organization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.reesedevelopment.greatneckzmanim.admin.structure.GNZSaveable;
import com.reesedevelopment.greatneckzmanim.admin.structure.user.GNZUser;
import com.reesedevelopment.greatneckzmanim.admin.structure.user.GNZUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrganizationDAO extends JdbcDaoSupport implements GNZSaveable<Organization> {

    @Autowired
    public OrganizationDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Organization findByName(String name) {
        String sql = OrganizationMapper.BASE_SQL + " WHERE u.NAME = ? ";
    
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        OrganizationMapper mapper = new OrganizationMapper();
    
        try {
            statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, name);
    
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
    public Organization findById(String id) {
        String sql = OrganizationMapper.BASE_SQL + " WHERE u.ID = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OrganizationMapper mapper = new OrganizationMapper();
    
        try {
            preparedStatement = this.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
    
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapper.mapRow(resultSet, 1);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error while finding organization by id: " + id, e);
            return null;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement and result set", e);
            }
        }
    }
    
    

    @Override
    public List<Organization> getAll() {
        String sql = "SELECT * FROM ORGANIZATION";

        OrganizationMapper mapper = new OrganizationMapper();

        List<Map<String, Object>> orgMaps = this.getJdbcTemplate().queryForList(sql);

        List<Organization> organizations = new ArrayList<>();

        for (Map<String, Object> orgMap : orgMaps) {
            organizations.add(mapper.mapRow(orgMap));
        }

        return organizations;
    }

    @Override
    public boolean save(Organization organization) {
        String sql;
        if (organization.getWebsiteURI() != null) {
            sql = "INSERT INTO ORGANIZATION (ID, NAME, ADDRESS, SITE_URI, NUSACH) VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO ORGANIZATION (ID, NAME, ADDRESS, SITE_URI, NUSACH) VALUES (?, ?, ?, NULL, ?)";
        }
    
        PreparedStatement insertStatement = null;
    
        try {
            insertStatement = this.getConnection().prepareStatement(sql);
    
            insertStatement.setString(1, organization.getId());
            insertStatement.setString(2, organization.getName());
            insertStatement.setString(3, organization.getAddress());
            if (organization.getWebsiteURI() != null) {
                insertStatement.setString(4, organization.getWebsiteURI().toString());
                insertStatement.setString(5, organization.getNusach().getText());
            } else {
                insertStatement.setString(4, organization.getNusach().getText());
            }
    
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
    public boolean delete(Organization objectToDelete) {
        String deleteString = "DELETE FROM ORGANIZATION " + "WHERE ID = ?";

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
    public boolean update(Organization organizationToUpdate) {
        String sql;
        if (organizationToUpdate.getWebsiteURI() != null) {
            sql = "UPDATE ORGANIZATION SET NAME = ?, ADDRESS = ?, SITE_URI = ?, NUSACH = ? WHERE ID = ?";
        } else {
            sql = "UPDATE ORGANIZATION SET NAME = ?, ADDRESS = ?, SITE_URI = NULL, NUSACH = ? WHERE ID = ?";
        }
    
        PreparedStatement updateStatement = null;
    
        try {
            updateStatement = this.getConnection().prepareStatement(sql);
    
            updateStatement.setString(1, organizationToUpdate.getName());
            updateStatement.setString(2, organizationToUpdate.getAddress());
            if (organizationToUpdate.getWebsiteURI() != null) {
                updateStatement.setString(3, organizationToUpdate.getWebsiteURI().toString());
                updateStatement.setString(4, organizationToUpdate.getNusach().getText());
                updateStatement.setString(5, organizationToUpdate.getId());
            } else {
                updateStatement.setString(3, organizationToUpdate.getNusach().getText());
                updateStatement.setString(4, organizationToUpdate.getId());
            }
    
            updateStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (updateStatement != null) {
                    updateStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public List<GNZUser> getUsersForOrganization(Organization organization) {
    String sql = "SELECT * FROM ACCOUNT WHERE ORGANIZATION_ID = ?";
    GNZUserMapper mapper = new GNZUserMapper();
    List<GNZUser> users = new ArrayList<>();

    try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
        stmt.setString(1, organization.getId());
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            GNZUser user = mapper.mapRow(rs, rs.getRow());
            users.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return users;
}

}