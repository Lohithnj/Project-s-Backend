package com.carpool.CarPoolingSystem.service;


import com.carpool.CarPoolingSystem.model.User;
import com.carpool.CarPoolingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(String email, String password) throws Exception {
        User user = userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new Exception("User not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new Exception("Invalid password");
        }
    }
}

