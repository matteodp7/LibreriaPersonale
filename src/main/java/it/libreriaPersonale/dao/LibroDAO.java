package it.libreriaPersonale.dao;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

import java.util.List;

public interface LibroDAO {
    List<Libro> trovaTutti();
    Libro trovaPerId(int id);
    void salva(Libro libro);
    void aggiorna(Libro libro);
    void elimina(Libro libro);

    // Ora accettano l’enum StatoLettura
    boolean esistePerIsbn(String isbn);
    List<Libro> trovaOrdinatiPerAutore();
    List<Libro> cercaPerTitoloOAutore(String query);
    List<Libro> filtraPerStato(StatoLettura stato);
    List<Libro> filtraPerValutazioneMinima(int valutazioneMinima);

    void chiudi();
}
