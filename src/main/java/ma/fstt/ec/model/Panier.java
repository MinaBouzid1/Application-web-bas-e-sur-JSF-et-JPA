package ma.fstt.ec.model;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "paniers")
public class Panier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "internaute_id")
    private Internaute internaute;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LignePanier> lignePaniers = new ArrayList<>();


    // Constructeurs
    public Panier() {
        this.dateCreation = new Date();
    }

    public Panier(Internaute internaute) {
        this.internaute = internaute;
        this.dateCreation = new Date();
    }

    // Méthode pour calculer le total
    public Double getTotal() {
        return lignePaniers.stream()
                .mapToDouble(ligne -> ligne.getProduit().getPrix() * ligne.getQuantite())
                .sum();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Internaute getInternaute() {
        return internaute;
    }

    public void setInternaute(Internaute internaute) {
        this.internaute = internaute;
    }

    public List<LignePanier> getLignePaniers() {
        return lignePaniers;
    }

    public void setLignePaniers(List<LignePanier> lignePaniers) {
        this.lignePaniers = lignePaniers;
    }
}