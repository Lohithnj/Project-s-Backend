package com.carpool.CarPoolingSystem.repository;

import com.carpool.CarPoolingSystem.model.Description;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description,Long> {
}

