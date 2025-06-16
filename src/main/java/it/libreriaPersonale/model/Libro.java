package it.libreriaPersonale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Entity Libro, estende LibroAbstract.
 * Usa il converter per StatoLettura.
 */
@Entity
@Table(name = "libri")
public class Libro extends LibroAbstract {

    public Libro() {}

    public Libro(String titolo, String autore, String genere, String isbn,
                 StatoLettura statolettura, int valutazione, String copertinaUrl) {
        super(titolo, autore, genere, isbn, statolettura, valutazione, copertinaUrl);
    }
}
