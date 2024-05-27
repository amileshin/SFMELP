package org.amileshin.sfmelp.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amileshin.sfmelp.exception.DatabaseConnectException;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.amileshin.sfmelp.utils.ComposingUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClickHouseManager {
    private static final String DATABASE = "ClickHouse";

    public Connection getConnectionToDatabase(ConnectInfoDTO info) throws SQLException {
        if (info.getUsername() == null || info.getPassword() == null) {
            return DriverManager.getConnection(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info));
        } else {
            return DriverManager.getConnection(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info),
                    info.getUsername(), info.getPassword());
        }
    }

    public void getTestConnection(ConnectInfoDTO info) throws DatabaseConnectException {
        log.info("Attempting to connect to database {}",
                ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info));
        try {
            this.getConnectionToDatabase(info).close();
            log.info("Connection is successful");
        } catch (SQLException e) {
            log.info("Connection failed: {}", e.getMessage());
            throw new DatabaseConnectException(DATABASE, ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info));
        }
    }

    public List<String> listDatabases(ConnectInfoDTO info) throws DatabaseConnectException {
        try {
            Connection connect = this.getConnectionToDatabase(info);
            Statement statement = connect.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select name from system.tables " +
                            "where database='" + info.getDatabase() + "'");


            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString("name"));
            }
            connect.close();
            return tables;
        } catch (SQLException e) {
            log.info("Load data from DB {} failed: {}", ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info), e.getMessage());
            throw new DatabaseConnectException(DATABASE, ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info));
        }
    }

    public void loadLogsToDatabase(ConnectInfoDTO info, String table, List<String> logs) throws DatabaseConnectException {
        try {
            Connection connect = this.getConnectionToDatabase(info);
            Statement statement = connect.createStatement();
            statement.executeQuery(ComposingUtils.getSQLRequestToLoadLogToDatabase(info.getDatabase(), table, logs));

            connect.close();
        } catch (SQLException e) {
            log.info("Load data to DB {} failed: {}", ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info), e.getMessage());
            throw new DatabaseConnectException(DATABASE, ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info));
        }
    }

    public List<String> loadLogsFromDatabase(ConnectInfoDTO info, String table) throws DatabaseConnectException {
        try {
            Connection connect = this.getConnectionToDatabase(info);
            Statement statement = connect.createStatement();

            ResultSet result = statement.executeQuery(ComposingUtils
                    .getSQLRequestToLoadLogFromDatabase(info.getDatabase(), table));

            List<String> logs = new ArrayList<>();
            while (result.next()) {
                logs.add(result.getString("name"));
            }
            connect.close();
            return logs;
        } catch (SQLException e) {
            log.info("Load data from DB {} failed: {}", ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info), e.getMessage());
            throw new DatabaseConnectException(DATABASE, ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE,
                    info));
        }
    }
}
