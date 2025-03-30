package com.example.PEP1_Tingeso_Backend.repositories;

import com.example.PEP1_Tingeso_Backend.entities.KartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KartRepository extends JpaRepository<KartEntity, Long> {
    public KartEntity findByCode(String code);
}
