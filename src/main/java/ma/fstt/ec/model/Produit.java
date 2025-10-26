package ma.fstt.ec.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double prix;

    private Integer quantiteStock;

    private String image;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<LignePanier> lignePaniers = new ArrayList<>();

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    // Constructeurs
    public Produit() {
    }

    public Produit(String nom, String description, Double prix, Integer quantiteStock) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantiteStock = quantiteStock;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(Integer quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LignePanier> getLignePaniers() {
        return lignePaniers;
    }

    public void setLignePaniers(List<LignePanier> lignePaniers) {
        this.lignePaniers = lignePaniers;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }
}