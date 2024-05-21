package com.core.chat.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectMessageRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direct_message_room_id")
    private Long id;

    @Column(name = "user_1_id")
    private Long user1Id;

    @Column(name = "user_2_id")
    private Long user2Id;

}
