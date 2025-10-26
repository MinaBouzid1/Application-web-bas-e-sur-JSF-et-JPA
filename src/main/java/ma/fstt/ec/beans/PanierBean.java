package ma.fstt.ec.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import ma.fstt.ec.dao.PanierDao;
import ma.fstt.ec.dao.ProduitDao;
import ma.fstt.ec.model.LignePanier;
import ma.fstt.ec.model.Panier;
import ma.fstt.ec.model.Produit;

import java.io.Serializable;

@Named
@SessionScoped
public class PanierBean implements Serializable {

    private Panier panier;
    private PanierDao panierDao = new PanierDao();
    private ProduitDao produitDao = new ProduitDao();

    public void ajouterAuPanier(Long produitId, int quantite) {
        try {
            if (panier == null) {
                addMessage("Erreur", "Vous devez être connecté pour ajouter des produits au panier");
                return;
            }

            Produit produit = produitDao.findById(produitId);
            if (produit == null) {
                addMessage("Erreur", "Produit introuvable");
                return;
            }

            if (produit.getQuantiteStock() < quantite) {
                addMessage("Erreur", "Stock insuffisant");
                return;
            }

            panierDao.ajouterProduit(panier, produit, quantite);

            // Recharger le panier
            panier = panierDao.findById(panier.getId());

            addMessage("Succès", "Produit ajouté au panier");
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public void ajouterAuPanier(Long produitId) {
        ajouterAuPanier(produitId, 1);
    }

    public void supprimerLigne(Long lignePanierId) {
        try {
            panierDao.supprimerLignePanier(lignePanierId);

            // Recharger le panier
            if (panier != null) {
                panier = panierDao.findById(panier.getId());
            }

            addMessage("Succès", "Produit retiré du panier");
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public void viderPanier() {
        try {
            if (panier != null) {
                panierDao.viderPanier(panier.getId());
                panier = panierDao.findById(panier.getId());
                addMessage("Succès", "Panier vidé");
            }
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public void updateQuantite(LignePanier ligne, int nouvelleQuantite) {
        try {
            if (nouvelleQuantite <= 0) {
                supprimerLigne(ligne.getId());
                return;
            }

            if (ligne.getProduit().getQuantiteStock() < nouvelleQuantite) {
                addMessage("Erreur", "Stock insuffisant");
                return;
            }

            ligne.setQuantite(nouvelleQuantite);
            panierDao.update(panier);

            // Recharger le panier
            panier = panierDao.findById(panier.getId());

            addMessage("Succès", "Quantité mise à jour");
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public Double getTotal() {
        if (panier != null && panier.getLignePaniers() != null) {
            return panier.getTotal();
        }
        return 0.0;
    }

    public int getNombreArticles() {
        if (panier != null && panier.getLignePaniers() != null) {
            return panier.getLignePaniers().stream()
                    .mapToInt(LignePanier::getQuantite)
                    .sum();
        }
        return 0;
    }

    public boolean isPanierVide() {
        return panier == null || panier.getLignePaniers() == null || panier.getLignePaniers().isEmpty();
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(summary, detail));
    }

    // Getters et Setters
    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }
}