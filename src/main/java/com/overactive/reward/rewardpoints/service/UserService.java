package com.overactive.reward.rewardpoints.service;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.exception.UserNotFoundException;
import com.overactive.reward.rewardpoints.model.User;
import com.overactive.reward.rewardpoints.repository.UserRepository;
import com.overactive.reward.rewardpoints.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
                return userRepository.save(user);
            }
            return user1;
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

    public Long rewardUser(Long user_id, Timestamp from, Timestamp to) throws BusinessException {
        try {
            User user = userRepository.findUserById(user_id)
                    .orElseThrow(() -> new UserNotFoundException("User with id "+ user_id +" not found"));
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
