package com.example.pep1_tingeso_backend.repositories;

import com.example.pep1_tingeso_backend.entities.VoucherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long> {
    public Optional<VoucherEntity> findById(Long id);
    List<VoucherEntity> findByBookingId(Long bookingId);
}
