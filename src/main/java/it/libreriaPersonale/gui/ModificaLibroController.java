package it.libreriaPersonale.gui;

import it.libreriaPersonale.controller.LibroController;
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
    @FXML private TextField campoCopertinaUrl; // nuovo campo per URL copertina

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

    @FXML
    public void initialize() {
        // Popolo il ComboBox con i valori dell'enum
        comboStato.setItems(FXCollections.observableArrayList(StatoLettura.values()));
        comboStato.getSelectionModel().selectFirst(); // Default: DA_LEGGERE

        spinnerValutazione.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3));
    }

    @FXML
    private void handleSalva() {
        libroDaModificare.setTitolo(campoTitolo.getText());
        libroDaModificare.setAutore(campoAutore.getText());
        libroDaModificare.setGenere(campoGenere.getText());
        libroDaModificare.setIsbn(campoIsbn.getText());
        libroDaModificare.setStatoLettura(comboStato.getValue());
        libroDaModificare.setValutazione(spinnerValutazione.getValue());
        libroDaModificare.setCopertinaUrl(campoCopertinaUrl.getText().trim().isEmpty() ? null : campoCopertinaUrl.getText().trim());

        controller.gestisciAggiornamentoLibro(libroDaModificare);
        chiudiFinestra();
    }

    @FXML
    private void handleAnnulla() {
        chiudiFinestra();
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) campoTitolo.getScene().getWindow();
        stage.close();
    }
}
