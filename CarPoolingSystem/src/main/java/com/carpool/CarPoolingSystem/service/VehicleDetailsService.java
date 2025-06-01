package com.carpool.CarPoolingSystem.service;

import com.carpool.CarPoolingSystem.model.User;
import com.carpool.CarPoolingSystem.model.VehicleDetails;
import com.carpool.CarPoolingSystem.repository.UserRepository;
import com.carpool.CarPoolingSystem.repository.VehicleDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleDetailsService {

    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;

    @Autowired
    private UserRepository userRepository;

//    public VehicleDetails saveVehicleDetails(Long userId, VehicleDetails vehicleDetails) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        vehicleDetails.setUser(user);
//        return vehicleDetailsRepository.save(vehicleDetails);
//    }

    public void saveVehicleDetails(VehicleDetails vehicleDetails, String userEmail) {
    vehicleDetails.setUserEmail(userEmail);
    vehicleDetailsRepository.save(vehicleDetails);
}
    public List<VehicleDetails> getVehiclesByEmail(String email) {
        return vehicleDetailsRepository.findByUserEmail(email);
    }

}

