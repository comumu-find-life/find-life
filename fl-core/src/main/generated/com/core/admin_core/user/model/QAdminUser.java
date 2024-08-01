package com.core.admin_core.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminUser is a Querydsl query type for AdminUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminUser extends EntityPathBase<AdminUser> {

    private static final long serialVersionUID = 712326895L;

    public static final QAdminUser adminUser = new QAdminUser("adminUser");

    public final com.core.base.model.QBaseTimeEntity _super = new com.core.base.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public QAdminUser(String variable) {
        super(AdminUser.class, forVariable(variable));
    }

    public QAdminUser(Path<? extends AdminUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminUser(PathMetadata metadata) {
        super(AdminUser.class, metadata);
    }

}

