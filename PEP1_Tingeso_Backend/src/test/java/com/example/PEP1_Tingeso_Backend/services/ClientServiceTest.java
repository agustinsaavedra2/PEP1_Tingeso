package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.entities.ClientEntity;
import com.example.PEP1_Tingeso_Backend.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void whenCreateClient_thenClientIsSaved() {
        // Given
        ClientEntity client = new ClientEntity();

        client.setId(1L);
        client.setName("Agustín Saavedra");
        client.setRut("20.677.670-6");
        client.setEmail("agustinsaavedra056@gmail.com");
        client.setBirthDate(LocalDate.of(2001, 4, 30));
        client.setNumberOfVisits(0);

        // When
        when(clientRepository.save(client)).thenReturn(client);

        // Then
        ClientEntity savedClient = clientService.createClient(client);

        assertEquals(client.getId(), savedClient.getId());
        assertEquals(client.getName(), savedClient.getName());
        assertEquals(client.getRut(), savedClient.getRut());
        assertEquals(client.getEmail(), savedClient.getEmail());
        assertEquals(client.getBirthDate(), savedClient.getBirthDate());
        assertEquals(client.getNumberOfVisits(), savedClient.getNumberOfVisits());
    }

    @Test
    public void whenNumberOfVisitsIsNegative_thenClientIsNotSaved(){
        ClientEntity client = new ClientEntity();

        client.setId(3L);
        client.setName("Diego Orellana");
        client.setRut("20.152.576-0");
        client.setEmail("diego_orellana156@gmail.com");
        client.setBirthDate(LocalDate.of(2000, 12, 22));
        client.setNumberOfVisits(-1);

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(client));

        verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    public void whenEmailIsBlank_thenClientIsNotSaved(){
        ClientEntity client = new ClientEntity();

        client.setId(2L);
        client.setName("Fernanda Saez");
        client.setRut("21.156.450-2");
        client.setEmail("");
        client.setBirthDate(LocalDate.of(2002, 2, 26));
        client.setNumberOfVisits(0);

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(client));

        verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    public void whenNameIsNull_thenClientIsNotSaved(){
        ClientEntity client = new ClientEntity();

        client.setId(4L);
        client.setName(null);
        client.setRut("11.226.619-4");
        client.setEmail("ximeolmos@hotmail.cl");
        client.setBirthDate(LocalDate.of(1967, 1, 27));
        client.setNumberOfVisits(0);

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(client));

        verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    public void whenRutIsDuplicated_thenClientIsNotSaved(){
        ClientEntity client = new ClientEntity();

        client.setId(5L);
        client.setName("Daniel Saavedra");
        client.setRut("20.677.670-6");
        client.setEmail("daniel_saavedra@gmail.com");
        client.setBirthDate(LocalDate.of(1959, 1, 5));
        client.setNumberOfVisits(0);

        when(clientRepository.findByRut("20.677.670-6")).thenReturn(Optional.of(new ClientEntity()));

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(client));

        verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    public void whenGetClientById_thenGetClient(){
        Long clientId = 6L;
        ClientEntity client = new ClientEntity();

        client.setId(clientId);
        client.setName("Thomas Alvarez");
        client.setRut("12.803.706-K");
        client.setEmail("thomasalvarezXD@gmail.com");
        client.setBirthDate(LocalDate.of(1968, 5, 21));
        client.setNumberOfVisits(0);

        when(clientRepository.findById(6L)).thenReturn(Optional.of(client));

        ClientEntity resultClient = clientService.getClientById(clientId);

        assertEquals(client.getId(), resultClient.getId());
        assertEquals(client.getName(), resultClient.getName());
        assertEquals(client.getRut(), resultClient.getRut());
        assertEquals(client.getEmail(), resultClient.getEmail());
        assertEquals(client.getBirthDate(), resultClient.getBirthDate());
        assertEquals(client.getNumberOfVisits(), resultClient.getNumberOfVisits());

        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    public void whenIdDoesNotExist_thenShowIllegalException(){
        Long clientId = 20L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clientService.getClientById(clientId));

        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    public void whenIdIsNull_thenShowIllegalException(){
        Long clientId = null;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clientService.getClientById(clientId));

        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    public void whenDatabaseFails_thenThrowRuntimeException() {
        Long clientId = 42L;

        when(clientRepository.findById(clientId))
                .thenThrow(new RuntimeException("Simulated DB failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.getClientById(clientId);
        });

        assertEquals("Simulated DB failure", exception.getMessage());
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    public void whenClientIsIncomplete_thenShowIllegalException(){
        Long clientId = 50L;

        ClientEntity client = new ClientEntity();

        client.setId(clientId);
        client.setName("Fernando Saavedra");

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> clientService.getClientById(clientId));
        verify(clientRepository, times(1)).findById(clientId);
    }

}
