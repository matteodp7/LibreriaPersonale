package it.libreriaPersonale.service;

import it.libreriaPersonale.repository.LibroRepository;
import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.repository.LibroRepository;

import java.util.List;

public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository lr) {
        this.libroRepository = lr;
    }

    public LibroRepository getLibro() {
        return libroRepository;
    }


    public boolean aggiungiLibro(Libro libro) {
        if (libroRepository.esistePerIsbn(libro.getIsbn())) {
            System.out.println(" Esiste già un libro con questo ISBN.");
            return false;
        }
        libroRepository.salva(libro);
        return true;
    }

    public List<Libro> getLibri() {
        return libroRepository.trovaTutti();
    }

    public void eliminaLibro(Long id) {
        Libro libro = libroRepository.trovaPerId(id);
        if (libro != null) {
            libroRepository.elimina(libro);
        }
    }

    public List<Libro> getLibriOrdinatiPerAutore() {
        return libroRepository.trovaOrdinatiPerAutore();
    }

    public void aggiornaLibro(Libro libro) {
        libroRepository.aggiorna(libro);
    }

    public List<Libro> cercaLibri(String query) {
        return libroRepository.cercaPerTitoloOAutore(query);
    }

    /**
     * Filtra i libri per stato di lettura (enum).
     */
    public List<Libro> filtraPerStato(StatoLettura stato) {
        return libroRepository.filtraPerStato(stato);
    }

    public List<Libro> filtraPerValutazioneMinima(int valutazioneMinima) {
        return libroRepository.filtraPerValutazioneMinima(valutazioneMinima);
    }

    public void chiudi() {
        libroRepository.chiudi();
    }
}
