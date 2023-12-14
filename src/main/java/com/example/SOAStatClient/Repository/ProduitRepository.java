package com.example.SOAStatClient.Repository;

import com.example.SOAStatClient.Models.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
