package org.amileshin.sfmelp.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amileshin.sfmelp.exception.database.DatabaseAccessException;
import org.amileshin.sfmelp.exception.database.DatabaseConnectException;
import org.amileshin.sfmelp.exception.database.DatabaseException;
import org.amileshin.sfmelp.exception.database.DatabaseSQLExecuteException;
import org.amileshin.sfmelp.model.dto.ConnectionDB;
import org.amileshin.sfmelp.utils.ComposingUtils;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClickHouseManager {
    private static final String DATABASE = "ClickHouse";

    public Connection getConnectionToDatabase(ConnectionDB info) throws DatabaseException {
        try {
            Connection con;
            if (info.getUsername() == null || info.getPassword() == null) {
                con = DriverManager.getConnection(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info));
                log.info("Connect to database {} successfully",
                        ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info));
            } else {
                con = DriverManager.getConnection(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info),
                        info.getUsername(), info.getPassword());
                log.info("Connect to database {} by user {} successfully",
                        ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info), info.getUsername());
            }
            return con;
        } catch (SQLException e) {
            log.error("Failed to get database {} connection: {}",
                    ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info), e.getMessage());
            throw new DatabaseAccessException(DATABASE, ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info));
        }
    }

    private Statement getStatementFromConnection(ConnectionDB info, Connection connection) throws DatabaseAccessException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            log.error("Failed to get database {} access: {}",
                    ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info), e.getMessage());
            throw new DatabaseAccessException(DATABASE, ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info));
        }
    }

    private long getTimeForLastQuery(Statement statement, String db, String table, String operation) throws SQLException {
        ResultSet set = statement.executeQuery(ComposingUtils.getSQLRequestToQueryTime(db, table, operation));
        set.next();
        long res = set.getLong(1);
        set.close();
        return res;
    }

    public long getTimeForLoadLogsToDatabase(List<String> logs, String table, ConnectionDB info) throws DatabaseConnectException, InterruptedException {
        try {
            Connection con = getConnectionToDatabase(info);
            Statement statement = this.getStatementFromConnection(info, con);
            statement.execute(ComposingUtils.getSQLRequestToClearDatabase(info.getDatabase(), table));

            String sql = ComposingUtils.getSQLRequestToLoadLogToDatabase(info.getDatabase(), table, logs);

            statement.executeUpdate(sql);
            Thread.sleep(500);
            long time = this.getTimeForLastQuery(statement, info.getDatabase(), table, "insert");

            statement.close();
            con.close();
            return time;
        } catch (SQLException e) {
            log.error("Failed to execute request in database {}, message: {}",
                    ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info), e.getMessage());
            throw new DatabaseSQLExecuteException(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info),
                    e.getMessage());
        }
    }

    public long getTimeForLoadLogsFromDatabase(String table, ConnectionDB info) throws DatabaseConnectException, InterruptedException {
        try {
            Connection con = getConnectionToDatabase(info);
            Statement statement = this.getStatementFromConnection(info, con);
            statement.execute(ComposingUtils.CLEAR_DATABASE_CACHE);


            statement.executeQuery(ComposingUtils.getSQLRequestToLoadLogFromDatabase(info.getDatabase(), table));
            Thread.sleep(500);
            long time = this.getTimeForLastQuery(statement, info.getDatabase(), table, "select");

            statement.close();
            con.close();
            return time;
        } catch (SQLException e) {
            log.error("Failed to execute request in database {}, message: {}",
                    ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info), e.getMessage());
            throw new DatabaseSQLExecuteException(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info),
                    e.getMessage());
        }
    }

    public long getDatabaseByteSize(String tableName, ConnectionDB info) throws DatabaseException {
        try {
            Connection con = getConnectionToDatabase(info);
            Statement statement = this.getStatementFromConnection(info, con);

            statement.execute(ComposingUtils.CLEAR_DATABASE_CACHE);
            ResultSet set = statement.executeQuery(ComposingUtils.getSQLRequestToGetByteForTable(info.getDatabase(),
                    tableName));

            long bytes = 0;
            while (set.next()) {
                bytes = set.getLong(1);
            }
            set.close();
            statement.close();
            con.close();
            return bytes;
        } catch (SQLException e) {
            log.error("Failed to execute request in database {}, message: {}",
                    ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info), e.getMessage());
            throw new DatabaseSQLExecuteException(ComposingUtils.getDatabaseUrlFromConnectInfoDTO(DATABASE, info),
                    e.getMessage());
        }
    }
}
