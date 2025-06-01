package com.carpool.CarPoolingSystem.repository;
import com.carpool.CarPoolingSystem.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    @Query("SELECT DISTINCT r FROM Ride r LEFT JOIN r.stopovers s " +
            "WHERE (LOWER(r.fromLocation) LIKE LOWER(CONCAT('%', :fromDistrict, '%')) " +
            "   OR LOWER(s.stopoverLocation) LIKE LOWER(CONCAT('%', :fromDistrict, '%'))) " +
            "AND (LOWER(r.toLocation) LIKE LOWER(CONCAT('%', :toDistrict, '%')) " +
            "   OR LOWER(s.stopoverLocation) LIKE LOWER(CONCAT('%', :toDistrict, '%'))) " +
            "AND r.rideDate = :rideDate AND r.seatsAvailable >= :passengerCount")
    List<Ride> searchRidesWithStopovers(
            @Param("fromDistrict") String fromDistrict,
            @Param("toDistrict") String toDistrict,
            @Param("rideDate") LocalDate rideDate,
            @Param("passengerCount") int passengerCount
    );


}
