package com.accioshoppingbackend.Shopping_Backend.service;

import com.accioshoppingbackend.Shopping_Backend.exceptions.WrongCredentialsException;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import com.accioshoppingbackend.Shopping_Backend.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonUserService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    UserUtil userUtil;

    @Autowired
    MailService mailService;

    public void createUser(ApplicationUser user){
        applicationUserRepository.save(user);
    }

    public boolean aunthenticateUser(String email, String password){
       ApplicationUser user =  userUtil.checkEmailExist(email);
       if(user == null){
            throw new WrongCredentialsException(String.format("User entered wrong email %s", email));
       }

       String actualPassword = user.getPassword();
       if(actualPassword.equals(password)){
           mailService.cancelOrder(email, "123");
           return true;
       }else{
          // mailService.cancelOrder(email, "123");
           throw new WrongCredentialsException(String.format("Wrong password entered %s", password));
       }





    }
}
