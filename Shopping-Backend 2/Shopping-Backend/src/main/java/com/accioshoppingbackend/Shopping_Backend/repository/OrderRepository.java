package com.accioshoppingbackend.Shopping_Backend.repository;

import com.accioshoppingbackend.Shopping_Backend.model.ApplicationOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<ApplicationOrder, UUID> {

    @Query(value = "select * from application_order where shopper_id=:shopperID", nativeQuery = true)
    public List<ApplicationOrder> getOrderByShopperID(UUID shopperID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE application_order SET status = 'CANCEL' WHERE id=:orderID", nativeQuery = true)
    public void cancelStatus(UUID orderID);
}
