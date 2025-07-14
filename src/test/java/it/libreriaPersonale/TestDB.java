package it.libreriaPersonale;

import it.libreriaPersonale.config.DatabaseSingleton;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = DatabaseSingleton.INSTANCE.getConnection();

        if (conn != null) {
            System.out.println(" Connessione ottenuta correttamente.");
        } else {
            System.out.println(" Connessione fallita.");
        }

        DatabaseSingleton.INSTANCE.closeConnection();
    }
}
