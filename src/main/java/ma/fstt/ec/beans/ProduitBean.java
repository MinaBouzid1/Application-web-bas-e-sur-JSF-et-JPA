package ma.fstt.ec.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import ma.fstt.ec.dao.CategorieDao;
import ma.fstt.ec.dao.ProduitDao;
import ma.fstt.ec.model.Categorie;
import ma.fstt.ec.model.Produit;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProduitBean implements Serializable {

    private Produit produit = new Produit();
    private List<Produit> produits;
    private List<Categorie> categories;
    private ProduitDao produitDao = new ProduitDao();
    private CategorieDao categorieDao = new CategorieDao();
    private Long categorieId;

    @PostConstruct
    public void init() {
        loadProduits();
        loadCategories();
    }

    public void loadProduits() {
        produits = produitDao.findAll();
    }

    public void loadCategories() {
        categories = categorieDao.findAll();
    }

    public void save() {
        try {
            if (categorieId != null) {
                Categorie categorie = categorieDao.findById(categorieId);
                produit.setCategorie(categorie);
            }

            if (produit.getId() == null) {
                produitDao.save(produit);
                addMessage("Succès", "Produit ajouté avec succès");
            } else {
                produitDao.update(produit);
                addMessage("Succès", "Produit modifié avec succès");
            }
            loadProduits();
            reset();
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public void edit(Produit produit) {
        this.produit = produit;
        if (produit.getCategorie() != null) {
            this.categorieId = produit.getCategorie().getId();
        }
    }

    public void delete(Long id) {
        try {
            produitDao.delete(id);
            loadProduits();
            addMessage("Succès", "Produit supprimé avec succès");
        } catch (Exception e) {
            addMessage("Erreur", "Impossible de supprimer ce produit");
            e.printStackTrace();
        }
    }

    public void reset() {
        produit = new Produit();
        categorieId = null;
    }

    public List<Produit> getProduitsByCategorie(Long catId) {
        if (catId != null) {
            return produitDao.findByCategorie(catId);
        }
        return produits;
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(summary, detail));
    }

    // Getters et Setters
    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }
}