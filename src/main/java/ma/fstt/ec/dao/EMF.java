package ma.fstt.ec.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMF {

    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("ecomPU");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}