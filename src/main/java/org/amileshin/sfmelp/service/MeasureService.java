package org.amileshin.sfmelp.service;

import lombok.RequiredArgsConstructor;
import org.amileshin.sfmelp.manager.FileManager;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MeasureService {
    private final FileManager fileManager;
    private final DatabaseService databaseService;

    public long getFileSize() {
        return fileManager.getTestFileSize();
    }
}
