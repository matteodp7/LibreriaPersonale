# Libreria Personale

**Libreria Personale** è un’applicazione desktop Java che sfrutta JavaFX per l’interfaccia utente e JPA/Hibernate per la persistenza. Consente di gestire la propria collezione di libri con operazioni di aggiunta, modifica, cancellazione, ricerca, filtro, ordinamento e import/export in CSV e JSON.

---

## Caratteristiche principali

- **CRUD completo** sui libri: titolo, autore, genere, ISBN, stato di lettura, valutazione (1–5) e URL copertina.
- **Persistenza** su database relazionale via JPA/Hibernate.
- **Import/Export** dei dati in **CSV** e **JSON** (con DTO e Assembler).
- **Filtri dinamici** (Strategy) per titolo, autore, genere, stato e valutazione.
- **Ordinamento** sui vari attributi.
- **Undo** (Memento) per annullare e ripristinare modifiche in GUI e database.
- **Pattern Builder** per la costruzione fluida degli oggetti Libro.
- **Singleton** per la gestione centralizzata delle connessioni/database.

---

## Architettura e pattern utilizzati

| Livello        | Pattern                   | Scopo                                                                                                                             |
|----------------|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------|
| **Domain & DTO** | **DTO** + **Assembler**   | Trasferimento dati tra GUI e service; Assembler converte tra `Libro` (entity) e `LibroDTO`.                                       |
| **Creazione oggetti** | **Builder**               | Costruzione di `Libro` con molti parametri opzionali, migliorando leggibilità e manutenibilità.                                   |
| **Persistenza** | **Singleton**             | `DatabaseSingleton` assicura un’unica istanza di connessione/SessionFactory in tutta l’app.                                       |
| **Undo**       | **Memento**               | `Originator` cattura snapshot della lista di libri; `Caretaker` gestisce stack di undo e sincronizza la GUI con il database. |
| **Filtri**     | **Strategy**              | Incapsula ciascuna logica di filtro (titolo, autore, genere, stato, valutazione) in classi separate, selezionabili dinamicamente. |

---


## Risorse (resources)

- **FXML** (`gui/libro_view.fxml`): definisce layout, menu, bottoni e tabella.
- **persistence.xml**: parametri di connessione, dialect, gestione schema.
- **CSS & immagini**: personalizzazione grafica.
- **Esempi CSV** in `data/`: template per import/export.

---

## Tecnologie

- **Java 23**
- **JavaFX** (FXML)
- **Jakarta Persistence**
- **Gson** per JSON
- File I/O standard per CSV e JSON

---

## Guida rapida all’uso

1. **Configura** `src/main/resources/META-INF/persistence.xml` con credenziali e URL del database.
2. Compila il progetto con **Maven** o **Gradle** (includi dipendenze JavaFX, Gson).
3. Esegui la classe `it.libreriaPersonale.MainApp` come app JavaFX.
4. **Aggiungi**, **modifica**, **elimina** libri tramite la GUI.
5. **Applica filtri** e **ordina** usando i campi e i menu laterali.
6. **Undo** per annullare modifiche immediate.
7. **Import/Export** da/verso CSV e JSON tramite menu “Importa/Esporta”.
8. Alla chiusura automatica, i dati vengono salvati secondo configurazione.

---

## Ringraziamenti

Grazie per aver scelto **Libreria Personale**!  
Buona lettura e buona gestione della tua collezione!

