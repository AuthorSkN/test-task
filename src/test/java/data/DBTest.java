package data;

import com.google.gwt.thirdparty.guava.common.annotations.VisibleForTesting;
import org.junit.Test;

import java.sql.*;

public class DBTest {

    @Test
    public void dbTest() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("НЕ удалось загрузить драйвер ДБ.");
            e.printStackTrace();
            System.exit(1);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:database/Medicaments", "admin", "");
        } catch (SQLException e) {
            System.err.println("НЕ удалось оздать соединение.");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            Statement statement = connection.createStatement();
            // создаем таблицу со столбцами id и value.
            String query = "CREATE TABL mytable (id IDENTITY , value VARCHAR(32))";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                // если таблица уже существует, будет исключение, игнорируем его.
                int a = 0;
            }
            // добавляю записи в таблицу.
            query = "INSERT INTO mytable (value) VALUES('Киев')";
            statement.executeUpdate(query);
            query = "INSERT INTO mytable (value) VALUES('Минск')";
            statement.executeUpdate(query);
            query = "INSERT INTO mytable (value) VALUES('Москва')";
            statement.executeUpdate(query);

            // достаю записи из таблицы
            query = "SELECT id, value FROM mytable";
            ResultSet resultSet = ((Statement) statement).executeQuery(query);

            // распечатываю
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " "
                        + resultSet.getString(2));
            }

            //отключаюсь от БД
            query = "SHUTDOWN";
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
