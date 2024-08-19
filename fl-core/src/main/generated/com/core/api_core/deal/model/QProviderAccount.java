package com.core.api_core.deal.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProviderAccount is a Querydsl query type for ProviderAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProviderAccount extends EntityPathBase<ProviderAccount> {

    private static final long serialVersionUID = 210673309L;

    public static final QProviderAccount providerAccount = new QProviderAccount("providerAccount");

    public final StringPath account = createString("account");

    public final StringPath accountHolder = createString("accountHolder");

    public final StringPath bankName = createString("bankName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QProviderAccount(String variable) {
        super(ProviderAccount.class, forVariable(variable));
    }

    public QProviderAccount(Path<? extends ProviderAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProviderAccount(PathMetadata metadata) {
        super(ProviderAccount.class, metadata);
    }

}

