package com.overactive.reward.rewardpoints.utils;

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
}
