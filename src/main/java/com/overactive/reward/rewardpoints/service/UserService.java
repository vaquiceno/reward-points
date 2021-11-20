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
import java.util.List;

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
            //calculation of reward points using java 8 stream features
            return user
                    .getTransactions()
                    .stream()
                    .filter(
                            t -> (t.getCreated().after(from) && t.getCreated().before(to)
                            ))
                    .mapToLong(t -> Util.rewardPoints(t.getValue()))
                    .sum();
            /*for (Transaction t : user.getTransactions()){
                System.out.println(t.getValue() + " / " + t.getCreated());
            }*/
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
