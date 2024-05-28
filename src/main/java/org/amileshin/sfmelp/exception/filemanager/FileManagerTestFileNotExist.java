package org.amileshin.sfmelp.exception.filemanager;

public class FileManagerTestFileNotExist extends RuntimeException {
    public FileManagerTestFileNotExist(String file) {
        super("In test directory file not exist: " + file);
    }
}
