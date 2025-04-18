package com.example.PEP1_Tingeso_Backend.services;

import com.example.PEP1_Tingeso_Backend.repositories.RackRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RackServiceTest {

    @Mock
    RackRepository rackRepository;

    @InjectMocks
    RackService rackService;
}
