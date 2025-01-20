package com.accioshoppingbackend.Shopping_Backend.service;

import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import com.accioshoppingbackend.Shopping_Backend.model.Product;
import com.accioshoppingbackend.Shopping_Backend.requestbody.CreateProductDTO;
import org.springframework.stereotype.Service;

@Service
public class Mapper {


    public Product productMapper(CreateProductDTO productDTO, ApplicationUser seller){
        Product product = new Product();
        product.setProductCategory(productDTO.getProductCategory());
        product.setName(productDTO.getProductName());
        product.setPrice(productDTO.getPrice());
        product.setQunatity(productDTO.getQuantity());
        product.setSeller(seller);
        product.setRating(0.0);
        product.setQunatitySold(0);
        return product;
    }

}
