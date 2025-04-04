package com.example.PEP1_Tingeso_Backend.controllers;

import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.repositories.ClientRepository;
import com.example.PEP1_Tingeso_Backend.services.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/booking")
@CrossOrigin("*")
@AllArgsConstructor
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/")
    public ResponseEntity<BookingEntity> createBooking(@RequestBody BookingEntity booking){
        List<ClientEntity> getClients = clientRepository.findAllById(booking.getClients().stream().
                map(ClientEntity::getId).collect(Collectors.toList()));

        booking.setClients(getClients);

        BookingEntity newBooking = bookingService.createBooking(booking);

        return ResponseEntity.ok(newBooking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingEntity> getBookingById(@PathVariable("id") Long id){
        BookingEntity booking = bookingService.getBookingById(id);

        return ResponseEntity.ok(booking);
    }

    @GetMapping("/")
    public ResponseEntity<List<BookingEntity>> getAllBookings(){
        List<BookingEntity> bookings = bookingService.getAllBookings();

        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/")
    public ResponseEntity<BookingEntity> updateBooking(@RequestBody BookingEntity booking){
        BookingEntity updatedBooking = bookingService.updateBooking(booking);

        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingEntity> deleteBooking(@PathVariable("id") Long id) throws Exception{
        bookingService.deleteBooking(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/setPriceAndDuration/{id}")
    public ResponseEntity<BookingEntity> setPriceAndDuration(@PathVariable("id") Long id){
        try{
            BookingEntity updatedBooking = bookingService.setPriceAndDurationInBooking(id);
            return ResponseEntity.ok(updatedBooking);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
