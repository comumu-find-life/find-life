package com.core.home.model;

import com.core.base.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class HomeOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    @Enumerated(EnumType.STRING)
    private Option option;
}
