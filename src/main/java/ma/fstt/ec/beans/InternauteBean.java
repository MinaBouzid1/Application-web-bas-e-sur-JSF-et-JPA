package ma.fstt.ec.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.ec.dao.InternauteDao;
import ma.fstt.ec.dao.PanierDao;
import ma.fstt.ec.model.Internaute;
import ma.fstt.ec.model.Panier;

import java.io.Serializable;

@Named
@SessionScoped
public class InternauteBean implements Serializable {

    private Internaute internaute = new Internaute();
    private Internaute currentUser;
    private InternauteDao internauteDao = new InternauteDao();
    private PanierDao panierDao = new PanierDao();

    @Inject
    private PanierBean panierBean;

    private String email;
    private String motDePasse;

    public String login() {
        try {
            currentUser = internauteDao.authenticate(email, motDePasse);
            if (currentUser != null) {
                // Charger ou créer le panier de l'utilisateur
                Panier panier = panierDao.findByInternaute(currentUser.getId());
                if (panier == null) {
                    panier = new Panier(currentUser);
                    panierDao.save(panier);
                }
                panierBean.setPanier(panier);

                addMessage("Succès", "Bienvenue " + currentUser.getPrenom());
                return "vitrine.xhtml?faces-redirect=true";
            } else {
                addMessage("Erreur", "Email ou mot de passe incorrect");
                return null;
            }
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
            return null;
        }
    }

    public String register() {
        try {
            Internaute existing = internauteDao.findByEmail(internaute.getEmail());
            if (existing != null) {
                addMessage("Erreur", "Cet email est déjà utilisé");
                return null;
            }

            internauteDao.save(internaute);

            // Créer un panier pour le nouvel utilisateur
            Panier panier = new Panier(internaute);
            panierDao.save(panier);

            addMessage("Succès", "Inscription réussie. Vous pouvez maintenant vous connecter.");
            internaute = new Internaute();
            return "index.xhtml?faces-redirect=true";
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue lors de l'inscription");
            e.printStackTrace();
            return null;
        }
    }

    public String logout() {
        currentUser = null;
        panierBean.setPanier(null);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(summary, detail));
    }

    // Getters et Setters
    public Internaute getInternaute() {
        return internaute;
    }

    public void setInternaute(Internaute internaute) {
        this.internaute = internaute;
    }

    public Internaute getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Internaute currentUser) {
        this.currentUser = currentUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}