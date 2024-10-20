package com.chatting.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "mongoUser")
public class MongoUser {
    @Id
    private String id;
    private String name;
    private Double price;
}
