package ma.fstt.ec.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.ec.model.Categorie;
import java.util.List;

public class CategorieDao implements IDao<Categorie> {

    @Override
    public Categorie save(Categorie entity) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Categorie update(Categorie entity) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Categorie updated = em.merge(entity);
            tx.commit();
            return updated;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Categorie categorie = em.find(Categorie.class, id);
            if (categorie != null) {
                em.remove(categorie);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Categorie findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.find(Categorie.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Categorie> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Categorie c LEFT JOIN FETCH c.produits", Categorie.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}