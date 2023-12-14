package com.example.SOAStatClient;
import com.example.SOAStatClient.Repository.FactureRepository;
import com.example.SOAStatClient.Services.FactureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class FactureServiceTest {

    @Mock
    private FactureRepository factureRepository;

    @InjectMocks
    private FactureService factureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetInvoicePaymentDetailsByClient() {
        Object[] paymentDetail1 = {1L, "123", 500.0, 300.0, 200.0};
        Object[] paymentDetail2 = {2L, "456", 700.0, 400.0, 300.0};
        List<Object[]> mockPaymentDetails = Arrays.asList(paymentDetail1, paymentDetail2);

        when(factureRepository.getInvoicePaymentDetailsByClient(anyLong())).thenReturn(mockPaymentDetails);

        List<Object[]> result = factureService.getInvoicePaymentDetailsByClient(1L);

        assertEquals(mockPaymentDetails, result);
    }
}
