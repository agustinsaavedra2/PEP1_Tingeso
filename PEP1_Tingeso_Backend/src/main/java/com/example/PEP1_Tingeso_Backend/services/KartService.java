package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.KartEntity;
import com.example.PEP1_Tingeso_Backend.repositories.KartRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KartService {

    @Autowired
    private KartRepository kartRepository;

    public KartEntity createKart(KartEntity kart) {
        return kartRepository.save(kart);
    }

    public KartEntity getKartById(Long id){
        return kartRepository.findById(id).get();
    }

    public KartEntity getKartByCode(String code){
        return kartRepository.findByCode(code);
    }

    public List<KartEntity> getAllKarts(){
        return kartRepository.findAll();
    }

    public KartEntity updateKart(KartEntity kart){
        return kartRepository.save(kart);
    }

    public void deleteKart(Long id) throws Exception{
        try{
            kartRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
