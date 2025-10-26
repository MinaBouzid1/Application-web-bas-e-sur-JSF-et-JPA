package ma.fstt.ec.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.ec.model.Commande;
import ma.fstt.ec.model.LigneCommande;
import ma.fstt.ec.model.Panier;
import java.util.List;

public class CommandeDao implements IDao<Commande> {

    @Override
    public Commande save(Commande entity) {
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
    public Commande update(Commande entity) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Commande updated = em.merge(entity);
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
            Commande commande = em.find(Commande.class, id);
            if (commande != null) {
                em.remove(commande);
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
    public Commande findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Commande c " +
                                    "LEFT JOIN FETCH c.ligneCommandes l " +
                                    "LEFT JOIN FETCH l.produit " +
                                    "WHERE c.id = :id", Commande.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Commande> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Commande c", Commande.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Commande> findByInternaute(Long internauteId) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Commande c " +
                                    "LEFT JOIN FETCH c.ligneCommandes l " +
                                    "LEFT JOIN FETCH l.produit " +
                                    "WHERE c.internaute.id = :internauteId " +
                                    "ORDER BY c.dateCommande DESC", Commande.class)
                    .setParameter("internauteId", internauteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Commande creerCommandeDepuisPanier(Panier panier) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Recharger le panier avec ses lignes
            Panier managedPanier = em.createQuery(
                            "SELECT p FROM Panier p " +
                                    "LEFT JOIN FETCH p.lignePaniers l " +
                                    "LEFT JOIN FETCH l.produit " +
                                    "WHERE p.id = :id", Panier.class)
                    .setParameter("id", panier.getId())
                    .getSingleResult();

            Commande commande = new Commande(managedPanier.getInternaute());
            em.persist(commande);

            // Créer les lignes de commande
            for (var lignePanier : managedPanier.getLignePaniers()) {
                LigneCommande ligneCommande = new LigneCommande(
                        lignePanier.getQuantite(),
                        lignePanier.getProduit().getPrix(),
                        commande,
                        lignePanier.getProduit()
                );
                commande.getLigneCommandes().add(ligneCommande);
                em.persist(ligneCommande);
            }

            commande.calculerMontantTotal();
            em.merge(commande);

            // Vider le panier
            managedPanier.getLignePaniers().clear();
            em.merge(managedPanier);

            tx.commit();
            return commande;
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
}