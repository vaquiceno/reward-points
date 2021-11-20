package com.overactive.reward.rewardpoints.controller;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.exception.UserNotFoundException;
import com.overactive.reward.rewardpoints.model.User;
import com.overactive.reward.rewardpoints.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    @GetMapping("/rewardUser")
    public ResponseEntity<Long> getRewardUser(
        @RequestParam(value = "user_id", required = true) final Long user_id,
        @RequestParam(value = "from", required = true) final Timestamp from,
        @RequestParam(value = "to", required = true) final Timestamp to) {
        try {
            Long reward = userService.rewardUser(user_id, from, to);
            return new ResponseEntity<>(reward, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
