package com.example.PEP1_Tingeso_Backend.repositories;

import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

}
