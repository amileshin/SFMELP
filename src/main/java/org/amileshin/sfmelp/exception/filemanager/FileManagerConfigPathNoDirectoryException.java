package org.amileshin.sfmelp.exception.filemanager;

public class FileManagerConfigPathNoDirectoryException extends RuntimeException {
    public FileManagerConfigPathNoDirectoryException(String file) {
        super("The file " + file + " is not a directory.");
    }
}
