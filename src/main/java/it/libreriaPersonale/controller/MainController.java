package it.libreriaPersonale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    public void onVisualizzaLibri(ActionEvent event) {
        System.out.println("Visualizza Libri premuto");
    }

    @FXML
    public void onAggiungiLibro(ActionEvent event) {
        System.out.println("Aggiungi Libro premuto");
    }
}
