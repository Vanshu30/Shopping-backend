package com.accioshoppingbackend.Shopping_Backend.responsebody;

import lombok.Data;

import java.util.UUID;

@Data
public class BillProductDTO {
    UUID productID;
    String productName;
    String supplierName;
    int quantity;
    int totalPrice;
}
