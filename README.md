# Libreria Personale

**Libreria Personale** è una semplice applicazione desktop Java basata su JavaFX e JPA per la gestione di una collezione personale di libri. Permette di aggiungere, modificare, eliminare, cercare e filtrare libri, e di esportare/importare la libreria in formato CSV.

---

## Caratteristiche principali

- Persistenza dati tramite JPA con Hibernate e un database relazionale (configurabile).
- Gestione completa dei libri con attributi: titolo, autore, genere, ISBN, stato di lettura, valutazione e URL della copertina.
- Enum `StatoLettura` per indicare lo stato del libro: Da leggere, In lettura, Letto.
- Esportazione sul file CSV su richiesta
- Importazione da file CSV per caricare una lista di libri evitando duplicati per ISBN.
- Filtri per stato di lettura e valutazione minima.
- Ordinamento e ricerca libri per titolo e autore.
- Interfaccia grafica con JavaFX (FXML).

---

## Struttura del progetto

- **model**: contiene le classi del dominio
    - `Libro`, `LibroAbstract`, `ILibro`, `StatoLettura`, `StatoLetturaConverter`
- **repository**: gestisce l'accesso ai dati con JPA (`LibroRepository`)
- **service**: contiene servizi di business e utilità
    - `LibroService`, `CsvExporter`, `CsvImporter`
- **controller**: controller JavaFX (`LibroController`)
- **gui**: risorse grafiche (FXML, CSS)
- **MainApp**: classe principale che avvia l'app JavaFX

---

## Resources

La cartella `resources` contiene tutte le risorse non Java necessarie all'applicazione, in particolare:

- **File FXML:**  
  `gui/libro_view.fxml` definisce il layout dell’interfaccia utente in formato XML, utilizzato da JavaFX per costruire la UI.

- **Configurazione JPA:**  
  `META-INF/persistence.xml` configura l’unità di persistenza, i parametri di connessione al database e le proprietà di Hibernate.

- **File CSS:**  
  Se presenti, i fogli di stile personalizzano l’aspetto grafico della UI.

- **Immagini:**  
  Copertine di libri, icone e altre risorse statiche sono collocate in `images/`.

- **File CSV:**  
  File di importazione/esportazione dati, come modelli o esempi, si trovano in `data/`.

Queste risorse vengono caricate automaticamente all’avvio e sono fondamentali per la corretta esecuzione e l’aspetto dell’applicazione.

---

## Tecnologie e librerie

- Java 23
- Jakarta Persistence (JPA) / Hibernate
- JavaFX per UI
- CSV import/export tramite classi Java standard (`FileReader`, `FileWriter`)

---

## Come eseguire l'applicazione

1. Configura il file `persistence.xml` con il tuo database (es. MySQL, H2, PostgreSQL).
2. Compila il progetto con Maven o Gradle (configurare le dipendenze di JavaFX e JPA).
3. Avvia la classe `it.libreriaPersonale.MainApp` come applicazione JavaFX.
4. L'interfaccia grafica si aprirà e potrai gestire i libri.
5. Prima di chiudere, esportare i nuovi salvataggi su `libri.csv`

---


### Gestione libri

- Aggiungi nuovi libri con titolo, autore, genere, ISBN, stato di lettura, valutazione e copertina.
- Modifica libri esistenti.
- Elimina libri.
- Ricerca per titolo o autore .
- Filtra per stato di lettura (`Da leggere`, `In lettura`, `Letto`).
- Filtra per valutazione minima.
- Ordina per autore in modo ascendente.

### Import/Export CSV

- Esportazione in CSV su richiesta:  
  `id,titolo,autore,genere,isbn,statolettura,valutazione,copertinaUrl`.
- Importazione da CSV (evita duplicati per ISBN).
- Il campo `statolettura` viene salvato come stringa (es. `Da leggere`).
- La gestione CSV usa metodi di escape semplici (rimpiazza virgole con spazi).

---

## Note tecniche

- `StatoLettura` è un enum con etichette leggibili e supporto per conversione da stringa.
- JPA mappa lo stato di lettura come `STRING` tramite `StatoLetturaConverter`.
- `LibroAbstract` è una classe base mappata con `@MappedSuperclass` per riutilizzo.
- Il controller JavaFX viene ottenuto tramite FXMLLoader per mantenere lo stato corretto.
- Il pattern repository e service è utilizzato per separare la logica di accesso ai dati e la logica di business.

---


**Grazie per aver usato Libreria Personale! 📚**
