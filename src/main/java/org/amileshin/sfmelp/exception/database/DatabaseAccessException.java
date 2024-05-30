package org.amileshin.sfmelp.exception.database;

public class DatabaseAccessException extends DatabaseException {
    public DatabaseAccessException(String databaseName, String databaseUrl) {
        super("Failed to access to the database " + databaseName + " with url " + databaseUrl);
    }
}
