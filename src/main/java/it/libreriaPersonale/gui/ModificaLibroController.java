package it.libreriaPersonale.gui;

import it.libreriaPersonale.builder.ConcreteBuilder;
import it.libreriaPersonale.controller.LibroController;
import it.libreriaPersonale.dto.LibroDTO;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModificaLibroController {

    @FXML private TextField campoTitolo;
    @FXML private TextField campoAutore;
    @FXML private TextField campoGenere;
    @FXML private TextField campoIsbn;
    @FXML private ComboBox<StatoLettura> comboStato;
    @FXML private Spinner<Integer> spinnerValutazione;
    @FXML private TextField campoCopertinaUrl;

    private final LibroController controller = new LibroController();
    private Libro libroDaModificare;

    public void setLibro(Libro libro) {
        this.libroDaModificare = libro;
        campoTitolo.setText(libro.getTitolo());
        campoAutore.setText(libro.getAutore());
        campoGenere.setText(libro.getGenere());
        campoIsbn.setText(libro.getIsbn());
        comboStato.setValue(libro.getStatoLettura());
        spinnerValutazione.getValueFactory().setValue(libro.getValutazione());
        campoCopertinaUrl.setText(libro.getCopertinaUrl());
    }

    @FXML public void initialize() {
        comboStato.setItems(FXCollections.observableArrayList(StatoLettura.values()));
        comboStato.getSelectionModel().selectFirst();
        spinnerValutazione.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,3)
        );
    }

    @FXML private void handleSalva() {
        System.err.println("[Controller] handleSalva() modifica invoked");

        // Aggiorna usando ConcreteBuilder sul libro esistente
        ConcreteBuilder builder = new ConcreteBuilder(libroDaModificare);
        builder
                .setTitolo(campoTitolo.getText())
                .setAutore(campoAutore.getText())
                .setGenere(campoGenere.getText())
                .setIsbn(campoIsbn.getText())
                .setStatoLettura(comboStato.getValue())
                .setValutazione(spinnerValutazione.getValue())
                .setCopertinaUrl(
                        campoCopertinaUrl.getText().trim().isEmpty() ? null
                                : campoCopertinaUrl.getText().trim()
                )
                .build();

        controller.gestisciAggiornamentoLibro(libroDaModificare);
        chiudiFinestra();
    }

    @FXML private void handleAnnulla() {
        chiudiFinestra();
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) campoTitolo.getScene().getWindow();
        stage.close();
    }
}