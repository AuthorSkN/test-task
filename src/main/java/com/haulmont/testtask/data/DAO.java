package com.haulmont.testtask.data;

import com.haulmont.testtask.exceptions.MedicamentsSystemException;

import javax.sql.ConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {

    private static final String DB_PATH = "jdbc:hsqldb:file:database/Medicaments";
    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "%22authoR61";

    private static final String SHUTDOWN_QUERY = "SHUTDOWN";

    protected Connection getConnection() throws MedicamentsSystemException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_PATH, USER_NAME, PASSWORD);
        } catch (SQLException exc) {
            System.err.println("Attempt connecting to database is failed.");
            exc.printStackTrace();
            throw new MedicamentsSystemException();
        }
        return connection;
    }

    protected void connectionClose(Connection connection) throws MedicamentsSystemException {
        try {
            connection.createStatement().executeQuery(SHUTDOWN_QUERY);
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
            throw new MedicamentsSystemException();
        }
    }
}
