package com.overactive.reward.rewardpoints.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="User")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Transaction> transactions;
}
