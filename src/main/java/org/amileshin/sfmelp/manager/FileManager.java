package org.amileshin.sfmelp.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amileshin.sfmelp.config.TestFileConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class FileManager {
    private final TestFileConfig fileConfig;

    public File getTestFile() throws FileNotFoundException {
        return ResourceUtils.getFile(fileConfig.getPath() + fileConfig.getFile());
    }

    public long getTestFileSize() {
        try {
            File testFile = this.getTestFile();
            return new String(Files.readAllBytes(testFile.toPath())).replace("\n", "").length();
        } catch (IOException e) {
            log.error("Test file can't be load: {}", e.getMessage());
            return 0;
        }
    }

    public List<String> loadLogsForTest()  {
        try {
            return Files.readAllLines(this.getTestFile().toPath());
        } catch (IOException e) {
            log.error("Test file can't be load: {}", e.getMessage());
            return null;
        }
    }
}
