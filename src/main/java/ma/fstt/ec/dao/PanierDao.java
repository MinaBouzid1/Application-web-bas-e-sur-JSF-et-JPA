package ma.fstt.ec.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.fstt.ec.model.LignePanier;
import ma.fstt.ec.model.Panier;
import ma.fstt.ec.model.Produit;
import java.util.List;

public class PanierDao implements IDao<Panier> {

    @Override
    public Panier save(Panier entity) {
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
    public Panier update(Panier entity) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier updated = em.merge(entity);
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
            Panier panier = em.find(Panier.class, id);
            if (panier != null) {
                em.remove(panier);
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
    public Panier findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Panier p " +
                                    "LEFT JOIN FETCH p.lignePaniers l " +
                                    "LEFT JOIN FETCH l.produit " +
                                    "WHERE p.id = :id", Panier.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Panier> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Panier p", Panier.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Panier findByInternaute(Long internauteId) {
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT p FROM Panier p " +
                                    "LEFT JOIN FETCH p.lignePaniers l " +
                                    "LEFT JOIN FETCH l.produit " +
                                    "WHERE p.internaute.id = :internauteId", Panier.class)
                    .setParameter("internauteId", internauteId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void ajouterProduit(Panier panier, Produit produit, int quantite) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Recharger le panier avec ses lignes
            Panier managedPanier = em.find(Panier.class, panier.getId());
            Produit managedProduit = em.find(Produit.class, produit.getId());

            // Vérifier si le produit est déjà dans le panier
            LignePanier ligneExistante = null;
            for (LignePanier ligne : managedPanier.getLignePaniers()) {
                if (ligne.getProduit().getId().equals(managedProduit.getId())) {
                    ligneExistante = ligne;
                    break;
                }
            }

            if (ligneExistante != null) {
                // Mettre à jour la quantité
                ligneExistante.setQuantite(ligneExistante.getQuantite() + quantite);
                em.merge(ligneExistante);
            } else {
                // Créer une nouvelle ligne
                LignePanier nouvelleLigne = new LignePanier(quantite, managedPanier, managedProduit);
                managedPanier.getLignePaniers().add(nouvelleLigne);
                em.persist(nouvelleLigne);
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

    public void supprimerLignePanier(Long lignePanierId) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            LignePanier ligne = em.find(LignePanier.class, lignePanierId);
            if (ligne != null) {
                em.remove(ligne);
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

    public void viderPanier(Long panierId) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Panier panier = em.find(Panier.class, panierId);
            if (panier != null) {
                // Supprimer toutes les lignes du panier
                for (LignePanier ligne : panier.getLignePaniers()) {
                    em.remove(ligne);
                }
                panier.getLignePaniers().clear();
                em.merge(panier);
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
}