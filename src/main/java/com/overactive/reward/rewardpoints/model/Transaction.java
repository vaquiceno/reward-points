package com.overactive.reward.rewardpoints.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="Transaction")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long value;
    private Timestamp created;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("transaction")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
}
