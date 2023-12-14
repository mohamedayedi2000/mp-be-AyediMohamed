package com.example.SOAStatClient.Controller;

import com.example.SOAStatClient.Models.Client;
import com.example.SOAStatClient.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fidelite")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/clientsf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getClientsLesPlusFideles() {
        try {
            List<Client> loyalClients = clientService.getClientsLesPlusFideles();

            clientService.updateStatuClientBasedOnQueryResult();

            List<Map<String, Object>> clientJsonList = loyalClients.stream()
                    .map(Client::toJson)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(clientJsonList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving clients: " + e.getMessage());
        }
    }
    @GetMapping(value = "/most-purchased-products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMostPurchasedProducts() {
        try {
            List<Object[]> mostPurchasedProducts = clientService.getMostPurchasedProductByClient();
            return ResponseEntity.ok(mostPurchasedProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving most purchased products: " + e.getMessage());
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllClients() {
        try {
            List<Client> clients = clientService.getAllclient();
            List<Map<String, Object>> clientJsonList = clients.stream()
                    .map(Client::toJson)
                    .collect(Collectors.toList());

            System.out.println("Number of clients: " + clients.size());
            return ResponseEntity.ok(clientJsonList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving clients: " + e.getMessage());
        }
    }

    @GetMapping(value = "/unpaid-invoices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getClientsWithUnpaidInvoices() {
        try {
            List<Client> clientsWithUnpaidInvoices = clientService.findClientsWithUnpaidInvoices();
            List<Map<String, Object>> clientJsonList = clientsWithUnpaidInvoices.stream()
                    .map(Client::toJson)
                     .collect(Collectors.toList());
            return ResponseEntity.ok(clientJsonList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving clients with unpaid invoices: " + e.getMessage());
        }
    }

    @GetMapping(value = "/loyalty/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> checkClientLoyalty(@PathVariable Long clientId) {
        try {
            Optional<Client> client = clientService.getClientById(clientId);

            if (client.isPresent()) {
                boolean isLoyal = clientService.checkIfClientIsFidele(client.get());
                return ResponseEntity.ok(Map.of("clientId", clientId, "isLoyal", isLoyal));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking client loyalty: " + e.getMessage());
        }
    }
}
