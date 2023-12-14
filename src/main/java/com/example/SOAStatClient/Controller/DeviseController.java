package com.example.SOAStatClient.Controller;

import com.example.SOAStatClient.Models.Devise;
import com.example.SOAStatClient.Services.DeviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devise")
public class DeviseController {

    private final DeviseService deviseService;

    @Autowired
    public DeviseController(DeviseService deviseService) {
        this.deviseService = deviseService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Devise>> getAllDevises() {
        List<Devise> devises = deviseService.getAllDevises();
        return ResponseEntity.ok(devises);
    }
}
