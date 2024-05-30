package org.amileshin.sfmelp.exception.database;

public class DatabaseSQLExecuteException extends DatabaseException {
    public DatabaseSQLExecuteException(String databaseUrl, String sqlException) {
        super("Failed sql request to database " + databaseUrl + ", message: " + sqlException);
    }
}
