package it.libreriaPersonale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    public void onVisualizzaLibri(ActionEvent event) {
        System.out.println("Visualizza Libri premuto");
        // Qui potrai caricare una nuova vista o interagire con i dati
    }

    @FXML
    public void onAggiungiLibro(ActionEvent event) {
        System.out.println("Aggiungi Libro premuto");
        // Qui potrai aprire una finestra per aggiungere un nuovo libro
    }
}
