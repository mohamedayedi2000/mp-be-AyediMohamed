package com.example.SOAStatClient.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String designation;
    private Double prix;
    private Integer qteStock;
    private Date dateAchat;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categories categorie;

    private String mbType;
}
