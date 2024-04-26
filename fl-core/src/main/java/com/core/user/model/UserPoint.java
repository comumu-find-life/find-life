package com.core.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 Point 관리 클래승
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_point_id")
    private Long id;

    private double point;

    public double decreasePoint(double price){
        point -= price;
        return point;
    }

    public double increasePoint(double price){
        point += price;
        return point;
    }

}
