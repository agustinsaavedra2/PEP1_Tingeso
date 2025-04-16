package com.example.PEP1_Tingeso_Backend.services;

import org.springframework.data.util.Pair;
import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public BookingEntity createBooking(BookingEntity booking) {
        if(booking.getClients() != null){
            for(ClientEntity client : booking.getClients()){
                client.setNumberOfVisits(client.getNumberOfVisits() + 1);
            }
        }

        return bookingRepository.save(booking);
    }

    public BookingEntity getBookingById(Long id){
        return bookingRepository.findById(id).get();
    }

    public List<BookingEntity> getAllBookings(){
        return bookingRepository.findAll();
    }

    public BookingEntity updateBooking(BookingEntity booking){
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) throws Exception{
        try{
            bookingRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<Pair<String, Double>> setPriceAndDurationInBooking(Long id){
        BookingEntity booking = bookingRepository.findById(id).get();

        if(booking == null || booking.getClients() == null || booking.getClients().isEmpty()){
            throw new IllegalArgumentException("The booking was not found");
        }

        if(booking.getLapsNumber() == 10 ||
                    (booking.getMaximumTime() > 0 && booking.getMaximumTime() <= 10)){
            booking.setBasePrice(15000.0);
            booking.setTotalDuration(30);
        }

        else if(booking.getLapsNumber() == 15 ||
                    (booking.getMaximumTime() > 10 && booking.getMaximumTime() <= 15)){
            booking.setBasePrice(20000.0);
            booking.setTotalDuration(35);
        }

        else if(booking.getLapsNumber() == 20 ||
                    (booking.getMaximumTime() > 15 && booking.getMaximumTime() <= 20)){
            booking.setBasePrice(25000.0);
            booking.setTotalDuration(40);
        }
        else{
            throw new IllegalArgumentException("The maximum time or the number of laps are out of range");
        }

        List<Pair<String, Double>> clientsBasePrice = new ArrayList<>();

        for(ClientEntity client: booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }
            
            clientsBasePrice.add(Pair.of(client.getName(), booking.getBasePrice()));
        }

        updateBooking(booking);

        return clientsBasePrice;
    }

    public List<Pair<String, Double>> setDiscountByPeopleNumber(Long id){
        BookingEntity booking = bookingRepository.findById(id).get();
        double discount = 0.0;

        if(booking == null || booking.getClients() == null || booking.getClients().isEmpty()){
            throw new IllegalArgumentException("The booking was not found");
        }

        if(booking.getClients().size() >= 1 && booking.getClients().size() <= 2){
            discount = 0.0;
            booking.setDiscountByPeopleNumber(discount);
        }
        else if(booking.getClients().size() >= 3 && booking.getClients().size() <= 5){
            discount = booking.getBasePrice() * 0.10;
            booking.setDiscountByPeopleNumber(discount);
        }
        else if(booking.getClients().size() >= 6 && booking.getClients().size() <= 10){
            discount = booking.getBasePrice() * 0.20;
            booking.setDiscountByPeopleNumber(discount);
        }
        else if(booking.getClients().size() >= 11 && booking.getClients().size() <= 15){
            discount = booking.getBasePrice() * 0.20;
            booking.setDiscountByPeopleNumber(discount);
        }
        else{
            booking.setDiscountByPeopleNumber(discount);
        }

        List<Pair<String, Double>> clientsDiscountSizePeople = new ArrayList<>();

        for(ClientEntity client: booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }

            clientsDiscountSizePeople.add(Pair.of(client.getName(), booking.getDiscountByPeopleNumber()));
        }

        updateBooking(booking);

        return clientsDiscountSizePeople;
    }

    public List<Pair<String, Double>> discountByFrequentCustomer(Long id) {
        BookingEntity booking = bookingRepository.findById(id).get();
        double discount = 0.0;

        if(booking == null || booking.getClients() == null || booking.getClients().isEmpty()) {
            throw new IllegalArgumentException("The booking was not found");
        }

        List<Pair<String, Double>> clientsDiscountFrequency = new ArrayList<>();

        for (ClientEntity client : booking.getClients()) {
            if (client.getNumberOfVisits() >= 0 && client.getNumberOfVisits() <= 1) {
                discount = booking.getBasePrice() * 0.00;
            }

            else if (client.getNumberOfVisits() >= 2 && client.getNumberOfVisits() <= 4) {
                discount = booking.getBasePrice() * 0.10;
            }

            else if (client.getNumberOfVisits() >= 5 && client.getNumberOfVisits() <= 6) {
                discount = booking.getBasePrice() * 0.20;
            }

            else if (client.getNumberOfVisits() >= 7) {
                discount = booking.getBasePrice() * 0.30;
            }

            clientsDiscountFrequency.add(Pair.of(client.getName(), discount));
        }

        booking.setDiscountByFrequentCustomer(discount);
        updateBooking(booking);

        return clientsDiscountFrequency;
    }

    public List<Pair<String, Double>> discountBySpecialDays(Long id){
        BookingEntity booking = bookingRepository.findById(id).get();

        LocalDate bookingDate = booking.getBookingDate();
        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(bookingDate.getYear(), 1, 1),
                LocalDate.of(bookingDate.getYear(), 4, 18),
                LocalDate.of(bookingDate.getYear(), 4, 19),
                LocalDate.of(bookingDate.getYear(), 5, 1),
                LocalDate.of(bookingDate.getYear(), 5, 21),
                LocalDate.of(bookingDate.getYear(), 6, 20),
                LocalDate.of(bookingDate.getYear(), 6, 29),
                LocalDate.of(bookingDate.getYear(), 7, 16),
                LocalDate.of(bookingDate.getYear(), 8, 15),
                LocalDate.of(bookingDate.getYear(), 9, 18),
                LocalDate.of(bookingDate.getYear(), 9, 19),
                LocalDate.of(bookingDate.getYear(), 10, 12),
                LocalDate.of(bookingDate.getYear(), 10, 31),
                LocalDate.of(bookingDate.getYear(), 11, 1),
                LocalDate.of(bookingDate.getYear(), 11, 16),
                LocalDate.of(bookingDate.getYear(), 12, 8),
                LocalDate.of(bookingDate.getYear(), 12, 14),
                LocalDate.of(bookingDate.getYear(), 12, 25)
        );

        Double newBasePrice = booking.getBasePrice();

        if(holidays.contains(bookingDate)){
            newBasePrice += newBasePrice * 0.10;
        }
        if(bookingDate.getDayOfWeek() == DayOfWeek.SATURDAY || bookingDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            newBasePrice += newBasePrice * 0.15;
        }

        booking.setBasePrice(newBasePrice);

        Integer groupSize = booking.getClients().size();
        Integer numberBirthdays = 0;

        for(ClientEntity client: booking.getClients()){
            if(client != null && client.getBirthDate() != null){
                LocalDate birthDate = client.getBirthDate();

                if(birthDate.getMonth() == bookingDate.getMonth() &&
                        birthDate.getDayOfMonth() == bookingDate.getDayOfMonth()){
                    numberBirthdays++;
                }
            }
        }

        Integer numberPeopleDiscount = 0;

        if(groupSize >= 3 && groupSize <= 5){
            numberPeopleDiscount = Math.min(1, numberBirthdays);
        }
        if(groupSize >= 6 && groupSize <= 10){
            numberPeopleDiscount = Math.min(2, numberBirthdays);
        }

        List<Pair<String, Double>> clientDiscountBySpecialDays = new ArrayList<>();

        Double birthdayDiscount = 0.0;

        for(ClientEntity client: booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }

            if(client != null && client.getBirthDate() != null){
                LocalDate birthDate = client.getBirthDate();

                if(birthDate.getMonth() == bookingDate.getMonth() &&
                        birthDate.getDayOfMonth() == bookingDate.getDayOfMonth()){
                    birthdayDiscount = birthdayDiscount + (booking.getBasePrice() * 0.50) * numberPeopleDiscount;
                    booking.setDiscountBySpecialDays(booking.getBasePrice() - birthdayDiscount);

                    clientDiscountBySpecialDays.add(Pair.of(client.getName(), booking.getDiscountBySpecialDays()));
                }
                else{
                    birthdayDiscount = 0.0;
                    clientDiscountBySpecialDays.add(Pair.of(client.getName(), birthdayDiscount));
                }
            }
        }

        updateBooking(booking);

        return clientDiscountBySpecialDays;
    }

    public List<Map<String, Object>> getRevenueReportByBookingType(LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> bookings = bookingRepository.findByBookingDateBetween(startDate, endDate).get();

        Map<String, Map<String, Object>> reportMap = new HashMap<>();

        for(BookingEntity booking : bookings){
            String type;
            if (booking.getLapsNumber() != null) {
                type = booking.getLapsNumber() + " laps";
            } else if (booking.getMaximumTime() != null) {
                type = booking.getMaximumTime() + " minutes";
            } else {
                continue;
            }

            double totalDiscount =
                    (booking.getDiscountByPeopleNumber() != null ? booking.getDiscountByPeopleNumber() : 0.0)
                            + (booking.getDiscountByFrequentCustomer() != null ? booking.getDiscountByFrequentCustomer() : 0.0)
                            + (booking.getDiscountBySpecialDays() != null ? booking.getDiscountBySpecialDays() : 0.0);

            double revenue = (booking.getBasePrice() != null ? booking.getBasePrice() : 0.0) - totalDiscount;

            if (!reportMap.containsKey(type)) {
                Map<String, Object> info = new HashMap<>();
                info.put("type", type);
                info.put("totalBookings", 0);
                info.put("totalRevenue", 0.0);
                reportMap.put(type, info);
            }

            Map<String, Object> report = reportMap.get(type);
            report.put("totalBookings", (int) report.get("totalBookings") + 1);
            report.put("totalRevenue", (double) report.get("totalRevenue") + revenue);
        }

        return new ArrayList<>(reportMap.values());
    }

    public List<Map<String, Object>> getRevenueReportByGroupSize(LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> bookings = bookingRepository.findByBookingDateBetween(startDate, endDate).get();

        Map<String, Map<String, Object>> reportMap = new HashMap<>();

        for (BookingEntity booking : bookings) {
            int groupSize = (booking.getClients() != null) ? booking.getClients().size() : 0;

            String range;
            if (groupSize >= 1 && groupSize <= 2) {
                range = "1-2 people";
            } else if (groupSize >= 3 && groupSize <= 5) {
                range = "3-5 people";
            } else if (groupSize >= 6 && groupSize <= 10) {
                range = "6-10 people";
            } else if (groupSize >= 11 && groupSize <= 15) {
                range = "11-15 people";
            }else{
                range = "Other";
            }

            double totalDiscount =
                    (booking.getDiscountByPeopleNumber() != null ? booking.getDiscountByPeopleNumber() : 0.0)
                            + (booking.getDiscountByFrequentCustomer() != null ? booking.getDiscountByFrequentCustomer() : 0.0)
                            + (booking.getDiscountBySpecialDays() != null ? booking.getDiscountBySpecialDays() : 0.0);

            double revenue = (booking.getBasePrice() != null ? booking.getBasePrice() : 0.0) - totalDiscount;

            if (!reportMap.containsKey(range)) {
                Map<String, Object> data = new HashMap<>();
                data.put("groupRange", range);
                data.put("totalBookings", 0);
                data.put("totalRevenue", 0.0);
                reportMap.put(range, data);
            }

            Map<String, Object> report = reportMap.get(range);
            report.put("totalBookings", (int) report.get("totalBookings") + 1);
            report.put("totalRevenue", (double) report.get("totalRevenue") + revenue);
        }

        return new ArrayList<>(reportMap.values());
    }
}
