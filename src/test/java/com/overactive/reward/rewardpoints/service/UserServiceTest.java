package com.overactive.reward.rewardpoints.service;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.model.User;
import com.overactive.reward.rewardpoints.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

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
    private static final User USER2 = User
            .builder()
            .id(1L)
            .name("prueba")
            .transactions(TRANSACTIONS)
            .build();
    private static final Timestamp FROM = new Timestamp(new Date(System.currentTimeMillis()-7*24*60*60*1000).getTime());
    private static final Timestamp TO = new Timestamp(new Date(System.currentTimeMillis()+7*24*60*60*1000).getTime());
    private static final Integer NUMBERPERIODS = 12;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void findAllUsers() {
        userService.findAllUsers();
    }

    @Test
    public void addUser() throws BusinessException {
        userService.addUser(USER);
    }

    @Test(expected = Exception.class)
    public void addUserException() throws BusinessException {
        doThrow(DataAccessResourceFailureException.class).when(userRepository).save(any());
        userService.addUser(User.builder().build());
    }

    @Test
    public void updateUser() throws BusinessException {
        when(userRepository.findUserById(USER.getId())).thenReturn(Optional.of(USER));
        userService.updateUser(USER2);
    }

    @Test
    public void updateUser2() throws BusinessException {
        when(userRepository.findUserById(USER.getId())).thenReturn(Optional.of(USER));
        userService.updateUser(USER);
    }

    @Test(expected = Exception.class)
    public void updateUserException() throws BusinessException {
        userService.updateUser(User.builder().build());
    }

    @Test
    public void rewardUser() throws BusinessException {
        when(userRepository.findUserById(USER.getId())).thenReturn(Optional.of(USER));
        userService.rewardUser(USER.getId(), FROM, TO);
    }

    @Test(expected = Exception.class)
    public void rewardUserException() throws BusinessException {
        userService.rewardUser(null, FROM, TO);
    }

    @Test
    public void rewardUserLastMonths() throws BusinessException {
        when(userRepository.findUserById(USER.getId())).thenReturn(Optional.of(USER));
        userService.rewardUserLastMonths(USER.getId(), NUMBERPERIODS);
    }

    @Test(expected = Exception.class)
    public void rewardUserLastMonthsException() throws BusinessException {
        userService.rewardUserLastMonths(null, NUMBERPERIODS);
    }
}