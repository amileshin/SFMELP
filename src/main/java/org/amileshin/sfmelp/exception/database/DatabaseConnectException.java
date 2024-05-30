package org.amileshin.sfmelp.exception.database;

public class DatabaseConnectException extends DatabaseException {
    public DatabaseConnectException(String databaseName, String databaseUrl) {
        super("Failed to connect to the database " + databaseName + " with url " + databaseUrl);
    }
}
