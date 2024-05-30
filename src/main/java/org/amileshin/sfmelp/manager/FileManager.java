package org.amileshin.sfmelp.manager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.amileshin.sfmelp.config.TestFilesPathConfig;
import org.amileshin.sfmelp.exception.filemanager.FileManagerConfigPathNoDirectoryException;
import org.amileshin.sfmelp.exception.filemanager.FileManagerTestFileUnreadableException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class FileManager {
    private final TestFilesPathConfig fileConfig;
    @Getter
    private final List<String> files;

    @Autowired
    public FileManager(TestFilesPathConfig fileConfig) {
        this.fileConfig = fileConfig;
        File path = new File(fileConfig.getPath());
        if (!path.exists()) {
            log.error("Path for test logs {} not found", fileConfig.getPath());
            throw new FileManagerConfigPathNoDirectoryException(fileConfig.getPath());
        } else if (path.isDirectory()) {
            log.info("Loading files from {}", fileConfig.getPath());
            this.files = Arrays.stream(Objects.requireNonNull(path.listFiles()))
                    .filter(File::isFile)
                    .map(File::getName)
                    .toList();
            log.info("Test files with logs {} loaded", Strings.join(files, ','));
        } else {
            log.error("Path for test logs {} is not a directory", fileConfig.getPath());
            throw new FileManagerConfigPathNoDirectoryException(fileConfig.getPath());
        }
    }

    public File getFile(String fileName) throws FileManagerConfigPathNoDirectoryException {
        if (!files.contains(fileName)) {
            log.error("File {} not exist in test files directory {}", fileName, fileConfig.getPath());
            throw new FileManagerConfigPathNoDirectoryException(fileConfig.getPath());
        } else {
            return new File(fileConfig.getPath() + fileName);
        }
    }

    public long getFileSize(String fileName) throws FileManagerConfigPathNoDirectoryException {
        try {
            File file = this.getFile(fileName);
            return new String(Files.readAllBytes(file.toPath())).replace("\n", "").length();
        } catch (IOException e) {
            log.error("File {} in test files directory {} is not readable", fileName, fileConfig.getPath());
            throw new FileManagerTestFileUnreadableException(fileConfig.getPath());
        }
    }

    public List<String> getStringFromFile(String fileName) throws FileManagerConfigPathNoDirectoryException {
        try {
            File file = this.getFile(fileName);
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            log.error("File {} in test files directory {} is not readable", fileName, fileConfig.getPath());
            throw new FileManagerTestFileUnreadableException(fileConfig.getPath());
        }
    }
}
