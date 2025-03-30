package com.example.PEP1_Tingeso_Backend.repositories;

import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    public ClientEntity findByEmail(String email);
}
