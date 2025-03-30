package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
}
