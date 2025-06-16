package it.libreriaPersonale;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.dao.LibroDAO;
import it.libreriaPersonale.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibroServiceTest {

    private LibroDAO mockDAO;
    private LibroService service;

    @BeforeEach
    void setUp() {
        mockDAO = Mockito.mock(LibroDAO.class);
        service = new LibroService(mockDAO);
    }

    @Test
    void testAggiungiLibro_conIsbnDuplicato() {
        Libro libro = new Libro("Titolo", "Autore", "Genere", "ISBN123", "Letto", 5);

        // Simula che esiste già un libro con questo ISBN
        when(mockDAO.esistePerIsbn("ISBN123")).thenReturn(true);

        boolean risultato = service.aggiungiLibro(libro);

        assertFalse(risultato);
        verify(mockDAO, never()).salva(any());
    }

    @Test
    void testAggiungiLibro_successo() {
        Libro libro = new Libro("Titolo", "Autore", "Genere", "ISBN123", "Letto", 5);

        when(mockDAO.esistePerIsbn("ISBN123")).thenReturn(false);

        boolean risultato = service.aggiungiLibro(libro);

        assertTrue(risultato);
        verify(mockDAO).salva(libro);
    }
}
