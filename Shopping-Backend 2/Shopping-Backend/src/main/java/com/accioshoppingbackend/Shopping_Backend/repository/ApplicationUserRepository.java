package com.accioshoppingbackend.Shopping_Backend.repository;

import com.accioshoppingbackend.Shopping_Backend.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {

    public ApplicationUser findByEmail(String email);
}
