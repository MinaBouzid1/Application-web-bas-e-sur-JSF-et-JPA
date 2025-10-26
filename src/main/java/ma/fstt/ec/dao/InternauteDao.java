package ma.fstt.ec.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import ma.fstt.ec.model.Internaute;
import java.util.List;

public class InternauteDao implements IDao<Internaute> {

    @Override
    public Internaute save(Internaute entity) {
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
    public Internaute update(Internaute entity) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Internaute updated = em.merge(entity);
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
            Internaute internaute = em.find(Internaute.class, id);
            if (internaute != null) {
                em.remove(internaute);
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
    public Internaute findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.find(Internaute.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Internaute> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Internaute i", Internaute.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Internaute findByEmail(String email) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Internaute i WHERE i.email = :email", Internaute.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Internaute authenticate(String email, String motDePasse) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Internaute i WHERE i.email = :email AND i.motDePasse = :motDePasse", Internaute.class)
                    .setParameter("email", email)
                    .setParameter("motDePasse", motDePasse)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}