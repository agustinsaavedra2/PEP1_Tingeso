package com.example.PEP1_Tingeso_Backend.controllers;

import com.example.PEP1_Tingeso_Backend.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/booking")
@CrossOrigin("*")
@AllArgsConstructor
public class BookingController {
    @Autowired
    private BookingService bookingService;
}
