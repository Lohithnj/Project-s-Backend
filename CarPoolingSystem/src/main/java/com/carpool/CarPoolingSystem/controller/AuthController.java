//package com.carpool.CarPoolingSystem.controller;
//
//import com.carpool.CarPoolingSystem.model.User;
//import com.carpool.CarPoolingSystem.repository.OtpRepository;
//import com.carpool.CarPoolingSystem.repository.UserRepository;
//import com.carpool.CarPoolingSystem.service.OtpService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Random;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    @Autowired
//    private OtpService otpService;
//
//    @Autowired
//    private OtpRepository otpRepository;
//
//    //  Check if email already exists
//    @GetMapping("/check-email")
//    public ResponseEntity<?> checkEmail(@RequestParam String email) {
//        boolean exists = userRepo.existsByEmail(email);
//        return ResponseEntity.ok(!exists); // true = email available
//    }
//
//    @PostMapping("/send-otp")
//    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//
//        if (email == null || email.isEmpty()) {
//            return ResponseEntity.badRequest().body("Email is required");
//        }
//
//        if (userRepo.existsByEmail(email)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
//        }
//
//        try {
//            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
//            otpService.sendOtpEmail(email, otp);
//            otpRepository.save(email, otp, LocalDateTime.now());
//            return ResponseEntity.ok("OTP sent to email");
//        } catch (Exception e) {
//            e.printStackTrace(); // Shows exact error in terminal
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to send OTP: " + e.getMessage());
//        }
//    }
//
//
////    //  Verify OTP
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
//        String email = request.get("email");
//        String otp = request.get("otp");
//
//        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
//            return ResponseEntity.badRequest().body("Email and OTP are required");
//        }
//
//        boolean isValid = otpRepository.isValid(email, otp);
//        if (isValid) {
//            return ResponseEntity.ok("OTP verified successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP verification failed");
//        }
//    }
//
//
//    //-----------------------------------
//    //  Register after OTP verification
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        if (!user.getPassword().matches("^[a-zA-Z0-9]{6}$")) {
//            return ResponseEntity.badRequest().body("Password must be alphanumeric and exactly 6 characters.");
//        }
//        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body("Email already exists!");
//        }
//        userRepo.save(user);
//        return ResponseEntity.ok("User registered successfully");
//    }
//
//
//    //  Login method
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
//        String email = loginData.get("email");
//        String password = loginData.get("password");
//
//        Optional<User> user = userRepo.findByEmail(email, password);
//        if (user.isPresent()) {
//            return ResponseEntity.ok("Login successful");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }
//}

package com.carpool.CarPoolingSystem.controller;

import com.carpool.CarPoolingSystem.model.User;
import com.carpool.CarPoolingSystem.repository.OtpRepository;
import com.carpool.CarPoolingSystem.repository.UserRepository;
import com.carpool.CarPoolingSystem.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    private OtpRepository otpRepository;

    //  Check if email already exists
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userRepo.existsByEmail(email);
        return ResponseEntity.ok(!exists); // true = email available
    }

    //  Send OTP to new email
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (userRepo.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        try {
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            otpService.sendOtpEmail(email, otp);
            otpRepository.save(email, otp, LocalDateTime.now());
            return ResponseEntity.ok("OTP sent to email");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP: " + e.getMessage());
        }
    }

    // Verify the OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and OTP are required");
        }

        boolean isValid = otpRepository.isValid(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP verification failed");
        }
    }

    // Register user after OTP verification
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (!user.getPassword().matches("^[a-zA-Z0-9]{6}$")) {
            return ResponseEntity.badRequest().body("Password must be alphanumeric and exactly 6 characters.");
        }

        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already registered.");
        }

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    //  Login with email & password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Optional<User> user = userRepo.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
