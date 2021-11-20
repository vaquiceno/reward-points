package com.overactive.reward.rewardpoints.utils;

import com.overactive.reward.rewardpoints.exception.BusinessException;
import com.overactive.reward.rewardpoints.model.Transaction;
import com.overactive.reward.rewardpoints.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UtilTest {

    private static final Long VALUE_1 = 100L;
    private static final Long RESULT_1 = 50L;
    private static final Long VALUE_2 = 120L;
    private static final Long RESULT_2 = 90L;
    private static final List<Transaction> TRANSACTIONS
            = List.of(Transaction
                        .builder()
                        .created(new Timestamp(new Date().getTime()))
                        .value(120L)
                        .build());
    private static final User USER = User
                                    .builder()
                                    .transactions(TRANSACTIONS)
                                    .build();
    private static final Timestamp FROM = new Timestamp(new Date(System.currentTimeMillis()-7*24*60*60*1000).getTime());
    private static final Timestamp TO = new Timestamp(new Date(System.currentTimeMillis()+7*24*60*60*1000).getTime());
    private static final Long RESULT_3 = 90L;

    @InjectMocks
    private Util util;

    @Test
    public void rewardPoints_1() {
        Long r = util.rewardPoints(VALUE_1);
        assertEquals(r, RESULT_1);

    }

    @Test
    public void rewardPoints_2() {
        Long r = util.rewardPoints(VALUE_2);
        assertEquals(r, RESULT_2);

    }

    @Test
    public void acumRewardPoints() throws BusinessException {
        Long r = util.acumRewardPoints(USER, FROM, TO);
        assertEquals(r, RESULT_3);
    }

    @Test(expected = Exception.class)
    public void acumRewardPointsException() throws BusinessException {
        util.acumRewardPoints(null, FROM, TO);
    }
}