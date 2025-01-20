package com.accioshoppingbackend.Shopping_Backend.controller;

import com.accioshoppingbackend.Shopping_Backend.exceptions.WrongCredentialsException;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import com.accioshoppingbackend.Shopping_Backend.service.CommonUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/user")
public class CommonUserController {

    @Autowired
    CommonUserService commonUserService;

    //http://localhost:8080/api/user

    //http://localhost:8080/api/user/register

    @GetMapping("/")
    public String sayHello(){
        return "Hello";
    }
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody ApplicationUser user){
        commonUserService.createUser(user);

        return new ResponseEntity("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity authenticateUser(@RequestHeader String Authorization){
        String [] details = Authorization.split(":");
        String email = details[0];
        String password = details[1];
        try{
           commonUserService.aunthenticateUser(email, password);
           return new ResponseEntity("Login Successfull", HttpStatus.OK);
        }catch (WrongCredentialsException wrongCredentialsException){
            return new ResponseEntity(wrongCredentialsException.getMessage(),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
