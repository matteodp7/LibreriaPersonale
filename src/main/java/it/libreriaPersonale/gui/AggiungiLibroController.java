package it.libreriaPersonale.gui;

import it.libreriaPersonale.builder.ConcreteBuilder;
import it.libreriaPersonale.builder.DirectorLibro;
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
    @FXML private TextField campoCopertinaUrl;

    private final LibroController controller = new LibroController();

    @FXML public void initialize() {
        comboStato.setItems(FXCollections.observableArrayList(StatoLettura.values()));
        comboStato.getSelectionModel().selectFirst();
        spinnerValutazione.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,3)
        );
    }

    @FXML private void handleSalva() {
        System.err.println("[Controller] handleSalva() invoked");

        // Costruzione tramite Builder
        ConcreteBuilder builder = new ConcreteBuilder();
        DirectorLibro director = new DirectorLibro(builder);

        String url = campoCopertinaUrl.getText().trim().isEmpty()
                ? null : campoCopertinaUrl.getText().trim();

        Libro nuovoLibro = director.costruisciLibroCompleto(
                campoTitolo.getText(),
                campoAutore.getText(),
                campoGenere.getText(),
                campoIsbn.getText(),
                comboStato.getValue(),
                spinnerValutazione.getValue(),
                url
        );

        controller.gestisciAggiuntaLibro(nuovoLibro);
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