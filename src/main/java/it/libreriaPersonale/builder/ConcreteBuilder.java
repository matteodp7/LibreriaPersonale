package it.libreriaPersonale.builder;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

public class ConcreteBuilder implements InterfaceBuilder {

    private final Libro libro;

    // Costruttore per nuova istanza
    public ConcreteBuilder() {
        this.libro = new Libro();
    }

    // Costruttore per modificare esistente
    public ConcreteBuilder(Libro libroEsistente) {
        this.libro = libroEsistente;
    }

    @Override
    public InterfaceBuilder setTitolo(String titolo) {
        libro.setTitolo(titolo);
        return this;
    }

    @Override
    public InterfaceBuilder setAutore(String autore) {
        libro.setAutore(autore);
        return this;
    }

    @Override
    public InterfaceBuilder setGenere(String genere) {
        libro.setGenere(genere);
        return this;
    }

    @Override
    public InterfaceBuilder setIsbn(String isbn) {
        libro.setIsbn(isbn);
        return this;
    }

    @Override
    public InterfaceBuilder setStatoLettura(StatoLettura stato) {
        libro.setStatoLettura(stato);
        return this;
    }

    @Override
    public InterfaceBuilder setValutazione(int valutazione) {
        libro.setValutazione(valutazione);
        return this;
    }

    @Override
    public InterfaceBuilder setCopertinaUrl(String url) {
        libro.setCopertinaUrl(url);
        return this;
    }

    @Override
    public Libro build() {
        return libro;
    }
}
