package com.accioshoppingbackend.Shopping_Backend.repository;

import com.accioshoppingbackend.Shopping_Backend.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET qunatity =:quantity WHERE id =:pid", nativeQuery = true)
    public void updateProductQunatity(int quantity, UUID pid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET qunatity_sold =:quantity WHERE id =:pid", nativeQuery = true)
    public void updateTotalProductQunatity(int quantity, UUID pid);
}
