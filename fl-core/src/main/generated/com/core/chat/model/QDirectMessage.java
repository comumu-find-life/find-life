package com.core.chat.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDirectMessage is a Querydsl query type for DirectMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectMessage extends EntityPathBase<DirectMessage> {

    private static final long serialVersionUID = 194494949L;

    public static final QDirectMessage directMessage = new QDirectMessage("directMessage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath messageContents = createString("messageContents");

    public final NumberPath<Long> receiverId = createNumber("receiverId", Long.class);

    public final NumberPath<Long> senderId = createNumber("senderId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> sentAt = createDateTime("sentAt", java.time.LocalDateTime.class);

    public QDirectMessage(String variable) {
        super(DirectMessage.class, forVariable(variable));
    }

    public QDirectMessage(Path<? extends DirectMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDirectMessage(PathMetadata metadata) {
        super(DirectMessage.class, metadata);
    }

}

