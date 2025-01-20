package com.accioshoppingbackend.Shopping_Backend.service;

import com.accioshoppingbackend.Shopping_Backend.responsebody.BillResponseBody;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class MailService {

    public void cancelOrder(String email, String orderID){
        String path = "http://localhost:8085/api/mail/shopper/cancelorder";
        path += "?shopperEmail=" + email;
        path += "&orderId="+orderID;
        URI url = URI.create(path);
        RequestEntity request = new RequestEntity(HttpMethod.GET, url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.exchange(request, String.class);
    }
}
