package com.example.SOAStatClient;

import com.example.SOAStatClient.Models.Client;
import com.example.SOAStatClient.Models.DetailleFacture;
import com.example.SOAStatClient.Models.Facture;
import com.example.SOAStatClient.Repository.ClientRepository;
import com.example.SOAStatClient.Repository.FactureRepository;
import com.example.SOAStatClient.Services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Spy
    private Client client = new Client();

    @Mock
    private FactureRepository factureRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getClientsLesPlusFideles_ValidClients_ReturnsClientList() {
        // Arrange
        List<Client> expectedClients = Arrays.asList(new Client(), new Client());
        when(clientRepository.findLoyalClients()).thenReturn(expectedClients);

        // Act
        List<Client> result = clientService.getClientsLesPlusFideles();

        // Assert
        assertEquals(expectedClients, result);
    }



    @Test
    void testGetAllClient() {
        // Arrange
        List<Client> expectedClients = Arrays.asList(new Client(), new Client());
        when(clientRepository.findAll()).thenReturn(expectedClients);

        // Act
        List<Client> result = clientService.getAllclient();

        // Assert
        assertEquals(expectedClients, result);
        verify(clientRepository).findAll();
    }

    @Test
    void testCalculateTotalRecentAmount() {
        // Arrange
        Facture recentFacture = new Facture();
        recentFacture.setDateFacture(java.sql.Timestamp.from(Instant.now()));
        DetailleFacture detailleFacture = new DetailleFacture();
        detailleFacture.setQte(2);
        detailleFacture.setPu(3.0);
        recentFacture.setDetailleFactures(Arrays.asList(detailleFacture));

        when(client.getFactures()).thenReturn(Arrays.asList(recentFacture));

        // Act
        double result = clientService.calculateTotalRecentAmount(client);

        // Assert
        assertEquals(2 * 3.0, result);

        verify(client).getFactures();
    }
    @Test
    void testUpdateStatuClientBasedOnQueryResult() {
        // Arrange
        List<Client> loyalClients = Arrays.asList(new Client(), new Client());
        when(clientRepository.findLoyalClients()).thenReturn(loyalClients);

        // Act
        clientService.updateStatuClientBasedOnQueryResult();

        // Assert
        verify(clientRepository).updateStatuClientForLoyalClients(loyalClients);
        verify(clientRepository).findAll();
    }
}
