package it.libreriaPersonale;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.repository.LibroRepository;
import it.libreriaPersonale.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibroServiceTest {

    private LibroRepository mockRepo;
    private LibroService service;

    @BeforeEach
    void setUp() {
        mockRepo = Mockito.mock(LibroRepository.class);
        service = new LibroService(mockRepo);
    }

    @Test
    void testAggiungiLibro_conIsbnDuplicato() {
        Libro libro = new Libro("Titolo", "Autore", "Genere", "ISBN123", StatoLettura.LETTO, 5, "https://foto.jpg");

        when(mockRepo.esistePerIsbn("ISBN123")).thenReturn(true);

        boolean risultato = service.aggiungiLibro(libro);

        assertFalse(risultato);
        verify(mockRepo, never()).salva(any());
    }

    @Test
    void testAggiungiLibro_successo() {
        Libro libro = new Libro("Titolo", "Autore", "Genere", "ISBN123", StatoLettura.LETTO, 5, "https://foto.jpg");

        when(mockRepo.esistePerIsbn("ISBN123")).thenReturn(false);

        boolean risultato = service.aggiungiLibro(libro);

        assertTrue(risultato);
        verify(mockRepo).salva(libro);
    }
}
