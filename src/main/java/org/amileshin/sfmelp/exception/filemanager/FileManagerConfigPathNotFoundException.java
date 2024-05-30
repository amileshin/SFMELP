package org.amileshin.sfmelp.exception.filemanager;

public class FileManagerConfigPathNotFoundException extends FileManagerException {
    public FileManagerConfigPathNotFoundException(String file) {
        super("Directory for test logs " + file + " not found.");
    }
}
