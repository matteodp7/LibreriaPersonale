package it.libreriaPersonale;

import it.libreriaPersonale.controller.LibroController;
import it.libreriaPersonale.dao.LibroDAOImpl;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.repository.LibroRepository;
import it.libreriaPersonale.service.CsvImporter;
import it.libreriaPersonale.service.CsvExporter;

import java.util.List;
import java.util.Scanner;

public class EsempioMain {

    public static void main(String[] args) {
        LibroController controller = new LibroController();

        // Creiamo CsvImporter passando il repository usato da LibroService/LibroController
        CsvImporter importer = new CsvImporter(new LibroDAOImpl());

        // Importiamo i libri da CSV (evita duplicati basandosi sull'ISBN)
        importer.importaLibriDaCsv("libri.csv"); // Assicurati che 'libri.csv' sia nel path giusto

        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        System.out.println("📚 Benvenuto nella Libreria Personale!");

        while (continua) {
            System.out.println("\nCosa vuoi fare?");
            System.out.println("1 - Aggiungi un libro");
            System.out.println("2 - Mostra tutti i libri");
            System.out.println("3 - Aggiorna un libro esistente");
            System.out.println("4 - Mostra libri ordinati per autore");
            System.out.println("5 - Elimina libro per ID");
            System.out.println("6 - Cerca libri per titolo o autore");
            System.out.println("7 - Filtra libri per stato di lettura");
            System.out.println("8 - Filtra libri per valutazione minima");
            System.out.println("0 - Esci");

            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    System.out.println("\n📝 Inserisci i dati del libro:");
                    System.out.print("Titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Autore: ");
                    String autore = scanner.nextLine();
                    System.out.print("Genere: ");
                    String genere = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();

                    // Chiediamo lo stato di lettura come enum
                    System.out.println("Stato lettura (scegli tra: NON_LETTO, IN_LETTURA, LETTO): ");
                    String statoInput = scanner.nextLine().trim().toUpperCase();
                    StatoLettura statoEnum;
                    try {
                        statoEnum = StatoLettura.valueOf(statoInput);
                    } catch (IllegalArgumentException e) {
                        System.out.println("⚠️ Valore non valido, imposto di default NON_LETTO.");
                        statoEnum = StatoLettura.DA_LEGGERE;
                    }

                    System.out.print("Valutazione (1-5): ");
                    int valutazione;
                    try {
                        valutazione = Integer.parseInt(scanner.nextLine());
                        if (valutazione < 1 || valutazione > 5) {
                            System.out.println("⚠️ Valore fuori range, imposto di default 1.");
                            valutazione = 1;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Input non valido, imposto di default 1.");
                        valutazione = 1;
                    }

                    System.out.print("URL copertina: ");
                    String copertinaUrl = scanner.nextLine().trim();
                    if (copertinaUrl.isBlank()) {
                        copertinaUrl = null;
                    }

                    Libro nuovoLibro = new Libro(titolo, autore, genere, isbn, statoEnum, valutazione, copertinaUrl);
                    nuovoLibro.setCopertinaUrl(copertinaUrl);
                    controller.gestisciAggiuntaLibro(nuovoLibro);
                    break;

                case "2":
                    List<Libro> libri = controller.gestisciElencoLibri();
                    System.out.println("\n📚 Elenco libri salvati:");
                    for (Libro libro : libri) {
                        System.out.println("- [" + libro.getId() + "] " +
                                libro.getTitolo() +
                                " di " + libro.getAutore() +
                                " (" + libro.getStatoLettura().name() +
                                ", ⭐ " + libro.getValutazione() + ")");
                        if (libro.getCopertinaUrl() != null) {
                            System.out.println("    Copertina: " + libro.getCopertinaUrl());
                        }
                    }
                    break;

                case "3":
                    System.out.print("📝 Inserisci l'ID del libro da aggiornare: ");
                    Long updateId;
                    try {
                        updateId = Long.parseLong(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID non valido.");
                        break;
                    }

                    Libro esistente = controller.gestisciElencoLibri().stream()
                            .filter(libro -> libro.getId().equals(updateId))
                            .findFirst()
                            .orElse(null);

                    if (esistente == null) {
                        System.out.println("❌ Nessun libro trovato con questo ID.");
                        break;
                    }

                    System.out.println("✏️ Lascia vuoto un campo se non vuoi modificarlo.");

                    System.out.print("Nuovo titolo (attuale: " + esistente.getTitolo() + "): ");
                    String nuovoTitolo = scanner.nextLine();
                    if (!nuovoTitolo.isBlank()) esistente.setTitolo(nuovoTitolo);

                    System.out.print("Nuovo autore (attuale: " + esistente.getAutore() + "): ");
                    String nuovoAutore = scanner.nextLine();
                    if (!nuovoAutore.isBlank()) esistente.setAutore(nuovoAutore);

                    System.out.print("Nuovo genere (attuale: " + esistente.getGenere() + "): ");
                    String nuovoGenere = scanner.nextLine();
                    if (!nuovoGenere.isBlank()) esistente.setGenere(nuovoGenere);

                    System.out.print("Nuovo ISBN (attuale: " + esistente.getIsbn() + "): ");
                    String nuovoIsbn = scanner.nextLine();
                    if (!nuovoIsbn.isBlank()) esistente.setIsbn(nuovoIsbn);

                    System.out.print("Nuovo stato lettura (NON_LETTO, IN_LETTURA, LETTO) (attuale: "
                            + esistente.getStatoLettura().name() + "): ");
                    String nuovoStatoInput = scanner.nextLine().trim().toUpperCase();
                    if (!nuovoStatoInput.isBlank()) {
                        try {
                            esistente.setStatoLettura(StatoLettura.valueOf(nuovoStatoInput));
                        } catch (IllegalArgumentException e) {
                            System.out.println("⚠️ Stato non valido, mantengo quello attuale.");
                        }
                    }

                    System.out.print("Nuova valutazione (1-5) (attuale: " + esistente.getValutazione() + "): ");
                    String nuovaVal = scanner.nextLine();
                    if (!nuovaVal.isBlank()) {
                        try {
                            int valInt = Integer.parseInt(nuovaVal);
                            if (valInt >= 1 && valInt <= 5) {
                                esistente.setValutazione(valInt);
                            } else {
                                System.out.println("⚠️ Valutazione fuori range, mantengo attuale.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Input non valido, mantengo valutazione attuale.");
                        }
                    }

                    System.out.print("Nuovo URL copertina (attuale: "
                            + (esistente.getCopertinaUrl() != null ? esistente.getCopertinaUrl() : "nessuno") + "): ");
                    String nuovoUrl = scanner.nextLine().trim();
                    if (!nuovoUrl.isBlank()) {
                        esistente.setCopertinaUrl(nuovoUrl);
                    }

                    controller.gestisciAggiornamentoLibro(esistente);
                    break;

                case "4":
                    List<Libro> libriAutore = controller.gestisciLibriOrdinatiPerAutore();
                    System.out.println("\n📚 Libri ordinati per autore:");
                    for (Libro libro : libriAutore) {
                        System.out.println("- [" + libro.getId() + "] " +
                                libro.getAutore() + " - " + libro.getTitolo() +
                                " (" + libro.getStatoLettura().name() + ", ⭐ " + libro.getValutazione() + ")");
                        if (libro.getCopertinaUrl() != null) {
                            System.out.println("    Copertina: " + libro.getCopertinaUrl());
                        }
                    }
                    break;

                case "5":
                    System.out.print("🗑 Inserisci ID del libro da eliminare: ");
                    Long id;
                    try {
                        id = Long.parseLong(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID non valido.");
                        break;
                    }
                    controller.gestisciEliminazioneLibro(id);
                    break;

                case "6":
                    System.out.print("🔍 Inserisci parola chiave per titolo o autore: ");
                    String query = scanner.nextLine();
                    List<Libro> risultati = controller.gestisciRicerca(query);

                    if (risultati.isEmpty()) {
                        System.out.println("🔎 Nessun risultato trovato.");
                    } else {
                        System.out.println("🔎 Risultati della ricerca:");
                        for (Libro libro : risultati) {
                            System.out.println("- [" + libro.getId() + "] " +
                                    libro.getTitolo() + " di " + libro.getAutore() +
                                    " (" + libro.getStatoLettura().name() + ", ⭐ " + libro.getValutazione() + ")");
                            if (libro.getCopertinaUrl() != null) {
                                System.out.println("    Copertina: " + libro.getCopertinaUrl());
                            }
                        }
                    }
                    break;

                case "7":
                    System.out.print("📖 Inserisci stato di lettura (NON_LETTO, IN_LETTURA, LETTO): ");
                    String stato2Input = scanner.nextLine().trim().toUpperCase();
                    StatoLettura stato2;
                    try {
                        stato2 = StatoLettura.valueOf(stato2Input);
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Stato non valido, torni al menu.");
                        break;
                    }
                    List<Libro> perStato = controller.gestisciFiltroPerStato(stato2);
                    if (perStato.isEmpty()) {
                        System.out.println("❌ Nessun libro trovato con questo stato.");
                    } else {
                        System.out.println("📚 Libri trovati:");
                        for (Libro libro : perStato) {
                            System.out.println("- [" + libro.getId() + "] " +
                                    libro.getTitolo() + " (" + libro.getStatoLettura().name() + ")");
                            if (libro.getCopertinaUrl() != null) {
                                System.out.println("    Copertina: " + libro.getCopertinaUrl());
                            }
                        }
                    }
                    break;

                case "8":
                    System.out.print("⭐ Inserisci valutazione minima (1-5): ");
                    int minValutazione;
                    try {
                        minValutazione = Integer.parseInt(scanner.nextLine());
                        if (minValutazione < 1 || minValutazione > 5) {
                            System.out.println("❌ Valutazione fuori range.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Input non valido.");
                        break;
                    }
                    List<Libro> perValutazione = controller.gestisciFiltroPerValutazione(minValutazione);
                    if (perValutazione.isEmpty()) {
                        System.out.println("❌ Nessun libro con valutazione ≥ " + minValutazione);
                    } else {
                        System.out.println("⭐ Libri trovati:");
                        for (Libro libro : perValutazione) {
                            System.out.println("- [" + libro.getId() + "] " +
                                    libro.getTitolo() + " (⭐ " + libro.getValutazione() + ")");
                            if (libro.getCopertinaUrl() != null) {
                                System.out.println("    Copertina: " + libro.getCopertinaUrl());
                            }
                        }
                    }
                    break;

                case "0":
                    // ✅ Salva in CSV prima di uscire
                    List<Libro> tuttiLibri = controller.gestisciElencoLibri();
                    CsvExporter.esporta(tuttiLibri, "libri.csv");
                    continua = false;
                    break;

                default:
                    System.out.println("❌ Scelta non valida. Riprova.");
            }
        }

        scanner.close();
        controller.chiudiRisorse();
        System.out.println("\n👋 Arrivederci!");
    }
}
