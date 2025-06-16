package it.libreriaPersonale.model;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Classe astratta che implementa ILibro.
 * Ora il campo `statolettura` è mappato come STRING anziché SMALLINT.
 */
@MappedSuperclass
public abstract class LibroAbstract implements ILibro, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String titolo;
    protected String autore;
    protected String genere;
    protected String isbn;

    @Column(name = "statolettura")
    @Enumerated(EnumType.STRING)
    protected StatoLettura statolettura;

    @Column(name = "copertina_url")
    protected String copertinaUrl;

    protected int valutazione;

    public LibroAbstract() {}

    public LibroAbstract(String titolo, String autore, String genere, String isbn,
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
