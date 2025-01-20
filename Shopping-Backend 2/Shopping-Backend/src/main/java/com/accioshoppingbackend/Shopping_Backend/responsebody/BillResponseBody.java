package com.accioshoppingbackend.Shopping_Backend.responsebody;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class BillResponseBody {
    UUID orderID;
    List<BillProductDTO> products;
    int totalPrice;
    int totalQuantity;
    Date orderPlacedDate;
    Date orderExpectedDate;
    String shopperEmail;
}
