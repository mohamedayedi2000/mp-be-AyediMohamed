package com.example.SOAStatClient.Services;

import com.example.SOAStatClient.Models.Client;
import com.example.SOAStatClient.Models.DetailleFacture;
import com.example.SOAStatClient.Models.Facture;
import com.example.SOAStatClient.Repository.ClientRepository;
import com.example.SOAStatClient.Repository.FactureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final FactureRepository factureRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, FactureRepository factureRepository) {
        this.clientRepository = clientRepository;
        this.factureRepository = factureRepository;
    }

    public List<Client> getClientsLesPlusFideles() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -30);
            Date startDate = calendar.getTime();

            System.out.println("Start Date: " + startDate);

            List<Client> clients = clientRepository.findLoyalClients();

            System.out.println("Loyal Clients Query Result: " + clients);

            return clients;
        } catch (Exception e) {

            System.err.println("Error executing getClientsLesPlusFideles: " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();
        }
    }


    public List<Object[]> getMostPurchasedProductByClient() {
        try {
            return clientRepository.getMostPurchasedProductByClient();
        } catch (Exception e) {
            System.err.println("Error executing getMostPurchasedProductByClient: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public boolean checkIfClientIsFidele(Client client) {
        double totalRecentAmount = calculateTotalRecentAmount(client);
        double loyaltyAmountThreshold = 400.0;

        System.out.println("Total Recent Amount for client " + client.getId() + ": " + totalRecentAmount);

        return totalRecentAmount >= loyaltyAmountThreshold;
    }


    public List<Client> getAllclient() {
        return clientRepository.findAll();
    }

    public double calculateTotalRecentAmount(Client client) {
        List<Facture> factures = client.getFactures();

        if (factures.isEmpty()) {
            return 0.0;
        }

        //  (30 days)
        Instant thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS);

        List<Facture> recentFactures = factures.stream()
                .filter(facture -> facture.getDateFacture().toInstant().isAfter(thirtyDaysAgo))
                .collect(Collectors.toList());

        double totalRecentAmount = recentFactures.stream()
                .flatMap(facture -> facture.getDetailleFactures().stream())
                .mapToDouble(detailleFacture -> {
                    double qte = detailleFacture.getQte() != null ? detailleFacture.getQte() : 0.0;
                    double pu = detailleFacture.getPu() != null ? detailleFacture.getPu() : 0.0;
                    return qte * pu;
                })
                .sum();

        return totalRecentAmount;
    }
    public void updateStatuClientBasedOnQueryResult() {
        try {
            List<Client> loyalClients = clientRepository.findLoyalClients();

            clientRepository.updateStatuClientForLoyalClients(loyalClients);

            List<Client> allClients = clientRepository.findAll();

            List<Client> nonLoyalClients = getNonLoyalClients(allClients, loyalClients);

            clientRepository.updateStatuClientForNonLoyalClients(nonLoyalClients);

            System.out.println("Loyal Clients updated: " + loyalClients.size());
            System.out.println("Non-Loyal Clients updated: " + nonLoyalClients.size());
        } catch (Exception e) {
            System.err.println("Error updating client statuses: " + e.getMessage());
            e.printStackTrace();


        }
    }
    @Transactional
    public List<Client> findClientsWithUnpaidInvoices() {
        try {
            return clientRepository.findClientsWithUnpaidInvoices();
        } catch (Exception e) {
            System.err.println("Error executing findClientsWithUnpaidInvoices: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    private List<Client> getNonLoyalClients(List<Client> allClients, List<Client> loyalClients) {
        return allClients.stream()
                .filter(client -> !loyalClients.contains(client) && !Boolean.FALSE.equals(client.getStatuClient()))
                .   collect(Collectors.toList());
    }



}
