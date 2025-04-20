package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import com.example.PEP1_Tingeso_Backend.entities.RackEntity;
import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import com.example.PEP1_Tingeso_Backend.repositories.RackRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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

    private LocalTime getStartTimeForDate(LocalDate date, List<LocalDate> holidays) {
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || holidays.contains(date)) {
            return LocalTime.of(10, 0); // Fines de semana o feriados
        } else {
            return LocalTime.of(14, 0); // Días hábiles
        }
    }

    public Map<String, Map<LocalTime, BookingEntity>> getWeeklyBookingRackFromDate(LocalDate startDate) {
        Map<String, Map<LocalTime, BookingEntity>> rack = new LinkedHashMap<>();

        int slotMinutes = 20;
        LocalDate monday = startDate.with(DayOfWeek.MONDAY);
        LocalDate sunday = monday.plusDays(6);

        // Lista de feriados
        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(startDate.getYear(), 1, 1),
                LocalDate.of(startDate.getYear(), 4, 18),
                LocalDate.of(startDate.getYear(), 4, 19),
                LocalDate.of(startDate.getYear(), 5, 1),
                LocalDate.of(startDate.getYear(), 5, 21),
                LocalDate.of(startDate.getYear(), 6, 20),
                LocalDate.of(startDate.getYear(), 6, 29),
                LocalDate.of(startDate.getYear(), 7, 16),
                LocalDate.of(startDate.getYear(), 8, 15),
                LocalDate.of(startDate.getYear(), 9, 18),
                LocalDate.of(startDate.getYear(), 9, 19),
                LocalDate.of(startDate.getYear(), 10, 12),
                LocalDate.of(startDate.getYear(), 10, 31),
                LocalDate.of(startDate.getYear(), 11, 1),
                LocalDate.of(startDate.getYear(), 11, 16),
                LocalDate.of(startDate.getYear(), 12, 8),
                LocalDate.of(startDate.getYear(), 12, 14),
                LocalDate.of(startDate.getYear(), 12, 25)
        );

        // Buscar reservas entre lunes y domingo
        List<BookingEntity> weekBookings = bookingRepository.findByBookingDateBetween(monday, sunday).get();

        // Indexar por fecha y hora
        Map<LocalDate, Map<LocalTime, BookingEntity>> bookingMap = new HashMap<>();
        for (BookingEntity booking : weekBookings) {
            bookingMap
                    .computeIfAbsent(booking.getBookingDate(), k -> new HashMap<>())
                    .put(booking.getBookingTime(), booking);
        }

        // Construir mapa día por día
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = monday.plusDays(i);
            String dayName = currentDate.getDayOfWeek().toString();
            Map<LocalTime, BookingEntity> slots = new LinkedHashMap<>();

            LocalTime startTime = getStartTimeForDate(currentDate, holidays);
            LocalTime endTime = LocalTime.of(22, 0);

            for (LocalTime time = startTime; time.isBefore(endTime); time = time.plusMinutes(slotMinutes)) {
                BookingEntity bookingSlot = bookingMap
                        .getOrDefault(currentDate, Collections.emptyMap())
                        .getOrDefault(time, null);

                slots.put(time, bookingSlot); // null = libre
            }

            rack.put(dayName, slots);
        }

        return rack;
    }

}
