package it.libreriaPersonale.gui;

import it.libreriaPersonale.controller.LibroController;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.service.CsvExporter;
import it.libreriaPersonale.service.CsvImporter;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
    private CsvImporter csvImporter;


    @FXML
    public void initialize() {
        controller = new LibroController();
        csvImporter = new CsvImporter(controller.getLibroDAO());
        colCopertina.setCellValueFactory(data -> {
            String url = data.getValue().getCopertinaUrl();
            return new ReadOnlyStringWrapper(url);
        });
        colCopertina.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(60);
                imageView.setFitHeight(80);
                imageView.setPreserveRatio(true);
            }
            @Override
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);
                if (empty || url == null || url.isBlank()) {
                    setGraphic(null);
                } else {
                    try {
                        Image img = new Image(url, true);
                        imageView.setImage(img);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });
        colTitolo.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTitolo()));
        colAutore.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAutore()));

        colonnaStato.setCellValueFactory(data -> {
            StatoLettura stato = data.getValue().getStatoLettura();
            String statoLeggibile = switch (stato) {
                case DA_LEGGERE -> "Da leggere";
                case IN_LETTURA -> "In lettura";
                case LETTO -> "Letto";
            };
            return new ReadOnlyStringWrapper(statoLeggibile);
        });

        colonnaGenere.setCellValueFactory(new PropertyValueFactory<>("genere"));
        colonnaValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));

        // Carico dati iniziali
        libriList = FXCollections.observableArrayList(controller.gestisciElencoLibri());
        tabellaLibri.setItems(libriList);

        filterStato.getSelectionModel().selectFirst();
        for (StatoLettura stato : StatoLettura.values()) {
            filterStato.getItems().add(stato.toString()); // usa il label leggibile
        }



        filterValutazione.setItems(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5));
        filterValutazione.getSelectionModel().select(0);
    }

    private List<String> buildStatoList() {
        List<String> stati =
                new java.util.ArrayList<>(FXCollections.observableArrayList("Tutti").stream().toList());
        for (StatoLettura s : StatoLettura.values()) {
            stati.add(s.name());
        }
        return stati;
    }

    @FXML
    private void handleAggiungi() {
        Dialog<Libro> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Nuovo Libro");

        ButtonType aggiungiButtonType = new ButtonType("Aggiungi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(aggiungiButtonType, ButtonType.CANCEL);

        TextField titoloField = new TextField();
        TextField autoreField = new TextField();
        TextField genereField = new TextField();
        TextField isbnField = new TextField();

        ComboBox<StatoLettura> statoCombo = new ComboBox<>(FXCollections.observableArrayList(StatoLettura.values()));
        statoCombo.setValue(StatoLettura.DA_LEGGERE);

        Spinner<Integer> valutazioneSpinner = new Spinner<>(0, 5, 0);

        TextField urlField = new TextField();
        urlField.setPromptText("URL copertina");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Titolo:"), 0, 0);
        grid.add(titoloField, 1, 0);
        grid.add(new Label("Autore:"), 0, 1);
        grid.add(autoreField, 1, 1);
        grid.add(new Label("Genere:"), 0, 2);
        grid.add(genereField, 1, 2);
        grid.add(new Label("ISBN:"), 0, 3);
        grid.add(isbnField, 1, 3);
        grid.add(new Label("Stato Lettura:"), 0, 4);
        grid.add(statoCombo, 1, 4);
        grid.add(new Label("Valutazione:"), 0, 5);
        grid.add(valutazioneSpinner, 1, 5);
        grid.add(new Label("URL Copertina:"), 0, 6);
        grid.add(urlField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == aggiungiButtonType) {
                return new Libro(
                        titoloField.getText(),
                        autoreField.getText(),
                        genereField.getText(),
                        isbnField.getText(),
                        statoCombo.getValue(),
                        valutazioneSpinner.getValue(),
                        urlField.getText().trim()
                );
            }
            return null;
        });

        boolean aggiuntoConSuccesso = false;
        while (!aggiuntoConSuccesso) {
            var risultato = dialog.showAndWait();
            if (risultato.isEmpty()) {
                break; // utente ha cliccato “Annulla”
            }
            Libro nuovoLibro = risultato.get();

            if (controller.gestisciAggiuntaLibro(nuovoLibro)) {
                libriList.setAll(controller.gestisciElencoLibri());
                mostraAlertInfo("✅ Libro aggiunto con successo!");
                aggiuntoConSuccesso = true;
            } else {
                mostraAlertWarning("❌ Esiste già un libro con questo ISBN. Modifica l'ISBN e riprova.");
                // Ripristino i dati nel dialog
                titoloField.setText(nuovoLibro.getTitolo());
                autoreField.setText(nuovoLibro.getAutore());
                genereField.setText(nuovoLibro.getGenere());
                isbnField.setText(nuovoLibro.getIsbn());
                statoCombo.setValue(nuovoLibro.getStatoLettura());
                valutazioneSpinner.getValueFactory().setValue(nuovoLibro.getValutazione());
                urlField.setText(nuovoLibro.getCopertinaUrl());
            }
        }
    }

    @FXML
    private void handleModifica() {
        Libro libroSelezionato = tabellaLibri.getSelectionModel().getSelectedItem();
        if (libroSelezionato != null) {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Modifica Libro");

            ButtonType okButtonType = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            TextField titoloField = new TextField(libroSelezionato.getTitolo());
            TextField autoreField = new TextField(libroSelezionato.getAutore());
            TextField genereField = new TextField(libroSelezionato.getGenere());
            TextField isbnField = new TextField(libroSelezionato.getIsbn());

            ComboBox<StatoLettura> statoCombo = new ComboBox<>(FXCollections.observableArrayList(StatoLettura.values()));
            statoCombo.setValue(libroSelezionato.getStatoLettura());

            Spinner<Integer> valutazioneSpinner = new Spinner<>(0, 5, libroSelezionato.getValutazione());

            TextField urlField = new TextField(libroSelezionato.getCopertinaUrl());

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.add(new Label("Titolo:"), 0, 0);
            grid.add(titoloField, 1, 0);
            grid.add(new Label("Autore:"), 0, 1);
            grid.add(autoreField, 1, 1);
            grid.add(new Label("Genere:"), 0, 2);
            grid.add(genereField, 1, 2);
            grid.add(new Label("ISBN:"), 0, 3);
            grid.add(isbnField, 1, 3);
            grid.add(new Label("Stato Lettura:"), 0, 4);
            grid.add(statoCombo, 1, 4);
            grid.add(new Label("Valutazione:"), 0, 5);
            grid.add(valutazioneSpinner, 1, 5);
            grid.add(new Label("URL Copertina:"), 0, 6);
            grid.add(urlField, 1, 6);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    libroSelezionato.setTitolo(titoloField.getText());
                    libroSelezionato.setAutore(autoreField.getText());
                    libroSelezionato.setGenere(genereField.getText());
                    libroSelezionato.setIsbn(isbnField.getText());
                    libroSelezionato.setStatoLettura(statoCombo.getValue());
                    libroSelezionato.setValutazione(valutazioneSpinner.getValue());
                    libroSelezionato.setCopertinaUrl(urlField.getText().trim());

                    // Salvo le modifiche nel controller
                    controller.gestisciAggiornamentoLibro(libroSelezionato);
                }
                return null;
            });

            dialog.showAndWait();

            // Aggiorno la lista e la tabella con i dati aggiornati
            libriList.setAll(controller.gestisciElencoLibri());
        } else {
            mostraAlertWarning("Seleziona un libro da modificare.");
        }
    }


    @FXML
    private void handleElimina() {
        Libro libroSelezionato = tabellaLibri.getSelectionModel().getSelectedItem();
        if (libroSelezionato != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Eliminazione");
            alert.setHeaderText(null);
            alert.setContentText("Sei sicuro di voler eliminare il libro \"" + libroSelezionato.getTitolo() + "\"?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    controller.gestisciEliminazioneLibro(libroSelezionato.getId());
                    libriList.setAll(controller.gestisciElencoLibri());
                }
            });
        } else {
            mostraAlertWarning("Seleziona un libro da eliminare.");
        }
    }

    @FXML
    private void handleApplicaFiltro() {
        String titoloFiltro = filterTitolo.getText().toLowerCase().trim();
        String autoreFiltro = filterAutore.getText().toLowerCase().trim();
        String genereFiltro = filterGenere.getText().toLowerCase().trim(); // <-- aggiunto
        String statoFiltro = filterStato.getValue();
        int valutazioneMinima = filterValutazione.getValue();

        List<Libro> tuttiLibri = controller.gestisciElencoLibri();

        List<Libro> filtrati = tuttiLibri.stream()
                .filter(l -> l.getTitolo().toLowerCase().contains(titoloFiltro))
                .filter(l -> l.getAutore().toLowerCase().contains(autoreFiltro))
                .filter(l -> l.getGenere().toLowerCase().contains(genereFiltro)) // <-- aggiunto
                .filter(l -> {
                    if (statoFiltro == null || statoFiltro.equals("Tutti")) {
                        return true;
                    }
                    StatoLettura statoEnum = StatoLettura.fromLabel(statoFiltro);
                    return l.getStatoLettura() == statoEnum;
                })
                .filter(l -> l.getValutazione() >= valutazioneMinima)
                .collect(Collectors.toList());

        libriList.setAll(filtrati);
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
        String query = searchBar.getText();
        List<Libro> risultati = controller.gestisciRicerca(query);
        libriList.setAll(risultati);
    }

    @FXML
    private void handleEsportaCsv() {
        List<Libro> libri = controller.gestisciElencoLibri();
        String percorsoFile = "libri.csv";

        try {
            CsvExporter.esporta(libri, percorsoFile);
            mostraAlertInfo("Esportazione CSV completata con successo in:\n" + percorsoFile);
        } catch (Exception e) {
            mostraAlertWarning("Errore durante l'esportazione del file CSV: " + e.getMessage());
        }
    }
    @FXML
    private void handleImportaCSV() {
        String percorsoFile = "libri.csv";

        try {
            csvImporter.importaLibriDaCsv(percorsoFile);

            // Aggiorna la tabella con i nuovi dati importati
            libriList.setAll(controller.gestisciElencoLibri());
            tabellaLibri.refresh();

            mostraAlertInfo("📥 Importazione completata da: " + percorsoFile);
        } catch (Exception e) {
            mostraAlertWarning("❌ Errore durante l'importazione: " + e.getMessage());
        }
    }





    private void mostraAlertWarning(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void mostraAlertInfo(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
