package it.libreriaPersonale.gui;

import it.libreriaPersonale.builder.ConcreteBuilder;
import it.libreriaPersonale.builder.DirectorLibro;
import it.libreriaPersonale.controller.LibroController;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.repository.LibroRepository;
import it.libreriaPersonale.service.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class LibroGUIController {

    @FXML private TableView<Libro> tabellaLibri;
    @FXML private TableColumn<Libro, String> colTitolo;
    @FXML private TableColumn<Libro, String> colAutore;
    @FXML private TableColumn<Libro, String> colonnaStato;
    @FXML private TableColumn<Libro, String> colonnaGenere;
    @FXML private TableColumn<Libro, Integer> colonnaValutazione;
    @FXML private TableColumn<Libro, String> colCopertina;

    @FXML private TextField filterTitolo;
    @FXML private TextField filterAutore;
    @FXML private TextField filterGenere;
    @FXML private ComboBox<String> filterStato;
    @FXML private ComboBox<Integer> filterValutazione;
    @FXML private TextField searchBar;

    private ObservableList<Libro> libriList;
    private LibroController controller;
    private LibroService service;
    private LibroRepository repository;
    private CSVImporter csvImporter;
    private JSONImporter jsonImporter;

    @FXML
    public void initialize() {
        controller    = new LibroController();
        service = new LibroService(repository);
        repository= new LibroRepository();
        csvImporter   = new CSVImporter(controller.getLibroRepository());
        jsonImporter   = new JSONImporter(service, repository);

        // Colonne tabella
        colTitolo.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTitolo()));
        colAutore.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAutore()));
        colonnaGenere.setCellValueFactory(new PropertyValueFactory<>("genere"));
        colonnaValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));
        colonnaStato.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getStatoLettura().toString())
        );

        colCopertina.setCellValueFactory(data ->
                new ReadOnlyStringWrapper(data.getValue().getCopertinaUrl())
        );
        colCopertina.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(60);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(true);
            }
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);
                if (empty || url == null || url.isBlank()) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(url, true));
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });

        // Carico dati
        libriList = FXCollections.observableArrayList(controller.gestisciElencoLibri());
        tabellaLibri.setItems(libriList);

        // Inizializza filtri
        filterTitolo.clear();
        filterAutore.clear();
        filterGenere.clear();

        filterStato.setItems(FXCollections.observableArrayList("Tutti"));
        for (StatoLettura s: StatoLettura.values()) {
            filterStato.getItems().add(s.toString());
        }
        filterStato.getSelectionModel().selectFirst();

        filterValutazione.setItems(FXCollections.observableArrayList(0,1,2,3,4,5));
        filterValutazione.getSelectionModel().select(0);
    }

    @FXML
    private void handleAggiungi() {
        Dialog<Libro> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Nuovo Libro");
        ButtonType btnAggiungi = new ButtonType("Aggiungi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(btnAggiungi, ButtonType.CANCEL);

        // Campi dialog
        TextField titoloF = new TextField();
        TextField autoreF = new TextField();
        TextField genereF = new TextField();
        TextField isbnF   = new TextField();
        ComboBox<StatoLettura> statoC = new ComboBox<>(FXCollections.observableArrayList(StatoLettura.values()));
        statoC.setValue(StatoLettura.DA_LEGGERE);
        Spinner<Integer> valSpinner = new Spinner<>(0,5,0);
        TextField urlF = new TextField();
        urlF.setPromptText("URL Copertina");

        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Titolo:"),0,0);       grid.add(titoloF,1,0);
        grid.add(new Label("Autore:"),0,1);      grid.add(autoreF,1,1);
        grid.add(new Label("Genere:"),0,2);      grid.add(genereF,1,2);
        grid.add(new Label("ISBN:"),0,3);        grid.add(isbnF,1,3);
        grid.add(new Label("Stato Lettura:"),0,4);grid.add(statoC,1,4);
        grid.add(new Label("Valutazione:"),0,5); grid.add(valSpinner,1,5);
        grid.add(new Label("URL Copertina:"),0,6);grid.add(urlF,1,6);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(b -> {
            if (b == btnAggiungi) {
                ConcreteBuilder builder = new ConcreteBuilder();
                DirectorLibro director = new DirectorLibro(builder);
                String url = urlF.getText().trim().isEmpty() ? null : urlF.getText().trim();
                return director.costruisciLibroCompleto(
                        titoloF.getText(),
                        autoreF.getText(),
                        genereF.getText(),
                        isbnF.getText(),
                        statoC.getValue(),
                        valSpinner.getValue(),
                        url
                );
            }
            return null;
        });

        boolean success = false;
        while (!success) {
            var res = dialog.showAndWait();
            if (res.isEmpty()) break;
            Libro l = res.get();
            if (controller.gestisciAggiuntaLibro(l)) {
                libriList.setAll(controller.gestisciElencoLibri());
                mostraAlertInfo(" Libro aggiunto con successo!");
                success = true;
            } else {
                mostraAlertWarning(" ISBN duplicato, riprova.");
            }
        }
    }

    @FXML
    private void handleModifica() {
        Libro sel = tabellaLibri.getSelectionModel().getSelectedItem();
        if (sel == null) {
            mostraAlertWarning("Seleziona un libro da modificare.");
            return;
        }

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Modifica Libro");
        ButtonType btnSave = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().setAll(btnSave, ButtonType.CANCEL);

        // Prepopolo
        TextField titoloF = new TextField(sel.getTitolo());
        TextField autoreF = new TextField(sel.getAutore());
        TextField genereF = new TextField(sel.getGenere());
        TextField isbnF   = new TextField(sel.getIsbn());
        ComboBox<StatoLettura> statoC = new ComboBox<>(FXCollections.observableArrayList(StatoLettura.values()));
        statoC.setValue(sel.getStatoLettura());
        Spinner<Integer> valSpinner = new Spinner<>(0,5, sel.getValutazione());
        TextField urlF = new TextField(sel.getCopertinaUrl());

        GridPane grid = new GridPane(); grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Titolo:"),0,0);       grid.add(titoloF,1,0);
        grid.add(new Label("Autore:"),0,1);      grid.add(autoreF,1,1);
        grid.add(new Label("Genere:"),0,2);      grid.add(genereF,1,2);
        grid.add(new Label("ISBN:"),0,3);        grid.add(isbnF,1,3);
        grid.add(new Label("Stato Lettura:"),0,4);grid.add(statoC,1,4);
        grid.add(new Label("Valutazione:"),0,5); grid.add(valSpinner,1,5);
        grid.add(new Label("URL Copertina:"),0,6);grid.add(urlF,1,6);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(b -> {
            if (b == btnSave) {
                new ConcreteBuilder(sel)
                        .setTitolo(titoloF.getText())
                        .setAutore(autoreF.getText())
                        .setGenere(genereF.getText())
                        .setIsbn(isbnF.getText())
                        .setStatoLettura(statoC.getValue())
                        .setValutazione(valSpinner.getValue())
                        .setCopertinaUrl(urlF.getText().trim().isEmpty() ? null : urlF.getText().trim())
                        .build();
                controller.gestisciAggiornamentoLibro(sel);
            }
            return null;
        });

        dialog.showAndWait();
        libriList.setAll(controller.gestisciElencoLibri());
    }

    @FXML
    private void handleElimina() {
        Libro sel = tabellaLibri.getSelectionModel().getSelectedItem();
        if (sel != null) {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText(null);
            a.setContentText("Eliminare \"" + sel.getTitolo() + "\"?");
            a.showAndWait().ifPresent(r -> {
                if (r == ButtonType.OK) {
                    controller.gestisciEliminazioneLibro(sel.getId());
                    libriList.setAll(controller.gestisciElencoLibri());
                }
            });
        } else {
            mostraAlertWarning("Seleziona un libro da eliminare.");
        }
    }

    @FXML
    private void handleApplicaFiltro() {
        String t = filterTitolo.getText().toLowerCase().trim();
        String a = filterAutore.getText().toLowerCase().trim();
        String g = filterGenere.getText().toLowerCase().trim();
        String s = filterStato.getValue();
        int v = filterValutazione.getValue();

        List<Libro> res = controller.gestisciElencoLibri().stream()
                .filter(l -> l.getTitolo().toLowerCase().contains(t))
                .filter(l -> l.getAutore().toLowerCase().contains(a))
                .filter(l -> l.getGenere().toLowerCase().contains(g))
                .filter(l -> s.equals("Tutti") || l.getStatoLettura().toString().equals(s))
                .filter(l -> l.getValutazione() >= v)
                .collect(Collectors.toList());

        libriList.setAll(res);
    }

    @FXML
    private void handleResetFiltri() {
        filterTitolo.clear();
        filterAutore.clear();
        filterGenere.clear();
        filterStato.getSelectionModel().selectFirst();
        filterValutazione.getSelectionModel().select(0);
        libriList.setAll(controller.gestisciElencoLibri());
    }

    @FXML
    private void handleRicerca() {
        libriList.setAll(controller.gestisciRicerca(searchBar.getText()));
    }

    @FXML
    private void handleEsportaCSV() {
        try {
            CSVExporter.esporta(controller.gestisciElencoLibri(), "libri.csv");
            mostraAlertInfo("Esportazione completata!");
        } catch (Exception e) {
            mostraAlertWarning("Errore export: " + e.getMessage());
        }
    }

    @FXML
    private void handleImportaCSV() {
        try {
            csvImporter.importaLibriDaCsv("libri.csv");
            libriList.setAll(controller.gestisciElencoLibri());
            mostraAlertInfo("Importazione completata!");
        } catch (Exception e) {
            mostraAlertWarning("Errore import: " + e.getMessage());
        }
    }

    @FXML
    private void handleEsportaJSON() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva libreria come JSON");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

        File file = fileChooser.showSaveDialog(tabellaLibri.getScene().getWindow());
        if (file != null) {
            try {
                JSONExporter.esportaJson(file, controller.gestisciElencoLibri());
                mostraAlertInfo("Esportazione JSON completato!");
            } catch (Exception e) {
                mostraAlertWarning("Errore esportazione JSON: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleImportaJSON() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Apri file JSON per importare");
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

            File file = fileChooser.showOpenDialog(tabellaLibri.getScene().getWindow());
            if (file != null) {
                try {
                    jsonImporter.importaJson(file);
                    } catch (Exception e) {
                    mostraAlertWarning("Errore importazione JSON: " + e.getMessage());

                }
            libriList.setAll(controller.gestisciElencoLibri());
            mostraAlertInfo("Importazione JSON completato!");
        }
    }


    private void mostraAlertWarning(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
    private void mostraAlertInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
