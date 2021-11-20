package com.overactive.reward.rewardpoints.controller;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.exception.UserNotFoundException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.model.User;
import com.overactive.reward.rewardpoints.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserControllerTest {

    private static final List<Transaction> TRANSACTIONS
            = List.of(Transaction
            .builder()
            .created(new Timestamp(new Date().getTime()))
            .value(120L)
            .build());

    private static final User USER = User
            .builder()
            .id(1L)
            .transactions(TRANSACTIONS)
            .build();

    private static final Timestamp FROM = new Timestamp(new Date(System.currentTimeMillis()-7*24*60*60*1000).getTime());
    private static final Timestamp TO = new Timestamp(new Date(System.currentTimeMillis()+7*24*60*60*1000).getTime());
    private static final Integer NUMBERPERIODS = 12;
    private static final Long RESULT = 90L;

    private static final Map<String, Long> LONG_MAP = Map.of("2021-11", 0L);
    private static final Map<String, Object> OBJECT_MAP = Map.of("total", 0L, "year-month", LONG_MAP);

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void getAllUsers() {
        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void addUser() {
        ResponseEntity<User> responseEntity = userController.addUser(USER);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void addUserException() throws BusinessException {
        doThrow(BusinessException.class).when(userService)
                .addUser(any());
        ResponseEntity<User> responseEntity = userController.addUser(User.builder().build());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void updateUser() {
        ResponseEntity<User> responseEntity = userController.updateUser(USER);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void updateUserException() throws BusinessException {
        doThrow(BusinessException.class).when(userService)
                .updateUser(any());
        ResponseEntity<User> responseEntity = userController.updateUser(User.builder().build());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getRewardUser() throws BusinessException {
        when(userService.rewardUser(USER.getId(), FROM, TO)).thenReturn(RESULT);
        ResponseEntity<Long> responseEntity = userController.getRewardUser(USER.getId(), FROM, TO);
        assertEquals(responseEntity.getBody(), RESULT);
    }

    @Test
    public void getRewardUserException() throws BusinessException {
        doThrow(UserNotFoundException.class).when(userService)
                .rewardUser(any(), any(), any());
        ResponseEntity<Long> responseEntity = userController.getRewardUser(USER.getId(), FROM, TO);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getRewardUserException2() throws BusinessException {
        doThrow(BusinessException.class).when(userService)
                .rewardUser(any(), any(), any());
        ResponseEntity<Long> responseEntity = userController.getRewardUser(USER.getId(), FROM, TO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getRewardUserLastMonths() throws BusinessException {
        when(userService.rewardUserLastMonths(USER.getId(), NUMBERPERIODS)).thenReturn(OBJECT_MAP);
        ResponseEntity<Map<String, Object>> responseEntity = userController.getRewardUserLastMonths(USER.getId(), NUMBERPERIODS);
        assertEquals(responseEntity.getBody(), OBJECT_MAP);
    }

    @Test
    public void getRewardUserLastMonthsException() throws BusinessException {
        doThrow(UserNotFoundException.class).when(userService)
                .rewardUserLastMonths(any(), any());
        ResponseEntity<Map<String, Object>> responseEntity = userController.getRewardUserLastMonths(USER.getId(), NUMBERPERIODS);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getRewardUserLastMonthsException2() throws BusinessException {
        doThrow(BusinessException.class).when(userService)
                .rewardUserLastMonths(any(), any());
        ResponseEntity<Map<String, Object>> responseEntity = userController.getRewardUserLastMonths(USER.getId(), NUMBERPERIODS);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

}