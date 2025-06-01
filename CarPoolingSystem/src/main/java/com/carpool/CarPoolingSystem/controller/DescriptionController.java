package com.carpool.CarPoolingSystem.controller;


import com.carpool.CarPoolingSystem.model.Description;
import com.carpool.CarPoolingSystem.service.DescriptionService;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ToString
@RequestMapping("/api/ride/description")
@CrossOrigin(origins = "*")
public class DescriptionController {

    @Autowired
    private DescriptionService DescriptionService;

    @PostMapping("/save")
    public Description saveDescription(@RequestBody Description description) {
        System.out.println("Saving: " + description);
        return DescriptionService.saveDescription(description);
    }
}

