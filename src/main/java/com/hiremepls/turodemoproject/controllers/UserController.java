package com.hiremepls.turodemoproject.controllers;

import com.hiremepls.turodemoproject.models.Users;
import com.hiremepls.turodemoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository repository;

    @GetMapping()
    public ResponseEntity<List<Users>> getUsers(@RequestParam(required = false) Integer id){
        try{
            List<Users> users = new ArrayList<>();
            if(Optional.ofNullable(id).isPresent()){
                users.addAll(repository.findAll());
            }else{
                users.addAll(repository.findUserById(id));
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Users> createUser(@RequestBody Users user){
        try {
            Users _users = repository
                    .save(new Users(user.getFirstName(), user.getLastName()));
            return new ResponseEntity<>(_users, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
