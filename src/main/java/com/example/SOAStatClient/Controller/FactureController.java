package com.example.SOAStatClient.Controller;

import com.example.SOAStatClient.Services.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/invoices")
@CrossOrigin(origins = "http://localhost:4200")
public class FactureController {

    private final FactureService factureService;

    @Autowired
    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping(value = "/payment-details/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getInvoicePaymentDetailsByClient(@PathVariable(required = false) Long clientId) {
        try {
            List<Object[]> paymentDetails = factureService.getInvoicePaymentDetailsByClient(clientId);

            return ResponseEntity.ok(paymentDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving invoice payment details: " + e.getMessage());
        }
    }
}
