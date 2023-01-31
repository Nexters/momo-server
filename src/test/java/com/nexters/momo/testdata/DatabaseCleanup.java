package com.nexters.momo.testdata;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseCleanup implements InitializingBean {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = new ArrayList<>();
        extractTableNames();
    }

    private void extractTableNames() {
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void execute() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = FALSE");
        for (String tableName : tableNames) {
            jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
        }
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = TRUE");
    }
}
