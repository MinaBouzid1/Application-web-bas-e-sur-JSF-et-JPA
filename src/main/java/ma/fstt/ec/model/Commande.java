package ma.fstt.ec.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;

    @Column(nullable = false)
    private String statut;

    private Double montantTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "internaute_id")
    private Internaute internaute;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    // Constructeurs
    public Commande() {
        this.dateCommande = new Date();
        this.statut = "EN_COURS";
    }

    public Commande(Internaute internaute) {
        this.internaute = internaute;
        this.dateCommande = new Date();
        this.statut = "EN_COURS";
    }

    // Méthode pour calculer le montant total
    public void calculerMontantTotal() {
        this.montantTotal = ligneCommandes.stream()
                .mapToDouble(ligne -> ligne.getPrixUnitaire() * ligne.getQuantite())
                .sum();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Internaute getInternaute() {
        return internaute;
    }

    public void setInternaute(Internaute internaute) {
        this.internaute = internaute;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}