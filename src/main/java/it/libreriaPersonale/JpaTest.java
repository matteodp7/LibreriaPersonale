package it.libreriaPersonale;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaTest {

    private static final Logger logger = LoggerFactory.getLogger(JpaTest.class);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPersonalePU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Libro libro = new Libro();
            libro.setTitolo("Il nome della rosa");
            libro.setAutore("Umberto Eco");
            libro.setValutazione(5);
            // Usa l'enum StatoLettura invece della stringa
            libro.setStatoLettura(StatoLettura.IN_LETTURA);
            // Imposta anche l'URL della copertina:
            libro.setCopertinaUrl("https://esempio.com/copertina_nome_della_rosa.jpg");

            em.persist(libro);

            em.getTransaction().commit();
            logger.info("✅ Libro aggiunto con successo!");
        } catch (Exception e) {
            logger.error("❌ Errore durante l'inserimento del libro", e);
        } finally {
            em.close();
            emf.close();
        }
    }
}
