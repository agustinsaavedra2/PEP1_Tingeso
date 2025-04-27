package com.example.pep1_tingeso_backend.repositories;

import com.example.pep1_tingeso_backend.entities.RackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RackRepository extends JpaRepository<RackEntity, Long> {
    public Optional<RackEntity> findById(Long id);
    public List<RackEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
