package com.overactive.reward.rewardpoints.controller;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.exception.UserNotFoundException;
import com.overactive.reward.rewardpoints.model.User;
import com.overactive.reward.rewardpoints.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser (
            @RequestBody User user){
        try{
            User user1 = userService.addUser(user);
            return new ResponseEntity<>(user1, HttpStatus.CREATED);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser (
            @RequestBody User user){
        try{
            User user1 = userService.updateUser(user);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rewardUser")
    public ResponseEntity<Long> getRewardUser(
        @RequestParam(value = "user_id", required = true) final Long user_id,
        @RequestParam(value = "from", required = true) final Timestamp from,
        @RequestParam(value = "to", required = true) final Timestamp to) {
        try {
            Long reward = userService.rewardUser(user_id, from, to);
            return new ResponseEntity<>(reward, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rewardUserLastMonths")
    public ResponseEntity<Map<String, Object>> getRewardUserLastMonths(
            @RequestParam(value = "user_id", required = true) final Long user_id,
            @RequestParam(value = "numberPeriods", required = true) final Integer numberPeriods) {
        try {
            Map<String, Object> reward = userService.rewardUserLastMonths(user_id, numberPeriods);
            return new ResponseEntity<>(reward, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
