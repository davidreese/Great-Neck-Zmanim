package com.reesedevelopment.greatneckzmanim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Time;
import java.util.TimeZone;

@SpringBootApplication
public class GreatNeckZmanimApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreatNeckZmanimApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
    }
}
