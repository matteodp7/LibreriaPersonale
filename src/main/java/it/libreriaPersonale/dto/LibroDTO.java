package it.libreriaPersonale.dto;

import it.libreriaPersonale.model.ILibro;
import it.libreriaPersonale.model.StatoLettura;

public class LibroDTO implements ILibro {

    private Long id;
    private String titolo;
    private String autore;
    private String genere;
    private String isbn;
    private StatoLettura statolettura;
    private int valutazione;
    private String copertinaUrl;

    public LibroDTO() {}

    public LibroDTO( String titolo, String autore, String genere, String isbn,
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
    public String getTitolo() { return titolo; }
    @Override
    public void setTitolo(String titolo) { this.titolo = titolo; }

    @Override
    public String getAutore() { return autore; }
    @Override
    public void setAutore(String autore) { this.autore = autore; }

    @Override
    public String getGenere() { return genere; }
    @Override
    public void setGenere(String genere) { this.genere = genere; }

    @Override
    public String getIsbn() { return isbn; }
    @Override
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public StatoLettura getStatoLettura() { return statolettura; }
    @Override
    public void setStatoLettura(StatoLettura statoLettura) { this.statolettura = statoLettura; }

    @Override
    public int getValutazione() { return valutazione; }
    @Override
    public void setValutazione(int valutazione) { this.valutazione = valutazione; }

    @Override
    public String getCopertinaUrl() { return copertinaUrl; }
    @Override
    public void setCopertinaUrl(String copertinaUrl) { this.copertinaUrl = copertinaUrl; }
}
