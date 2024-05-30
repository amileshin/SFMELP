package org.amileshin.sfmelp.exception.filemanager;

public class FileManagerTestFileUnreadableException extends FileManagerException {
    public FileManagerTestFileUnreadableException(String file) {
        super("File " + file + " is not readable");
    }
}
