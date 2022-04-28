package com.reesedevelopment.greatneckzmanim.admin.structure.minyan;

import com.reesedevelopment.greatneckzmanim.admin.structure.location.Location;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MinyanMapper implements RowMapper<Minyan>, Serializable {

    public static final String BASE_SQL = "SELECT m.ID, m.TYPE, m.LOCATION_ID, m.ORGANIZATION_ID, m.ENABLED, m.START_TIME_1, m.START_TIME_2, m.START_TIME_3, m.START_TIME_4, m.START_TIME_5, m.START_TIME_6, m.START_TIME_7, m.START_TIME_RC, m.START_TIME_CH, m.START_TIME_CHRC, m.START_TIME_YT, m.NOTES, m.NUSACH FROM MINYAN m";

    @Override
    public Minyan mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("ID");
        String typeString = rs.getString("TYPE");
        String locationId = rs.getString("LOCATION_ID");
        String organizationId = rs.getString("ORGANIZATION_ID");
        boolean enabled = rs.getBoolean("ENABLED");
        String startTime1 = rs.getString("START_TIME_1");
        String startTime2 = rs.getString("START_TIME_2");
        String startTime3 = rs.getString("START_TIME_3");
        String startTime4 = rs.getString("START_TIME_4");
        String startTime5 = rs.getString("START_TIME_5");
        String startTime6 = rs.getString("START_TIME_6");
        String startTime7 = rs.getString("START_TIME_7");
        String startTimeRC = rs.getString("START_TIME_RC");
        String startTimeCH = rs.getString("START_TIME_CH");
        String startTimeCHRC = rs.getString("START_TIME_CHRC");
        String startTimeYT = rs.getString("START_TIME_YT");
        String notes = rs.getString("NOTES");
        String nusach = rs.getString("NUSACH");

        return new Minyan(id, typeString, locationId, organizationId, enabled, startTime1, startTime2, startTime3, startTime4, startTime5, startTime6, startTime7, startTimeRC, startTimeCH, startTimeCHRC, startTimeYT, notes, nusach);
    }

    public Minyan mapRow(Map<String, Object> m) {
        String id = (String) m.get("ID");
        String typeString = (String) m.get("TYPE");
        String organizationId = (String) m.get("ORGANIZATION_ID");
        String locationId = (String) m.get("LOCATION_ID");
        boolean enabled = (boolean) m.get("ENABLED");
        String startTime1 = (String) m.get("START_TIME_1");
        String startTime2 = (String) m.get("START_TIME_2");
        String startTime3 = (String) m.get("START_TIME_3");
        String startTime4 = (String) m.get("START_TIME_4");
        String startTime5 = (String) m.get("START_TIME_5");
        String startTime6 = (String) m.get("START_TIME_6");
        String startTime7 = (String) m.get("START_TIME_7");
        String startTimeRC = (String) m.get("START_TIME_RC");
        String startTimeCH = (String) m.get("START_TIME_CH");
        String startTimeCHRC = (String) m.get("START_TIME_CHRC");
        String startTimeYT = (String) m.get("START_TIME_YT");
        String notes = (String) m.get("NOTES");
        String nusach = (String) m.get("NUSACH");


        return new Minyan(id, typeString, locationId, organizationId, enabled, startTime1, startTime2, startTime3, startTime4, startTime5, startTime6, startTime7, startTimeRC, startTimeCH, startTimeCHRC, startTimeYT, notes, nusach);
    }
}
