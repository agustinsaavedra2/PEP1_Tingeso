package com.example.PEP1_Tingeso_Backend.repositories;

import com.example.PEP1_Tingeso_Backend.entities.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {
    public Optional<VoucherEntity> findById(Long id);
}
