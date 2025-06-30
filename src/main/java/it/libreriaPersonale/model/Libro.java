package it.libreriaPersonale.model;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Entity Libro.
 * Tutti i campi e metodi sono integrati direttamente qui.
 */
@Entity
@Table(name = "libri")
public class Libro implements ILibro, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;
    private String autore;
    private String genere;
    private String isbn;

    @Column(name = "statolettura")
    @Enumerated(EnumType.STRING)
    private StatoLettura statolettura;

    @Column(name = "copertina_url")
    private String copertinaUrl;

    private int valutazione;

    public Libro() {}

    public Libro(String titolo, String autore, String genere, String isbn,
                 StatoLettura statolettura, int valutazione, String copertinaUrl) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.isbn = isbn;
        this.statolettura = statolettura;
        this.valutazione = valutazione;
        this.copertinaUrl = copertinaUrl;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public String getAutore() {
        return autore;
    }

    @Override
    public void setAutore(String autore) {
        this.autore = autore;
    }

    @Override
    public String getGenere() {
        return genere;
    }

    @Override
    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public StatoLettura getStatoLettura() {
        return statolettura;
    }

    @Override
    public void setStatoLettura(StatoLettura statolettura) {
        this.statolettura = statolettura;
    }

    @Override
    public int getValutazione() {
        return valutazione;
    }

    @Override
    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public String getCopertinaUrl() {
        return copertinaUrl;
    }

    public void setCopertinaUrl(String copertinaUrl) {
        this.copertinaUrl = copertinaUrl;
    }
}
