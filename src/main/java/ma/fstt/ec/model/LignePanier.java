package ma.fstt.ec.model;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ligne_panier")
public class LignePanier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "panier_id")
    private Panier panier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id")
    private Produit produit;


    // Constructeurs
    public LignePanier() {
    }

    public LignePanier(Integer quantite, Panier panier, Produit produit) {
        this.quantite = quantite;
        this.panier = panier;
        this.produit = produit;
    }

    // Méthode pour calculer le sous-total
    public Double getSousTotal() {
        if (produit != null && quantite != null) {
            return produit.getPrix() * quantite;
        }
        return 0.0;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}