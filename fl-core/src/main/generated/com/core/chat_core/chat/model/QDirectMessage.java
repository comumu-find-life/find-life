package com.core.chat_core.chat.model;

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

    private static final long serialVersionUID = -797771731L;

    public static final QDirectMessage directMessage = new QDirectMessage("directMessage");

    public final NumberPath<Long> dealId = createNumber("dealId", Long.class);

    public final EnumPath<com.core.api_core.deal.model.DealState> dealState = createEnum("dealState", com.core.api_core.deal.model.DealState.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> isDeal = createNumber("isDeal", Integer.class);

    public final StringPath message = createString("message");

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

