package ma.fstt.ec.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ma.fstt.ec.dao.CommandeDao;
import ma.fstt.ec.dao.PanierDao;
import ma.fstt.ec.model.Commande;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class CommandeBean implements Serializable {

    private List<Commande> commandes = new ArrayList<>();
    private Commande commandeSelectionnee;
    private CommandeDao commandeDao = new CommandeDao();

    @Inject
    private InternauteBean internauteBean;

    @Inject
    private PanierBean panierBean;

    @PostConstruct
    public void init() {
        loadCommandes();
    }

    public void loadCommandes() {
        if (internauteBean.getCurrentUser() != null) {
            commandes = commandeDao.findByInternaute(internauteBean.getCurrentUser().getId());
        }
    }

    public String validerCommande() {
        try {
            if (internauteBean.getCurrentUser() == null) {
                addMessage("Erreur", "Vous devez être connecté pour passer une commande");
                return null;
            }

            if (panierBean.isPanierVide()) {
                addMessage("Erreur", "Votre panier est vide");
                return null;
            }

            Commande commande = commandeDao.creerCommandeDepuisPanier(panierBean.getPanier());

            if (commande != null) {
                // Recharger le panier vide
                PanierDao panierDao = new PanierDao();
                panierBean.setPanier(panierDao.findByInternaute(internauteBean.getCurrentUser().getId()));

                loadCommandes();
                addMessage("Succès", "Commande validée avec succès. Numéro: " + commande.getId());
                return "commandes.xhtml?faces-redirect=true";
            } else {
                addMessage("Erreur", "Erreur lors de la création de la commande");
                return null;
            }
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue lors de la validation de la commande");
            e.printStackTrace();
            return null;
        }
    }

    public void annulerCommande(Long commandeId) {
        try {
            Commande commande = commandeDao.findById(commandeId);
            if (commande != null && "EN_COURS".equals(commande.getStatut())) {
                commande.setStatut("ANNULEE");
                commandeDao.update(commande);
                loadCommandes();
                addMessage("Succès", "Commande annulée");
            } else {
                addMessage("Erreur", "Impossible d'annuler cette commande");
            }
        } catch (Exception e) {
            addMessage("Erreur", "Une erreur est survenue");
            e.printStackTrace();
        }
    }

    public void viewDetails(Commande commande) {
        this.commandeSelectionnee = commandeDao.findById(commande.getId());
    }

    public String getStatutStyle(String statut) {
        switch (statut) {
            case "EN_COURS":
                return "warning";
            case "VALIDEE":
                return "info";
            case "LIVREE":
                return "success";
            case "ANNULEE":
                return "danger";
            default:
                return "secondary";
        }
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(summary, detail));
    }

    // Getters et Setters
    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public Commande getCommandeSelectionnee() {
        return commandeSelectionnee;
    }

    public void setCommandeSelectionnee(Commande commandeSelectionnee) {
        this.commandeSelectionnee = commandeSelectionnee;
    }
}