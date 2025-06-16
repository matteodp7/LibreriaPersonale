package it.libreriaPersonale.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseManager {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) return connection;

        try (InputStream input = DatabaseManager.class.getClassLoader()
                .getResourceAsStream("config/database.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connessione al database riuscita.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
