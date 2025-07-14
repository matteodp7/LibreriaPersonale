package it.libreriaPersonale.model;


public interface ILibro {
    Long getId();
    String getTitolo();
    String getAutore();
    String getGenere();
    String getIsbn();
    StatoLettura getStatoLettura();
    int getValutazione();
    String getCopertinaUrl();

    void setTitolo(String titolo);
    void setAutore(String autore);
    void setGenere(String genere);
    void setIsbn(String isbn);
    void setStatoLettura(StatoLettura statoLettura);
    void setValutazione(int valutazione);
    void setCopertinaUrl(String copertinaUrl);
}
