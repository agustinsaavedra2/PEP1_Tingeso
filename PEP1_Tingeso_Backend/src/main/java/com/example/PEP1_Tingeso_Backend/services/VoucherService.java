package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.BookingEntity;
import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.entities.VoucherEntity;
import com.example.PEP1_Tingeso_Backend.repositories.BookingRepository;
import com.example.PEP1_Tingeso_Backend.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public VoucherEntity createVoucher(VoucherEntity voucher){
        return voucherRepository.save(voucher);
    }

    public VoucherEntity getVoucherById(Long id){
        return voucherRepository.findById(id).get();
    }

    public List<VoucherEntity> getAllVouchers(){
        return voucherRepository.findAll();
    }

    public VoucherEntity updateVoucher(VoucherEntity voucher){
        return voucherRepository.save(voucher);
    }

    public void deleteVoucher(Long id) throws Exception{
        try{
            voucherRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<VoucherEntity> generateVouchers(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId).get();

        List<VoucherEntity> vouchers = new ArrayList<>();

        Long idBooking = booking.getId();
        LocalDate bookingDate = booking.getBookingDate();
        LocalTime bookingTime = booking.getBookingTime();
        Integer numberLaps = booking.getLapsNumber();
        Integer maximumTime = booking.getMaximumTime();
        String bookingName = booking.getNameBooking();

        double basePrice = booking.getBasePrice();
        double discountNumberPeople = booking.getDiscountByPeopleNumber();
        double discountFrequentCustomer = booking.getDiscountByFrequentCustomer();
        double discountSpecialDays = booking.getDiscountBySpecialDays();

        for (ClientEntity client : booking.getClients()) {
            double finalPrice = basePrice - discountNumberPeople - discountFrequentCustomer - discountSpecialDays;
            double iva = basePrice * 0.19;
            double totalPrice = basePrice + iva;

            VoucherEntity voucher = new VoucherEntity();

            voucher.setBookingId(idBooking);
            voucher.setBookingDate(bookingDate);
            voucher.setBookingTime(bookingTime);
            voucher.setNumberLaps(numberLaps);
            voucher.setMaximumTime(maximumTime);
            voucher.setBookingName(bookingName);

            voucher.setClientName(client.getName());
            voucher.setBase_price(basePrice);
            voucher.setDiscountNumberPeople(discountNumberPeople);
            voucher.setDiscountFrequentCustomer(discountFrequentCustomer);
            voucher.setDiscountSpecialDays(discountSpecialDays);
            voucher.setIva(iva);
            voucher.setFinal_price(finalPrice);
            voucher.setTotal_price(totalPrice);

            createVoucher(voucher);
            vouchers.add(voucher);
        }

        return vouchers;

    }
}
