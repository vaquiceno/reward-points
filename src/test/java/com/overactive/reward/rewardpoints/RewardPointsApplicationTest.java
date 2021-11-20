package com.overactive.reward.rewardpoints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RewardPointsApplicationTest {

    @Test
    public void testMain() throws Exception {
        RewardPointsApplication.main(new String[]{"args"});
    }
}