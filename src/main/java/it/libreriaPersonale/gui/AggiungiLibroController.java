package it.libreriaPersonale.gui;

import it.libreriaPersonale.controller.LibroController;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AggiungiLibroController {

    @FXML private TextField campoTitolo;
    @FXML private TextField campoAutore;
    @FXML private TextField campoGenere;
    @FXML private TextField campoIsbn;
    @FXML private ComboBox<StatoLettura> comboStato;
    @FXML private Spinner<Integer> spinnerValutazione;
    @FXML private TextField campoCopertinaUrl; // nuovo campo per URL copertina

    private final LibroController controller = new LibroController();

    @FXML
    public void initialize() {
        // Popolo il ComboBox con i valori dell'enum
        comboStato.setItems(FXCollections.observableArrayList(StatoLettura.values()));
        comboStato.getSelectionModel().selectFirst(); // Default: DA_LEGGERE

        spinnerValutazione.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3));
    }

    @FXML
    private void handleSalva() {
        Libro nuovoLibro = new Libro(
                campoTitolo.getText(),
                campoAutore.getText(),
                campoGenere.getText(),
                campoIsbn.getText(),
                comboStato.getValue(),
                spinnerValutazione.getValue(),
                campoCopertinaUrl.getText().trim().isEmpty() ? null : campoCopertinaUrl.getText().trim()
        );

        controller.gestisciAggiuntaLibro(nuovoLibro);
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
