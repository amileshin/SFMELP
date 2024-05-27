package org.amileshin.sfmelp.utils;

import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ComposingUtils {
    private ComposingUtils() {}

    public static String getDatabaseUrlFromConnectInfoDTO(String databaseName, ConnectInfoDTO dto) {
        return "jdbc:" + databaseName.toLowerCase() + "://" + dto.getHost() + ":" + dto.getPort()
                + (dto.getDatabase().isBlank()? "" : "/" + dto.getDatabase());
    }

    public static String getSQLRequestToLoadLogToDatabase(String database, String table, List<String> logs) {
        return "INSERT INTO " + database + "." + table + " VALUES " +
                logs.stream()
                        .map(log -> "('" + log.replace("'", "\\'") + "')")
                        .collect(Collectors.joining(","));
    }

    public static String getSQLRequestToLoadLogFromDatabase(String database, String table) {
        return "SELECT name FROM " + database + "." + table;
    }

    public static String getSQLRequestToGetByteForTable(String database, String table) {
        return "SELECT total_bytes FROM system.tables WHERE database = '" + database + "' AND name = '" + table + "'";
    }

    public static String getSQLRequestToClearDatabaseCache() {
        return "SYSTEM DROP UNCOMPRESSED CACHE";
    }
}
