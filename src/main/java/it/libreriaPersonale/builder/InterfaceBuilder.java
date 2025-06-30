package it.libreriaPersonale.builder;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

public interface InterfaceBuilder {
    InterfaceBuilder setTitolo(String titolo);
    InterfaceBuilder setAutore(String autore);
    InterfaceBuilder setGenere(String genere);
    InterfaceBuilder setIsbn(String isbn);
    InterfaceBuilder setStatoLettura(StatoLettura stato);
    InterfaceBuilder setValutazione(int valutazione);
    InterfaceBuilder setCopertinaUrl(String url);

    Libro build();
}