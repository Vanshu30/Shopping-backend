package com.accioshoppingbackend.Shopping_Backend.requestbody;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MultiProductDT0 {
    List<CreateProductDTO> products;
}
