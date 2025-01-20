package com.accioshoppingbackend.Shopping_Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    String productCategory;
    int price;
    int qunatity;
    @ManyToOne
    ApplicationUser seller;
    int qunatitySold;
    Double rating;
}
