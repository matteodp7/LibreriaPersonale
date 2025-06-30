package it.libreriaPersonale.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import it.libreriaPersonale.model.Libro;

/**
 * HibernateUtil aggiornata per caricare hibernate.cfg.xml dalla cartella config nel classpath
 * e registrare automaticamente le entity annotate.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Carica hibernate.cfg.xml da src/main/resources/config/hibernate.cfg.xml
            Configuration configuration = new Configuration().configure("config/hibernate.cfg.xml");

            // Registra la tua entity Libro
            configuration.addAnnotatedClass(Libro.class);

            // Costruisce il ServiceRegistry a partire dalle proprietà
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            // Crea la SessionFactory
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println(" Errore nella creazione della SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Restituisce la SessionFactory creata.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Chiude la SessionFactory se aperta.
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
