package org.amileshin.sfmelp.utils;

import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;

public class ComposingUtils {
    public static String getDatabaseUrlFromConnectInfoDTO(String databaseName, ConnectInfoDTO dto) {
        return "jdbc:" + databaseName.toLowerCase() + "://" + dto.getHost() + ":" + dto.getPort()
                + (dto.getDatabase().isBlank()? "/" + dto.getDatabase() : "");
    }
}
