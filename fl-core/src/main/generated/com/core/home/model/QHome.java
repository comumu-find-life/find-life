package com.core.home.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHome is a Querydsl query type for Home
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHome extends EntityPathBase<Home> {

    private static final long serialVersionUID = 304588561L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHome home = new QHome("home");

    public final com.core.base.model.QBaseTimeEntity _super = new com.core.base.model.QBaseTimeEntity(this);

    public final NumberPath<Integer> bathRoomCount = createNumber("bathRoomCount", Integer.class);

    public final NumberPath<Integer> bedroomCount = createNumber("bedroomCount", Integer.class);

    public final NumberPath<Integer> bill = createNumber("bill", Integer.class);

    public final NumberPath<Integer> bond = createNumber("bond", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath dealSavable = createBoolean("dealSavable");

    public final EnumPath<com.core.user.model.Gender> gender = createEnum("gender", com.core.user.model.Gender.class);

    public final QHomeAddress homeAddress;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<HomeImage, QHomeImage> images = this.<HomeImage, QHomeImage>createList("images", HomeImage.class, QHomeImage.class, PathInits.DIRECT2);

    public final StringPath introduce = createString("introduce");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Integer> peopleCount = createNumber("peopleCount", Integer.class);

    public final NumberPath<Integer> rent = createNumber("rent", Integer.class);

    public final EnumPath<HomeType> type = createEnum("type", HomeType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QHome(String variable) {
        this(Home.class, forVariable(variable), INITS);
    }

    public QHome(Path<? extends Home> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHome(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHome(PathMetadata metadata, PathInits inits) {
        this(Home.class, metadata, inits);
    }

    public QHome(Class<? extends Home> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.homeAddress = inits.isInitialized("homeAddress") ? new QHomeAddress(forProperty("homeAddress")) : null;
    }

}

