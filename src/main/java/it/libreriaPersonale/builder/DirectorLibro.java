package it.libreriaPersonale.builder;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

public class DirectorLibro {

    private final InterfaceBuilder builder;

    public DirectorLibro(InterfaceBuilder builder) {
        this.builder = builder;
    }


    public Libro costruisciLibroCompleto(String titolo, String autore, String genere,
                                         String isbn, StatoLettura stato, int valutazione, String url) {
        return builder
                .setTitolo(titolo)
                .setAutore(autore)
                .setGenere(genere)
                .setIsbn(isbn)
                .setStatoLettura(stato)
                .setValutazione(valutazione)
                .setCopertinaUrl(url)
                .build();
    }
}
