package it.libreriaPersonale.dao;

import it.libreriaPersonale.model.Libro;
import it.libreriaPersonale.model.StatoLettura;
import it.libreriaPersonale.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public List<Libro> trovaTutti() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Libro", Libro.class).list();
        }
    }

    @Override
    public Libro trovaPerId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Libro.class, id);
        }
    }

    @Override
    public void salva(Libro libro) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(libro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void aggiorna(Libro libro) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(libro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void elimina(Libro libro) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(libro);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public boolean esistePerIsbn(String isbn) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(l) FROM Libro l WHERE l.isbn = :isbn", Long.class)
                    .setParameter("isbn", isbn)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    @Override
    public List<Libro> trovaOrdinatiPerAutore() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Libro l ORDER BY l.autore ASC", Libro.class)
                    .list();
        }
    }

    @Override
    public List<Libro> cercaPerTitoloOAutore(String query) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Libro l WHERE LOWER(l.titolo) LIKE :q OR LOWER(l.autore) LIKE :q", Libro.class)
                    .setParameter("q", "%" + query.toLowerCase() + "%")
                    .list();
        }
    }

    @Override
    public List<Libro> filtraPerStato(StatoLettura stato) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Libro l WHERE l.statolettura = :stato", Libro.class)
                    .setParameter("stato", stato)
                    .list();
        }
    }

    @Override
    public List<Libro> filtraPerValutazioneMinima(int valutazioneMinima) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Libro l WHERE l.valutazione >= :val", Libro.class)
                    .setParameter("val", valutazioneMinima)
                    .list();
        }
    }

    @Override
    public void chiudi() {
        if (HibernateUtil.getSessionFactory().isOpen()) {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
