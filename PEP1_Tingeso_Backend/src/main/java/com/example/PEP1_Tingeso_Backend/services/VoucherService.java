package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.VoucherEntity;
import com.example.PEP1_Tingeso_Backend.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

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
}
