package com.core.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 431381393L;

    public static final QUser user = new QUser("user");

    public final com.core.base.model.QBaseTimeEntity _super = new com.core.base.model.QBaseTimeEntity(this);

    public final NumberPath<Integer> brith = createNumber("brith", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    public final StringPath job = createString("job");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nationality = createString("nationality");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> phoneNumber = createNumber("phoneNumber", Integer.class);

    public final StringPath profileUrl = createString("profileUrl");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

