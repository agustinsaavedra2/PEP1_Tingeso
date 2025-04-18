package com.example.PEP1_Tingeso_Backend.repositories;

import com.example.PEP1_Tingeso_Backend.entities.RackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RackRepository extends JpaRepository<RackEntity, Long> {
    public Optional<RackEntity> findById(Long id);
    public Optional<RackEntity> findByDateAndStartTime(LocalDate date, LocalTime startTime);
    public Optional<List<RackEntity>> findByDateAndStatus(LocalDate date, String status);
}
