package com.example.PEP1_Tingeso_Backend.repositories;

import com.example.PEP1_Tingeso_Backend.entities.KartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KartRepository extends JpaRepository<KartEntity, Long> {
    public Optional<KartEntity> findById(Long id);
    public KartEntity findByCode(String code);
}
