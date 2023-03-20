package com.reesedevelopment.greatneckzmanim.admin.structure.organization;

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

        Object[] params = new Object[] { name };
        OrganizationMapper mapper = new OrganizationMapper();

        try {
            Organization orgInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return orgInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Organization findById(String id) {
        String sql = OrganizationMapper.BASE_SQL + " WHERE u.ID = ? ";

        Object[] params = new Object[] { id };
        OrganizationMapper mapper = new OrganizationMapper();

        try {
            Organization orgInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return orgInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
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
            sql = String.format("INSERT INTO ORGANIZATION (ID, NAME, ADDRESS, SITE_URI, NUSACH) VALUES ('%s', '%s', '%s', '%s', '%s')", organization.getId(), organization.getName(), organization.getAddress(), organization.getWebsiteURI(), organization.getNusach().getText());
        } else {
            sql = String.format("INSERT INTO ORGANIZATION (ID, NAME, ADDRESS, SITE_URI, NUSACH) VALUES ('%s', '%s', '%s', NULL, '%s')", organization.getId(), organization.getName(), organization.getAddress(), organization.getNusach().getText());
        }

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Organization objectToDelete) throws SQLException {
        String sql = String.format("DELETE FROM ORGANIZATION WHERE ID='%s'", objectToDelete.getId());

        // TODO: USE PREP STATEMENT

        try {
            this.getConnection().createStatement().execute(sql);

            String matchingOrgsSQL = String.format("DELETE FROM ACCOUNT WHERE ORGANIZATION_ID='%s'", objectToDelete.getId());

            try {
                this.getConnection().createStatement().execute(matchingOrgsSQL);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Organization organizationToUpdate) throws SQLException {
        String sql;
        if (organizationToUpdate.getWebsiteURI() != null) {
            sql = String.format("UPDATE ORGANIZATION SET NAME='%s', ADDRESS='%s', SITE_URI='%s', NUSACH='%s' WHERE ID='%s'", organizationToUpdate.getName(), organizationToUpdate.getAddress(), organizationToUpdate.getWebsiteURI(), organizationToUpdate.getNusach().getText(), organizationToUpdate.getId());
        } else {
            sql = String.format("UPDATE ORGANIZATION SET NAME='%s', ADDRESS='%s', SITE_URI=NULL, NUSACH='%s' WHERE ID='%s'", organizationToUpdate.getName(), organizationToUpdate.getAddress(), organizationToUpdate.getNusach().getText(), organizationToUpdate.getId());
        }

        try {
            this.getConnection().createStatement().execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<GNZUser> getUsersForOrganization(Organization organization) {
        String sql = String.format("SELECT * FROM ACCOUNT WHERE ORGANIZATION_ID='%s'", organization.getId());

        GNZUserMapper mapper = new GNZUserMapper();

        List<Map<String, Object>> userMaps = this.getJdbcTemplate().queryForList(sql);

        List<GNZUser> users = new ArrayList<>();

//        iterate through the list and create an GNZUser object for each row
        for (Map<String, Object> userMap : userMaps) {
            users.add(mapper.mapRow(userMap));
        }

        return users;
    }
}