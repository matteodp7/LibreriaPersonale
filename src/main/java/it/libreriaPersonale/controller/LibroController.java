package it.libreriaPersonale.controller;

import it.libreriaPersonale.dao.LibroDAO;
import it.libreriaPersonale.dao.LibroDAOImpl;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.service.LibroService;

import java.util.List;

public class LibroController {

    private final LibroService service;

    public LibroController() {
        LibroDAO libroDAO = new LibroDAOImpl();
        this.service = new LibroService(libroDAO);
    }

    public LibroDAO getLibroDAO() {
        return service.getLibroDAO();
    }


    /**
     * Ritorna true se l'aggiunta ha avuto successo, false se l'ISBN era duplicato.
     */
    public boolean gestisciAggiuntaLibro(Libro libro) {
        return service.aggiungiLibro(libro);
    }

    public List<Libro> gestisciElencoLibri() {
        return service.getLibri();
    }

    public void gestisciEliminazioneLibro(Long id) {
        service.eliminaLibro(id);
        System.out.println("🗑 Libro eliminato (se esiste).");
    }

    public List<Libro> gestisciLibriOrdinatiPerAutore() {
        return service.getLibriOrdinatiPerAutore();
    }

    public void gestisciAggiornamentoLibro(Libro libro) {
        service.aggiornaLibro(libro);
        System.out.println("✏️ Libro aggiornato con successo!");
    }

    public List<Libro> gestisciRicerca(String query) {
        return service.cercaLibri(query);
    }

    /**
     * Il parametro ora è di tipo StatoLettura anziché String.
     */
    public List<Libro> gestisciFiltroPerStato(StatoLettura stato) {
        return service.filtraPerStato(stato);
    }

    public List<Libro> gestisciFiltroPerValutazione(int valutazioneMinima) {
        return service.filtraPerValutazioneMinima(valutazioneMinima);
    }

    public void chiudiRisorse() {
        service.chiudi();
    }
}
