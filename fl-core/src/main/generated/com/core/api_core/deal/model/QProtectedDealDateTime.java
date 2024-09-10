package com.core.api_core.deal.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProtectedDealDateTime is a Querydsl query type for ProtectedDealDateTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProtectedDealDateTime extends EntityPathBase<ProtectedDealDateTime> {

    private static final long serialVersionUID = -1435306538L;

    public static final QProtectedDealDateTime protectedDealDateTime = new QProtectedDealDateTime("protectedDealDateTime");

    public final DateTimePath<java.time.LocalDateTime> dealCancellationDateTime = createDateTime("dealCancellationDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dealCompletionDateTime = createDateTime("dealCompletionDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dealCompletionRequestDateTime = createDateTime("dealCompletionRequestDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dealStartDateTime = createDateTime("dealStartDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> depositCancelDateTime = createDateTime("depositCancelDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> depositCompletionDateTime = createDateTime("depositCompletionDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> depositRequestDateTime = createDateTime("depositRequestDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QProtectedDealDateTime(String variable) {
        super(ProtectedDealDateTime.class, forVariable(variable));
    }

    public QProtectedDealDateTime(Path<? extends ProtectedDealDateTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProtectedDealDateTime(PathMetadata metadata) {
        super(ProtectedDealDateTime.class, metadata);
    }

}

