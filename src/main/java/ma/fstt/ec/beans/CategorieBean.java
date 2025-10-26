package ma.fstt.ec.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import ma.fstt.ec.dao.CategorieDao;
import ma.fstt.ec.model.Categorie;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CategorieBean implements Serializable {

    private Categorie categorie = new Categorie();
    private List<Categorie> categories;
    private CategorieDao categorieDao = new CategorieDao();

    @PostConstruct
    public void init() {
        loadCategories();
    }

    public void loadCategories() {
        categories = categorieDao.findAll();
    }

    public void save() {
        try {
            if (categorie.getId() == null) {
                categorieDao.save(categorie);
                addMessage("Succès", "Catégorie ajoutée avec succès");
            } else {
                categorieDao.update(categorie);
                addMessage("Succès", "Catégorie modifiée avec succès");
            }
            loadCategories();
            categorie = new Categorie();
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public void edit(Categorie categorie) {
        this.categorie = categorie;
    }

    public void delete(Long id) {
        try {
            categorieDao.delete(id);
            loadCategories();
            addMessage("Succès", "Catégorie supprimée avec succès");
        } catch (Exception e) {
            addMessage("Erreur", "Impossible de supprimer cette catégorie");
            e.printStackTrace();
        }
    }

    public void reset() {
        categorie = new Categorie();
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(summary, detail));
    }

    // Getters et Setters
    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }
}