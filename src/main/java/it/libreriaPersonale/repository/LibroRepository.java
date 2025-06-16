package it.libreriaPersonale.repository;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class LibroRepository {

    private final EntityManagerFactory emf;

    public LibroRepository() {
        this.emf = Persistence.createEntityManagerFactory("LibreriaPersonalePU");
    }

    public void salva(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante il salvataggio del libro", e);
        } finally {
            em.close();
        }
    }

    public List<Libro> trovaTutti() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Libro trovaPerId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Libro.class, id);
        } finally {
            em.close();
        }
    }

    public void elimina(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Libro managedLibro = em.find(Libro.class, libro.getId());
            if (managedLibro != null) {
                em.remove(managedLibro);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'eliminazione del libro", e);
        } finally {
            em.close();
        }
    }

    public boolean esistePerIsbn(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(l) FROM Libro l WHERE l.isbn = :isbn", Long.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public List<Libro> trovaOrdinatiPerAutore() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libro l ORDER BY l.autore ASC", Libro.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void aggiorna(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Errore durante l'aggiornamento del libro", e);
        } finally {
            em.close();
        }
    }

    public List<Libro> cercaPerTitoloOAutore(String query) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT l FROM Libro l WHERE LOWER(l.titolo) LIKE :q OR LOWER(l.autore) LIKE :q", Libro.class)
                    .setParameter("q", "%" + query.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Ora accetta l'enum StatoLettura, non String.
     */
    public List<Libro> filtraPerStato(StatoLettura stato) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT l FROM Libro l WHERE l.statolettura = :stato", Libro.class)
                    .setParameter("stato", stato)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Libro> filtraPerValutazioneMinima(int valutazioneMinima) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT l FROM Libro l WHERE l.valutazione >= :val", Libro.class)
                    .setParameter("val", valutazioneMinima)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void chiudi() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
