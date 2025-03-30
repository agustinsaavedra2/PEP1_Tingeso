package com.example.PEP1_Tingeso_Backend.controllers;

import com.example.PEP1_Tingeso_Backend.entities.KartEntity;
import com.example.PEP1_Tingeso_Backend.services.KartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/kart")
@CrossOrigin("*")
public class KartController {

    @Autowired
    private KartService kartService;

    @PostMapping("/")
    public ResponseEntity<KartEntity> createKart(@RequestBody KartEntity kart){
        KartEntity newKart = kartService.createKart(kart);

        return ResponseEntity.ok(newKart);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<KartEntity> getKartById(@PathVariable("id") Long id){
        KartEntity kart = kartService.getKartById(id);

        return ResponseEntity.ok(kart);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<KartEntity> getKartByCode(@PathVariable("code") String code){
        KartEntity kart = kartService.getKartByCode(code);

        return ResponseEntity.ok(kart);
    }

    @GetMapping("/")
    public ResponseEntity<List<KartEntity>> getAllKarts(){
        List<KartEntity> karts = kartService.getAllKarts();

        return ResponseEntity.ok(karts);
    }

    @PutMapping("/")
    public ResponseEntity<KartEntity> updateKart(@RequestBody KartEntity kart){
        KartEntity updatedKart = kartService.updateKart(kart);

        return ResponseEntity.ok(updatedKart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKart(@PathVariable("id") Long id) throws Exception {
        kartService.deleteKart(id);

        return ResponseEntity.noContent().build();
    }
}
