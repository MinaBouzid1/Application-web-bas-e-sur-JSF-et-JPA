package ma.fstt.ec.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.ec.model.Produit;
import java.util.List;

public class ProduitDao implements IDao<Produit> {

    @Override
    public Produit save(Produit entity) {
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
    public Produit update(Produit entity) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Produit updated = em.merge(entity);
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
            Produit produit = em.find(Produit.class, id);
            if (produit != null) {
                em.remove(produit);
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
    public Produit findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produit> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p", Produit.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Produit> findByCategorie(Long categorieId) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p WHERE p.categorie.id = :categorieId", Produit.class)
                    .setParameter("categorieId", categorieId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}