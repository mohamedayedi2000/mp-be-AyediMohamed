package com.example.SOAStatClient.Models;

import com.example.SOAStatClient.Models.Facture;
import com.example.SOAStatClient.Models.Produit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DetailleFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @Column(name = "qte")
    private Integer qte;

    @Column(name = "pu")
    private Double pu;


    public Double getPu() {
        return pu;
    }

    public Integer getQte() {
        return qte;
    }
}