package com.carpool.CarPoolingSystem.service;

import com.carpool.CarPoolingSystem.model.Description;
import com.carpool.CarPoolingSystem.repository.DescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptionService {

    @Autowired
    private DescriptionRepository DescriptionRepository;

    public Description saveDescription(Description description) {
        return DescriptionRepository.save(description);
    }
}

