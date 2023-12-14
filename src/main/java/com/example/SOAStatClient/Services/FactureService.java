package com.example.SOAStatClient.Services;

import com.example.SOAStatClient.Repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureService {
    private final FactureRepository factureRepository;

    @Autowired
    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    public List<Object[]> getInvoicePaymentDetailsByClient(Long clientId) {
        return factureRepository.getInvoicePaymentDetailsByClient(clientId);
    }
}
