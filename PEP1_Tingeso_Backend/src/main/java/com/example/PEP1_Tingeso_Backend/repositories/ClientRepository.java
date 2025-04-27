package com.example.pep1_tingeso_backend.repositories;

import com.example.pep1_tingeso_backend.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    public Optional<ClientEntity> findById(Long id);
    public Optional<ClientEntity> findByEmail(String email);
    public Optional<ClientEntity> findByRut(String rut);
}
