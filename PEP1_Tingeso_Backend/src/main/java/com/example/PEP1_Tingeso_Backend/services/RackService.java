package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.RackEntity;
import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import com.example.PEP1_Tingeso_Backend.repositories.RackRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class RackService {

    @Autowired
    private RackRepository rackRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public RackEntity createRack(RackEntity rack) {
        return rackRepository.save(rack);
    }

    public RackEntity getRackById(Long id){
        return rackRepository.findById(id).get();
    }

    public List<RackEntity> getAllRacks(){
        return rackRepository.findAll();
    }

    public RackEntity updateRack(RackEntity rack){
        return rackRepository.save(rack);
    }

    public void deleteRack(Long id) throws Exception{
        try{
            rackRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Boolean occupyRackBlock(LocalDate date, LocalTime startTime, Long bookingId, String clientName) {
        RackEntity block = rackRepository.findByDateAndStartTime(date, startTime).get();
        if (block != null && block.getStatus().equals("AVAILABLE")) {
            block.setStatus("OCCUPIED");
            block.setBookingId(bookingId);
            block.setClientName(clientName);
            rackRepository.save(block);
            return true;
        }
        return false;
    }

    public Boolean freeRackBlock(LocalDate date, LocalTime startTime) {
        RackEntity block = rackRepository.findByDateAndStartTime(date, startTime).get();
        if (block != null && block.getStatus().equals("OCCUPIED")) {
            block.setStatus("AVAILABLE");
            block.setBookingId(null);
            block.setClientName(null);
            rackRepository.save(block);
            return true;
        }
        return false;
    }

    public List<RackEntity> getAvailableBlocksByDate(LocalDate date) {
        return rackRepository.findByDateAndStatus(date, "AVAILABLE").get();
    }

}
