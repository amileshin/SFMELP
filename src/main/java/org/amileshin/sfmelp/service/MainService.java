package org.amileshin.sfmelp.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.exception.DatabaseConnectException;
import org.amileshin.sfmelp.manager.ClickHouseManager;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class MainService {
    private final ClickHouseManager clickHouseManager;

    public boolean getTestConnect(ConnectInfoDTO info) {
        try {
            clickHouseManager.getTestConnection(info);
            return true;
        } catch (DatabaseConnectException e) {
            return false;
        }
    }

    public @Nullable List<String> getAllTablesFromDatabase(ConnectInfoDTO info) {
        try {
            clickHouseManager.getTestConnection(info);
        } catch (DatabaseConnectException e) {
            return null;
        }

        return clickHouseManager.listDatabases(info);
    }
}
