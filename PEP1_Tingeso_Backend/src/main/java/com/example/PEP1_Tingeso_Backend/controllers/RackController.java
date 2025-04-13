package com.example.PEP1_Tingeso_Backend.controllers;

import com.example.PEP1_Tingeso_Backend.entities.RackEntity;
import com.example.PEP1_Tingeso_Backend.services.BookingService;
import com.example.PEP1_Tingeso_Backend.services.RackService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/api/rack")
@CrossOrigin("*")
public class RackController {

    @Autowired
    private RackService rackService;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/")
    public ResponseEntity<RackEntity> createRack(@RequestBody RackEntity rack){
        RackEntity newRack = rackService.createRack(rack);

        return ResponseEntity.ok(newRack);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RackEntity> getRackById(@PathVariable("id") Long id){
        RackEntity rack = rackService.getRackById(id);

        return ResponseEntity.ok(rack);
    }

    @GetMapping("/")
    public ResponseEntity<List<RackEntity>> getAllRacks(){
        List<RackEntity> racks = rackService.getAllRacks();

        return ResponseEntity.ok(racks);
    }

    @PutMapping("/")
    public ResponseEntity<RackEntity> updateRack(@RequestBody RackEntity rack){
        RackEntity updatedRack = rackService.updateRack(rack);

        return ResponseEntity.ok(updatedRack);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable("id") Long id) throws Exception{
        rackService.deleteRack(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/occupy")
    public boolean occupyRackBlock(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam Long bookingId,
            @RequestParam String clientName
    ) {
        return rackService.occupyRackBlock(date, startTime, bookingId, clientName);
    }

    @PostMapping("/free")
    public boolean freeRackBlock(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime
    ) {
        return rackService.freeRackBlock(date, startTime);
    }

    @GetMapping("/available")
    public List<RackEntity> getAvailableBlocksByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return rackService.getAvailableBlocksByDate(date);
    }


}
