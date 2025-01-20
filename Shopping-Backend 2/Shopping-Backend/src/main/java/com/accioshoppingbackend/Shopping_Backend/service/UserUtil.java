package com.accioshoppingbackend.Shopping_Backend.service;

import com.accioshoppingbackend.Shopping_Backend.enums.UserType;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import com.accioshoppingbackend.Shopping_Backend.model.Product;
import com.accioshoppingbackend.Shopping_Backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    public ApplicationUser checkEmailExist(String email){
        ApplicationUser user =  applicationUserRepository.findByEmail(email);
        return user;
    }

    public boolean isSeller(ApplicationUser user){
        if(user.getUserType().equals(UserType.SELLER)){
            Product p = new Product();
            return true;
        }
        return false;
    }
}
