package com.core.deal.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProtectedDeal is a Querydsl query type for ProtectedDeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProtectedDeal extends EntityPathBase<ProtectedDeal> {

    private static final long serialVersionUID = 436124245L;

    public static final QProtectedDeal protectedDeal = new QProtectedDeal("protectedDeal");

    public final com.core.base.model.QBaseTimeEntity _super = new com.core.base.model.QBaseTimeEntity(this);

    public final NumberPath<Double> bond = createNumber("bond", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<DealState> dealState = createEnum("dealState", DealState.class);

    public final NumberPath<Long> getterId = createNumber("getterId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final NumberPath<Long> providerId = createNumber("providerId", Long.class);

    public QProtectedDeal(String variable) {
        super(ProtectedDeal.class, forVariable(variable));
    }

    public QProtectedDeal(Path<? extends ProtectedDeal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProtectedDeal(PathMetadata metadata) {
        super(ProtectedDeal.class, metadata);
    }

}

