package com.example.SOAStatClient.Repository;

import com.example.SOAStatClient.Models.Client;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT DISTINCT c FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailleFactures df " +
            "WHERE YEAR(f.dateFacture) = YEAR(CURRENT_DATE) " +
            "AND MONTH(f.dateFacture) = MONTH(CURRENT_DATE) " +
            "AND df.pu > 400")
    List<Client> findLoyalClients();

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.statuClient = true WHERE c IN :loyalClients")
    void updateStatuClientForLoyalClients(@Param("loyalClients") List<Client> loyalClients);

    @Modifying
    @Transactional
    @Query("UPDATE Client c SET c.statuClient = false WHERE c IN :nonLoyalClients")
    void updateStatuClientForNonLoyalClients(@Param("nonLoyalClients") List<Client> nonLoyalClients);

    @Query("SELECT c.id, c.name, p.designation, SUM(df.qte) AS totalQuantity " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "JOIN f.detailleFactures df " +
            "JOIN df.produit p " +
            "GROUP BY c.id, c.name, p.designation " +
            "ORDER BY c.id, totalQuantity DESC")
    List<Object[]> getMostPurchasedProductByClient();

    @Query("SELECT DISTINCT c " +
            "FROM Client c " +
            "JOIN FETCH c.factures f " +
            "LEFT JOIN RegleTsFacture rt ON rt.facture.idFacture = f.idFacture " +
            "WHERE c.id IN " +
            "(SELECT DISTINCT c.id " +
            "FROM Client c " +
            "JOIN c.factures f " +
            "WHERE f.idFacture NOT IN " +
            "(SELECT DISTINCT f.idFacture " +
            "FROM Facture f " +
            "LEFT JOIN RegleTsFacture rt ON rt.facture.idFacture = f.idFacture " +
            "WHERE COALESCE(rt.montant, 0) >= ELEMENT(f.detailleFactures).pu * ELEMENT(f.detailleFactures).qte))")
    List<Client> findClientsWithUnpaidInvoices();

}
