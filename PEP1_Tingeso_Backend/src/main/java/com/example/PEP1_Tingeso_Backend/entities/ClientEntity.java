package com.example.PEP1_Tingeso_Backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String rut;
    private String email;
    private LocalDate birthDate;
    private Integer numberOfVisits;

    @JsonBackReference
    @ManyToMany(mappedBy="clients")
    private List<BookingEntity> bookingList;
}
