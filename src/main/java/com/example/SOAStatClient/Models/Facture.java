package com.example.SOAStatClient.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFacture;

    private String num;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFacture;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "devise_id")
    private Devise devise;

    @OneToMany(mappedBy = "facture", fetch = FetchType.EAGER)
    private List<DetailleFacture> detailleFactures;



    public List<DetailleFacture> getDetailleFactures() {
        return detailleFactures;
    }
}
