package com.accioshoppingbackend.Shopping_Backend.requestbody;

import lombok.Data;

import java.util.UUID;

@Data
public class SingleProductOrderDTO {
    UUID pid;
    int quantity;
}
