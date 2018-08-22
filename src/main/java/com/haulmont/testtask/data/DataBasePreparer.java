package com.haulmont.testtask.data;

import com.haulmont.testtask.exceptions.MedicamentsSystemException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DataBasePreparer extends DAO {

    private static final String PATIENT_TABLE_CREATE_QUERY = "CREATE TABLE patient " +
            "(patient_id BIGINT PRIMARY KEY NOT NULL, " +
            "name VARCHAR(50) NOT NULL," +
            "family_name VARCHAR(50)," +
            "second_name VARCHAR(50), " +
            "phone VARCHAR(16))";

    private static final String DOCTOR_SPECIALIZATION_TABLE_CREATE_QUERY  = "CREATE TABLE specialization (" +
            "specialization_id BIGINT PRIMARY KEY NOT NULL," +
            "name VARCHAR(75) NOT NULL" +
            ")";

    private static final String RECIPE_PRIORITY_TABLE_CREATE_QUERY = "CREATE TABLE recipe_priority (" +
            "priority_id BIGINT PRIMARY KEY NOT NULL, " +
            "name VARCHAR(6) NOT NULL" +
            ")";

    private static final String  DOCTOR_TABLE_CREATE_QUERY = "CREATE TABLE doctor (" +
            "doctor_id BIGINT PRIMARY KEY NOT NULL, " +
            "name VARCHAR(50) NOT NULL," +
            "family_name VARCHAR(50), " +
            "second_name VARCHAR(50), " +
            "specialization_id BIGINT REFERENCES specialization(specialization_id)" +
            ")";

    private static final String RECIPE_TABLE_CREATE_QUERY = "CREATE TABLE recipe (" +
            "recipe_id BIGINT PRIMARY KEY NOT NULL, " +
            "desc VARCHAR(300)," +
            "patient_id BIGINT REFERENCES patient(patient_id), " +
            "doctor_id BIGINT REFERENCES doctor(doctor_id), " +
            "start_date DATE NOT NULL, " +
            "duration SMALLINT, " +
            "priority_id BIGINT REFERENCES recipe_priority(priority_id)" +
            ")";

    private void createTable(String tableQuery) throws MedicamentsSystemException {
        Connection connection = getConnection();
        try {
            connection.createStatement().executeQuery(tableQuery);
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            connectionClose(connection);
        }
    }

    //todo рассмотреть кейсы: отсутствия базы, отсутствия нескольких таблиц, наличия полной базы
    public void createDB() throws MedicamentsSystemException {
        createTable(PATIENT_TABLE_CREATE_QUERY);
        createTable(DOCTOR_SPECIALIZATION_TABLE_CREATE_QUERY);
        createTable(RECIPE_PRIORITY_TABLE_CREATE_QUERY);
        createTable(DOCTOR_TABLE_CREATE_QUERY);
        createTable(RECIPE_TABLE_CREATE_QUERY);
    }

    public void filling() {
        Connection connection = getConnection();
        try {
            connection.createStatement().executeQuery("INSERT INTO patient(patient_id, name, family_name) values(0,'Николай', 'Складнев')");
            connection.createStatement().executeQuery("INSERT INTO specialization(specialization_id, name) values(0,'Хирург'),(1, 'Терапевт')");
            connection.createStatement().executeQuery("INSERT INTO doctor(doctor_id, name, family_name, specialization_id) values(0,'Олег', 'Сонов', 1), (1, 'Кирил', 'Волков', 0)");
            connection.createStatement().executeQuery("INSERT INTO recipe_priority(priority_id, name) values(0,'normal'), (1, 'high')");
            connection.createStatement().executeQuery("INSERT INTO recipe(recipe_id, desc, patient_id, doctor_id, start_date, priority_id) values(0,'Что-то от хирурга', 0, 1, '1995-12-19', 1)");
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            connectionClose(connection);
        }
    }

    public void show() {
        Connection connection = getConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("Select recipe.desc, doctor.name, recipe.start_date, specialization.name " +
                    "From doctor join recipe on doctor.doctor_id=recipe.doctor_id join specialization on doctor.specialization_id=specialization.specialization_id");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+ " "+ resultSet.getString(3) + " " + resultSet.getString(4));
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            connectionClose(connection);
        }
    }
}
