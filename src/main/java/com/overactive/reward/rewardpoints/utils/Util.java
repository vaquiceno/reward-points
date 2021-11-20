package com.overactive.reward.rewardpoints.utils;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.model.User;

import java.sql.Timestamp;

public class Util {
    private static final Long FIRST_VALUE = 50L;
    private static final Long FIRST_POINT = 1L;
    private static final Long SECOND_VALUE = 100L;
    private static final Long SECOND_POINT = 2L;

    public static Long rewardPoints(Long value){
        Long reward = 0L;
        Long valuemodified = value;
        if (valuemodified > SECOND_VALUE){
            reward += (valuemodified - SECOND_VALUE)*SECOND_POINT;
            valuemodified = SECOND_VALUE;
        }
        if (valuemodified > FIRST_VALUE){
            reward += (valuemodified - FIRST_VALUE)*FIRST_POINT;
        }
        return reward;
    }

    public static Long acumRewardPoints(User user, Timestamp from, Timestamp to) throws BusinessException {
        //calculation of reward points using java 8 stream features
        try {
            return user
                    .getTransactions()
                    .stream()
                    .filter(
                            t -> (t.getCreated().after(from) && t.getCreated().before(to)
                            ))
                    .mapToLong(t -> rewardPoints(t.getValue()))
                    .sum();
        } catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }
}
