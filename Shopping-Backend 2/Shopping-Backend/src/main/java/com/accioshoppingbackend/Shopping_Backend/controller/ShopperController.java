package com.accioshoppingbackend.Shopping_Backend.controller;

import com.accioshoppingbackend.Shopping_Backend.exceptions.InvalidProductException;
import com.accioshoppingbackend.Shopping_Backend.exceptions.UserNotAllowed;
import com.accioshoppingbackend.Shopping_Backend.exceptions.UserNotFound;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationOrder;
import com.accioshoppingbackend.Shopping_Backend.requestbody.PlaceOrderDTO;
import com.accioshoppingbackend.Shopping_Backend.responsebody.BillResponseBody;
import com.accioshoppingbackend.Shopping_Backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shopper")
public class ShopperController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO, @RequestParam String shopperEmail){

        try{
            BillResponseBody bill = orderService.placeOrder(placeOrderDTO, shopperEmail);
            return new ResponseEntity(bill, HttpStatus.CREATED);
        }catch (UserNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(UserNotAllowed e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (InvalidProductException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/order/history")
    public ResponseEntity getOrdersById(@RequestParam String shopperEmail){
        try{
            List<ApplicationOrder>  orders =  orderService.getOrderByUser(shopperEmail);
            return new ResponseEntity(orders, HttpStatus.OK);
        }catch (UserNotAllowed e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }catch (UserNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/order/cancel")
    public void cancelOrder(@RequestParam String shopperEmail,
                      @RequestParam UUID orderID){
        orderService.cancelOrder(shopperEmail, orderID);

    }
}
