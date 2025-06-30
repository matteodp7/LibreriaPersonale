package it.libreriaPersonale.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public enum DatabaseSingleton {
    INSTANCE;  // unica istanza singleton

    private Connection connection;

    /**
     * Restituisce la connessione al database, creandola al primo accesso.
     */
    public synchronized Connection getConnection() {
        if (connection != null && !isClosed(connection)) {
            return connection;
        }

        try (InputStream input = DatabaseSingleton.class.getClassLoader()
                .getResourceAsStream("config/database.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Impossibile trovare il file database.properties nel classpath");
            }

            Properties prop = new Properties();
            prop.load(input);

            String url      = prop.getProperty("db.url");
            String user     = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            connection = DriverManager.getConnection(url, user, password);
            System.out.println(" Connessione al database riuscita.");
        } catch (Exception e) {
            throw new RuntimeException("Errore apertura connessione database", e);
        }

        return connection;
    }

    /**
     * Chiude la connessione, se aperta.
     */
    public synchronized void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println(" Connessione al database chiusa.");
            } catch (Exception e) {
                System.err.println(" Errore chiusura connessione: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }

    /**
     * Verifica se la connessione è chiusa o nulla.
     */
    private boolean isClosed(Connection conn) {
        try {
            return conn.isClosed();
        } catch (Exception e) {
            return true;
        }
    }
}
