package com.accioshoppingbackend.Shopping_Backend.requestbody;

import com.accioshoppingbackend.Shopping_Backend.enums.PaymentMethod;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderDTO {

    List<SingleProductOrderDTO> products;
    PaymentMethod paymentMethod;
}
