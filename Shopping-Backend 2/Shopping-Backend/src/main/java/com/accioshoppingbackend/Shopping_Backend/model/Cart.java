package com.accioshoppingbackend.Shopping_Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    @OneToOne
    ApplicationUser shopper;
    int totalAmount;
    int totalQuantity;
    @OneToMany
    List<Product> products;
}
