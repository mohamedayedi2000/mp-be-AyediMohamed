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
    public class Reglement {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String code;
        private Date dateR;
        private Double montant;
        private String typeP;

        @ManyToOne
        @JoinColumn(name = "devise_id")
        private Devise devise;
    }
