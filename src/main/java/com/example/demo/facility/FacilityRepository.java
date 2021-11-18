package com.example.demo.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility,Long> {

    @Query("SELECT f FROM Facility f WHERE f.ownerId = ?1")
    List<Facility> findByOwnerId(Long ownerId);

}
