package it.libreriaPersonale.service;

import it.libreriaPersonale.dao.LibroDAO;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

import java.util.List;

public class LibroService {

    private final LibroDAO libroDAO;

    public LibroService(LibroDAO libroDAO) {
        this.libroDAO = libroDAO;
    }

    public LibroDAO getLibroDAO() {
        return libroDAO;
    }


    public boolean aggiungiLibro(Libro libro) {
        if (libroDAO.esistePerIsbn(libro.getIsbn())) {
            System.out.println("❌ Esiste già un libro con questo ISBN.");
            return false;
        }
        libroDAO.salva(libro);
        return true;
    }

    public List<Libro> getLibri() {
        return libroDAO.trovaTutti();
    }

    public void eliminaLibro(Long id) {
        Libro libro = libroDAO.trovaPerId(Math.toIntExact(id));
        if (libro != null) {
            libroDAO.elimina(libro);
        }
    }

    public List<Libro> getLibriOrdinatiPerAutore() {
        return libroDAO.trovaOrdinatiPerAutore();
    }

    public void aggiornaLibro(Libro libro) {
        libroDAO.aggiorna(libro);
    }

    public List<Libro> cercaLibri(String query) {
        return libroDAO.cercaPerTitoloOAutore(query);
    }

    /**
     * Filtra i libri per stato di lettura (enum).
     */
    public List<Libro> filtraPerStato(StatoLettura stato) {
        return libroDAO.filtraPerStato(stato);
    }

    public List<Libro> filtraPerValutazioneMinima(int valutazioneMinima) {
        return libroDAO.filtraPerValutazioneMinima(valutazioneMinima);
    }

    public void chiudi() {
        libroDAO.chiudi();
    }
}
