package com.accioshoppingbackend.Shopping_Backend.service;

import com.accioshoppingbackend.Shopping_Backend.exceptions.InvalidProductException;
import com.accioshoppingbackend.Shopping_Backend.exceptions.UserNotAllowed;
import com.accioshoppingbackend.Shopping_Backend.exceptions.UserNotFound;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationOrder;
import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import com.accioshoppingbackend.Shopping_Backend.model.Product;
import com.accioshoppingbackend.Shopping_Backend.repository.OrderRepository;
import com.accioshoppingbackend.Shopping_Backend.repository.ProductRepository;
import com.accioshoppingbackend.Shopping_Backend.requestbody.PlaceOrderDTO;
import com.accioshoppingbackend.Shopping_Backend.requestbody.SingleProductOrderDTO;
import com.accioshoppingbackend.Shopping_Backend.responsebody.BillProductDTO;
import com.accioshoppingbackend.Shopping_Backend.responsebody.BillResponseBody;
import jakarta.persistence.criteria.Order;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    UserUtil userUtil;

    @Autowired
    ProductUtil productUtil;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MailService mailService;

    public List<ApplicationOrder> getOrderByUser(String shopperEmail){

        ApplicationUser shopper = userUtil.checkEmailExist(shopperEmail);
        if(shopper == null){
            throw new UserNotFound(String.format("User with email %s does not exist", shopperEmail));
        }

        boolean isSeller = userUtil.isSeller(shopper);
        if(isSeller){
            throw new UserNotAllowed(String.format("User with email %s does not access to perform this action", shopperEmail));
        }

        List<ApplicationOrder> orders = orderRepository.getOrderByShopperID(shopper.getId());
        return orders;

    }

    public void cancelOrder(String shopperEmail,
                            UUID oderID){
//        ApplicationUser shopper = userUtil.checkEmailExist(shopperEmail);
//        if(shopper == null){
//            throw new UserNotFound(String.format("User with email %s does not exist", shopperEmail));
//        }
//
//        boolean isSeller = userUtil.isSeller(shopper);
//        if(isSeller){
//            throw new UserNotAllowed(String.format("User with email %s does not access to perform this action", shopperEmail));
//        }

        // Validate orderID
        orderRepository.cancelStatus(oderID);
        // Mail user and sellers regarding cancelation
        // Load products back to the table

        try{
            mailService.cancelOrder(shopperEmail, oderID.toString());
        }catch (Exception e){

        }
    }

    public BillResponseBody placeOrder(PlaceOrderDTO placeOrderDTO, String shopperEmail){

        ApplicationUser user = userUtil.checkEmailExist(shopperEmail);
        if(user == null){
            throw new UserNotFound(String.format("Shopper with email %s does not exist", shopperEmail));
        }

        boolean isSeller = userUtil.isSeller(user);
        if(isSeller){
            throw new UserNotAllowed(String.format("Seller with email %s does not allowed to place order", shopperEmail));
        }

        List<SingleProductOrderDTO> products = placeOrderDTO.getProducts();
        ApplicationOrder order = new ApplicationOrder();
        order.setShopper(user);
        Date currentDate = new Date();
        order.setOrderPlaced(currentDate);
        Date expectedDate = new Date(currentDate.getDate() +  7);
        order.setExpectedDelivery(expectedDate);
        order.setStatus("PENDING");

        int totalAmount = 0;
        int totalItems = 0;
        List<Product> originalProducts = new ArrayList<>();
        BillResponseBody billResponseBody = new BillResponseBody();
        List<BillProductDTO> billProductDTOS = new ArrayList<>();
        for(SingleProductOrderDTO product : products){
            UUID pid = product.getPid();
            Product originalProduct = productUtil.isValidProductID(pid);
            if(originalProduct == null){
                throw new InvalidProductException(String.format("Product with id %s does not exist", pid.toString()));
            }

            int totalQuantity = originalProduct.getQunatity();
            if(totalQuantity <= 0){
                throw new InvalidProductException(String.format(
                        "Product with id %s does not have sufficient quantity",
                        pid.toString()
                ));
            }
            BillProductDTO billProductDTO = new BillProductDTO();
            billProductDTO.setProductID(originalProduct.getId());
            billProductDTO.setQuantity(product.getQuantity());
            billProductDTO.setTotalPrice(originalProduct.getPrice()*product.getQuantity());
            billProductDTO.setSupplierName(originalProduct.getSeller().getName());
            billProductDTO.setProductName(originalProduct.getName());
            billProductDTOS.add(billProductDTO);
            originalProducts.add(originalProduct);
            totalItems += product.getQuantity();
            totalAmount += originalProduct.getPrice()*product.getQuantity();
            int finalQuantity =  totalQuantity - product.getQuantity();
            productRepository.updateProductQunatity(finalQuantity, pid);
            int totalQuantitySold = originalProduct.getQunatitySold() + product.getQuantity();
            productRepository.updateTotalProductQunatity(totalQuantitySold, pid);
        }

        order.setTotalAmount(totalItems);
        order.setTotalAmount(totalAmount);
        order.setProducts(originalProducts);
        order.setPaymentMethod(placeOrderDTO.getPaymentMethod().toString());
        orderRepository.save(order);

        billResponseBody.setOrderID(order.getId());
        billResponseBody.setProducts(billProductDTOS);
        billResponseBody.setTotalPrice(totalAmount);
        billResponseBody.setOrderPlacedDate(order.getOrderPlaced());
        billResponseBody.setOrderExpectedDate(order.getExpectedDelivery());
        billResponseBody.setShopperEmail(user.getEmail());
        billResponseBody.setTotalQuantity(totalItems);
        return billResponseBody;
    }
}
