package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            booking.setPrice(15000.0);
            booking.setTotalDuration(30);
        }

        else if((booking.getLapsNumber() > 10 && booking.getLapsNumber() <= 15) ||
                    (booking.getMaximumTime() > 10 && booking.getMaximumTime() <= 15)){
            booking.setPrice(20000.0);
            booking.setTotalDuration(35);
        }

        else if((booking.getLapsNumber() > 15 && booking.getLapsNumber() <= 20) ||
                    (booking.getMaximumTime() > 15 && booking.getMaximumTime() <= 20)){
            booking.setPrice(25000.0);
            booking.setTotalDuration(40);
        }
        else{
            throw new IllegalArgumentException("The maximum time or the number of laps are out of range");
        }

        for(ClientEntity client: booking.getClients()){
            if(client == null){
                throw new IllegalArgumentException("The client was not found");
            }
            System.out.println("Client: " + client + ", Price: " + booking.getPrice());
        }

        updateBooking(booking);

        return booking;
    }

    public Double setDiscountByPeopleNumber(Long id){
        BookingEntity booking = getBookingById(id);
        double discount = 0.0;

        if(booking == null){
            throw new IllegalArgumentException("The booking was not found");
        }

        if(booking.getPeopleNumber() >= 1 && booking.getPeopleNumber() <= 2){
            discount = 0;
        }
        else if(booking.getPeopleNumber() >= 3 && booking.getPeopleNumber() <= 5){
            discount = booking.getPrice() * 0.10;
        }
        else if(booking.getPeopleNumber() >= 6 && booking.getPeopleNumber() <= 10){
            discount = booking.getPrice() * 0.20;
        }
        else if(booking.getPeopleNumber() >= 11 && booking.getPeopleNumber() <= 15){
            discount = booking.getPrice() * 0.20;
        }
        else{
            return null;
        }

        return discount;
    }

    public Double discountByFrequentCustomer(Long id, ClientEntity client){
        BookingEntity booking = getBookingById(id);
        int numberVisits = client.getNumberOfVisits();
        double discount = 0.0;

        if(booking == null){
            throw new IllegalArgumentException("The booking was not found");
        }

        if(numberVisits >= 0 && numberVisits <= 1){
            discount = booking.getPrice() * 0.00;
        }
        else if(numberVisits >= 2 && numberVisits <= 4){
            discount = booking.getPrice() * 0.10;
        }
        else if(numberVisits >= 5 && numberVisits <= 6){
            discount = booking.getPrice() * 0.20;
        }
        else if(numberVisits >= 7){
            discount = booking.getPrice() * 0.30;
        }
        else{
            return null;
        }

        return discount;
    }


}
