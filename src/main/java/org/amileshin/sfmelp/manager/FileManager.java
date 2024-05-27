package org.amileshin.sfmelp.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amileshin.sfmelp.config.TestFileConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

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
            return testFile.length();
        } catch (FileNotFoundException e) {
            log.error("Test file cann't be load: {}", e.getMessage());
            return 0;
        }
    }
}
