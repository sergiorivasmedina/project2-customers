package com.bootcamp.customer.repositories;

import com.bootcamp.customer.model.Personal;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PersonalRepository extends ReactiveMongoRepository<Personal, String> {
    
}