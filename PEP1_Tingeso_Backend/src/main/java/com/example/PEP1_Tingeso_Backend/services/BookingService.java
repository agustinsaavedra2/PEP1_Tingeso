package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public BookingEntity createBooking(BookingEntity booking) {
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

    public BookingEntity setPriceAndDurationInBooking(Long id){
        BookingEntity booking = bookingRepository.findById(id).get();

        if(booking == null || booking.getClients() == null || booking.getClients().isEmpty()){
            throw new IllegalArgumentException("The booking was not found");
        }

        if((booking.getLapsNumber() > 0 && booking.getLapsNumber() <= 10) ||
                    (booking.getMaximumTime() > 0 && booking.getMaximumTime() <= 10)){
            booking.setBasePrice(15000.0);
            booking.setTotalDuration(30);
        }

        else if((booking.getLapsNumber() > 10 && booking.getLapsNumber() <= 15) ||
                    (booking.getMaximumTime() > 10 && booking.getMaximumTime() <= 15)){
            booking.setBasePrice(20000.0);
            booking.setTotalDuration(35);
        }

        else if((booking.getLapsNumber() > 15 && booking.getLapsNumber() <= 20) ||
                    (booking.getMaximumTime() > 15 && booking.getMaximumTime() <= 20)){
            booking.setBasePrice(25000.0);
            booking.setTotalDuration(40);
        }
        else{
            throw new IllegalArgumentException("The maximum time or the number of laps are out of range");
        }

        for(ClientEntity client: booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }
            System.out.println("Client: " + client + ", Price: " + booking.getBasePrice());
        }

        updateBooking(booking);

        return booking;
    }

    public BookingEntity setDiscountByPeopleNumber(Long id){
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

        for(ClientEntity client: booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }

            System.out.println("Client: " + client + ", Discount for n° people: " + booking.getDiscountByPeopleNumber());
        }

        updateBooking(booking);

        return booking;
    }

    public BookingEntity discountByFrequentCustomer(Long id) {
        BookingEntity booking = bookingRepository.findById(id).get();
        double discount = 0.0;

        if(booking == null || booking.getClients() == null || booking.getClients().isEmpty()) {
            throw new IllegalArgumentException("The booking was not found");
        }

        for (ClientEntity client : booking.getClients()) {
            if (client.getNumberOfVisits() >= 0 && client.getNumberOfVisits() <= 1) {
                client.setNumberOfVisits(client.getNumberOfVisits() + 1);
                discount = booking.getBasePrice() * 0.00;
                booking.setDiscountByFrequentCustomer(discount);
            } else if (client.getNumberOfVisits() >= 2 && client.getNumberOfVisits() <= 4) {
                client.setNumberOfVisits(client.getNumberOfVisits() + 1);
                discount = booking.getBasePrice() * 0.10;
                booking.setDiscountByFrequentCustomer(discount);
            } else if (client.getNumberOfVisits() >= 5 && client.getNumberOfVisits() <= 6) {
                client.setNumberOfVisits(client.getNumberOfVisits() + 1);
                discount = booking.getBasePrice() * 0.20;
                booking.setDiscountByFrequentCustomer(discount);
            } else if (client.getNumberOfVisits() >= 7) {
                client.setNumberOfVisits(client.getNumberOfVisits() + 1);
                discount = booking.getBasePrice() * 0.30;
                booking.setDiscountByFrequentCustomer(discount);
            } else {
                client.setNumberOfVisits(client.getNumberOfVisits() + 1);
                booking.setDiscountByFrequentCustomer(discount);
            }
        }

        for(ClientEntity client : booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }
            System.out.println("Client: " + client.getName() + ", Discount Frecuent Customer: " + booking.getDiscountByFrequentCustomer());
        }

        updateBooking(booking);
        return booking;
    }

    public BookingEntity discountBySpecialDays(Long id){
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

        Double extraCharge = 0.0;
        Double birthdayDiscount = 0.0;

        if(holidays.contains(bookingDate)){
            extraCharge = extraCharge + booking.getBasePrice() * 0.1;
            booking.setBasePrice(booking.getBasePrice() + extraCharge);
        }
        if(bookingDate.getDayOfWeek() == DayOfWeek.SATURDAY || bookingDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            extraCharge = extraCharge + booking.getBasePrice() * 0.15;
            booking.setBasePrice(booking.getBasePrice() + extraCharge);
        }

        Integer groupSize = booking.getClients().size();
        Integer numberBirthdays = 0;

        for(ClientEntity client: booking.getClients()){
            if(client.getBirthDate() != null && client.getBirthDate().getDayOfWeek() == bookingDate.getDayOfWeek()){
                numberBirthdays = numberBirthdays + 1;
            }
        }

        Integer numberPeopleDiscount = 0;

        if(groupSize >= 3 && groupSize <= 5){
            numberPeopleDiscount = Math.min(1, numberBirthdays);
        }
        if(groupSize >= 6 && groupSize <= 10){
            numberPeopleDiscount = Math.min(2, numberBirthdays);
        }

        birthdayDiscount = birthdayDiscount + (booking.getBasePrice() * 0.50) * numberPeopleDiscount;

        booking.setDiscountBySpecialDays(booking.getBasePrice() - birthdayDiscount);
        updateBooking(booking);

        return booking;
    }
}
