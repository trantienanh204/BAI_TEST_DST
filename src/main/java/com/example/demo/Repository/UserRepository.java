package com.example.demo.Repository;

import com.example.demo.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Users,String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Users findByUsername(String username);
    Users findByUsernameAndDeleteFalse(String username);
}
