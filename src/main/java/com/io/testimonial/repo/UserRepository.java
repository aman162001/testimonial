package com.io.testimonial.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.io.testimonial.model.User;


public interface UserRepository extends MongoRepository<User,ObjectId> {
    
    User findByEmail(String email);

    void deleteByEmail(String email);

}
