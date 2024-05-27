package org.amileshin.sfmelp.service;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.manager.FileManager;
import org.amileshin.sfmelp.model.dto.ConnectInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MeasureService {
    private final FileManager fileManager;
    private final DatabaseService databaseService;

    public long getFileSize() {
        return fileManager.getTestFileSize();
    }

    public List<String> getLogsFromFile() {
        return fileManager.loadLogsForTest();
    }

    public void loadLogsToDatabase(ConnectInfoDTO info, String table) {
        databaseService.loadLogsToDatabase(info, table, this.getLogsFromFile());
    }
}
