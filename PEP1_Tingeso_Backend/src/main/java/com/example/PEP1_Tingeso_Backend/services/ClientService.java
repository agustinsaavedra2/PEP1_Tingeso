package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public ClientEntity createClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    public ClientEntity getClientById(Long id) {
        return clientRepository.findById(id).get();
    }

    public ClientEntity getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientEntity updateClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) throws Exception {
        try{
            clientRepository.deleteById(id);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
