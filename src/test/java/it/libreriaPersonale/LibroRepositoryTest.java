package it.libreriaPersonale;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import org.junit.jupiter.api.*;

import jakarta.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibroRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("test-unit");
    }

    @BeforeEach
    void setUp() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @AfterEach
    void tearDown() {
        if (tx.isActive()) tx.rollback();
        em.close();
    }

    @AfterAll
    static void close() {
        emf.close();
    }

    @Test
    void testPersistAndFindLibro() {
        Libro libro = new Libro("1984", "Orwell", "Distopia", "1234567890", StatoLettura.DA_LEGGERE, 5, "https://foto.jpg");
        em.persist(libro);
        em.flush();
        em.clear();

        Libro found = em.find(Libro.class, libro.getId());

        assertNotNull(found);
        assertEquals("1984", found.getTitolo());
        assertEquals("Orwell", found.getAutore());
    }

    @Test
    void testQueryAll() {
        em.persist(new Libro("Libro 1", "Autore", "Genere", "ISBN1", StatoLettura.IN_LETTURA, 4,"https://foto.jpg"));
        em.persist(new Libro("Libro 2", "Autore", "Genere", "ISBN2", StatoLettura.LETTO, 3,"https://foto.jpg"));
        em.flush();

        List<Libro> libri = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();

        assertEquals(2, libri.size());
    }
}
