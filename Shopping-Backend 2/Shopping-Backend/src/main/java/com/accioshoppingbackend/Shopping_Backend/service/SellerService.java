package com.accioshoppingbackend.Shopping_Backend.service;

import com.accioshoppingbackend.Shopping_Backend.exceptions.UserNotAllowed;
import com.accioshoppingbackend.Shopping_Backend.exceptions.UserNotFound;
import com.accioshoppingbackend.Shopping_Backend.exceptions.WrongCredentialsException;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import com.accioshoppingbackend.Shopping_Backend.model.Product;
import com.accioshoppingbackend.Shopping_Backend.repository.ProductRepository;
import com.accioshoppingbackend.Shopping_Backend.requestbody.CreateProductDTO;
import com.accioshoppingbackend.Shopping_Backend.requestbody.MultiProductDT0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
public class SellerService {

    @Autowired
    UserUtil userUtil;

    @Autowired
    Mapper mapper;

    @Autowired
    ProductRepository productRepository;

    public void createProduct(CreateProductDTO createProductDTO,
                              String sellerEmail){

        ApplicationUser user  = userUtil.checkEmailExist(sellerEmail);
        if(user == null){
            throw new UserNotFound(String.format(
                    "Seller with email %s does not exist in system",
                    sellerEmail
            ));
        }
        boolean isSeller = userUtil.isSeller(user);

        if(isSeller ==  false){
            throw new UserNotAllowed(String.format(
                    "User with email %s does not allowed to add product",
                    sellerEmail
            ));
        }



        Product product = mapper.productMapper(createProductDTO, user);

        productRepository.save(product);


    }

    public void createMultiProduct(MultiProductDT0 multiProductDT0,
                                   String sellerEmail){
        ApplicationUser user  = userUtil.checkEmailExist(sellerEmail);
        if(user == null){
            throw new UserNotFound(String.format(
                    "Seller with email %s does not exist in system",
                    sellerEmail
            ));
        }
        boolean isSeller = userUtil.isSeller(user);

        if(isSeller ==  false){
            throw new UserNotAllowed(String.format(
                    "User with email %s does not allowed to add product",
                    sellerEmail
            ));
        }

        List<CreateProductDTO> productDTOs = multiProductDT0.getProducts();

        for(CreateProductDTO productDTO : productDTOs){
            Product product = mapper.productMapper(productDTO, user);
            productRepository.save(product);
        }
    }
}
