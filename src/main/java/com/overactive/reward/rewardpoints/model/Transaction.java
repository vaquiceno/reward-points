package com.overactive.reward.rewardpoints.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="Transaction")
@Builder
@Setter
@Getter
public class Transaction {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long value;
    private Timestamp created;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
