package com.accioshoppingbackend.Shopping_Backend.model;

import com.accioshoppingbackend.Shopping_Backend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String email;

    String password;
    @Column(unique = true, nullable = false)
    Long phoneNumber;
    String address;
    UserType userType;
}
