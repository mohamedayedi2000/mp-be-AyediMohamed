package com.example.SOAStatClient.Services;

import com.example.SOAStatClient.Models.Devise;
import com.example.SOAStatClient.Repository.DeviseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviseService {

    private final DeviseRepository deviseRepository;

    @Autowired
    public DeviseService(DeviseRepository deviseRepository) {
        this.deviseRepository = deviseRepository;
    }

    public List<Devise> getAllDevises() {
        return deviseRepository.findAll();
    }
}
