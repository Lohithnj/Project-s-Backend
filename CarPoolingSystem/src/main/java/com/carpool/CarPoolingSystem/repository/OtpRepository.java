package com.carpool.CarPoolingSystem.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OtpRepository {

    private final Map<String, OtpEntry> otpStorage = new HashMap<>();

    public void save(String email, String otp, LocalDateTime timestamp) {
        otpStorage.put(email, new OtpEntry(otp, timestamp));
    }

//    public boolean isValid(String email, String otp) {
//        OtpEntry entry = otpStorage.get(email);
//        if (entry == null) return false;
//
//        // OTP valid for 5 minutes
//        if (entry.timestamp.plusMinutes(5).isBefore(LocalDateTime.now())) {
//            otpStorage.remove(email); // expired
//            return false;
//        }
//
//        return entry.otp.equals(otp);
//    }


    public boolean isValid(String email, String otp) {
        OtpEntry entry = otpStorage.get(email);

        if (entry == null) {
            System.out.println("No OTP found for: " + email);
            return false;
        }

        System.out.println("Stored OTP: " + entry.otp + ", Given OTP: " + otp);

        if (entry.timestamp.plusMinutes(5).isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            System.out.println("OTP expired");
            return false;
        }

        return entry.otp.equals(otp);
    }

    private static class OtpEntry {
        String otp;
        LocalDateTime timestamp;

        public OtpEntry(String otp, LocalDateTime timestamp) {
            this.otp = otp;
            this.timestamp = timestamp;
        }
    }
}
