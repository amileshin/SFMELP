package org.amileshin.sfmelp.exception;

public class DatabaseConnectException extends RuntimeException {
    public DatabaseConnectException(String databaseUrl) {
        super("Failed to connect to the database with url " + databaseUrl);
    }

    public DatabaseConnectException(String databaseName, String databaseUrl) {
        super("Failed to connect to the database " + databaseName + " with url " + databaseUrl);
    }
}
