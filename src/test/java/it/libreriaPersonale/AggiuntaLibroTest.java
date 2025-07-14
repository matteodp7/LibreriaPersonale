package it.libreriaPersonale;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggiuntaLibroTest {

    private static final Logger logger = LoggerFactory.getLogger(AggiuntaLibroTest.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPersonalePU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Libro libro = new Libro();
            libro.setTitolo("Il nome della rosa");
            libro.setAutore("Umberto Eco");
            libro.setGenere("Romanzo");
            libro.setValutazione(1);
            libro.setStatoLettura(StatoLettura.IN_LETTURA);
            libro.setCopertinaUrl("https://m.media-amazon.com/images/I/61Aa9Yic8AL._UF1000,1000_QL80_.jpg");

            em.persist(libro);

            em.getTransaction().commit();
            logger.info(" Libro aggiunto con successo!");
        } catch (Exception e) {
            logger.error(" Errore durante l'inserimento del libro", e);
        } finally {
            em.close();
            emf.close();
        }
    }
}
