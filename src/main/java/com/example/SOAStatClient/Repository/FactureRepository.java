package com.example.SOAStatClient.Repository;

import com.example.SOAStatClient.Models.Client;
import com.example.SOAStatClient.Models.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture,Long> {

    List<Facture> findByClient(Client client);

    @Query("SELECT f.idFacture, f.num, " +
            "df.pu * df.qte AS totalAmount, " +
            "SUM(COALESCE(rt.montant, 0)) AS paidAmount, " +
            "df.pu * df.qte - SUM(COALESCE(rt.montant, 0)) AS remainingAmount " +
            "FROM Facture f " +
            "JOIN f.detailleFactures df " +
            "LEFT JOIN RegleTsFacture rt ON rt.facture.idFacture = f.idFacture " +
            "WHERE f.client.id = :clientId " +
            "GROUP BY f.idFacture, f.num")
    List<Object[]> getInvoicePaymentDetailsByClient(@Param("clientId") Long clientId);
}
