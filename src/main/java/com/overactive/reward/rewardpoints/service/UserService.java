package com.overactive.reward.rewardpoints.service;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.exception.UserNotFoundException;
import com.overactive.reward.rewardpoints.model.User;
import com.overactive.reward.rewardpoints.repository.UserRepository;
import com.overactive.reward.rewardpoints.utils.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@Log4j2
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) throws BusinessException {
        try {
            log.info("Saving user..");
            return userRepository.save(user);
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public User updateUser(User user) throws BusinessException {
        try {
            User user1 = userRepository.findUserById(user.getId())
                    .orElseThrow(() -> new UserNotFoundException("User with id " + user.getId() + " not found"));
            if (!user1.equals(user)) {
                log.info("Updating user with id " + user.getId());
                return userRepository.save(user);
            }
            log.info("User was not updated");
            return user1;
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Long rewardUser(Long user_id, Timestamp from, Timestamp to) throws BusinessException {
        try {
            User user = userRepository.findUserById(user_id)
                    .orElseThrow(() -> new UserNotFoundException("User with id "+ user_id +" not found"));
            log.info("Calculating reward points");
            return Util.acumRewardPoints(user, from, to);
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Map<String, Object> rewardUserLastMonths(Long user_id, Integer numberPeriods) throws BusinessException {
        try {
            User user = userRepository.findUserById(user_id)
                    .orElseThrow(() -> new UserNotFoundException("User with id "+ user_id +" not found"));
            TreeMap<String, Object> finalMap = new TreeMap<>();
            TreeMap<String, Long> mapReward = new TreeMap<>();
            YearMonth ym = YearMonth.now();
            int currentMonth = ym.getMonth().getValue();
            int currentYear = ym.getYear();
            log.info("looking for previous months");
            for (int i = 0; i < numberPeriods; i++){
                ym = YearMonth.of(currentYear, currentMonth);
                mapReward.put(
                        ym.toString(),
                        Util.acumRewardPoints(user,
                        Timestamp.valueOf(ym.atDay(1).atStartOfDay()),
                        Timestamp.valueOf(ym.atEndOfMonth().atStartOfDay())));
                currentMonth -= 1;
                if (currentMonth == 0){
                    currentMonth = 12;
                    currentYear -= 1;
                }
            }
            log.info("information was retrieved successfully");
            finalMap.put("year-month", mapReward);
            finalMap.put("total",
                        mapReward
                        .values()
                        .stream()
                        .mapToLong(i -> i)
                        .sum());
            return finalMap;
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
