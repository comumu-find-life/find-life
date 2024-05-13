package com.core.job.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJob is a Querydsl query type for Job
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJob extends EntityPathBase<Job> {

    private static final long serialVersionUID = 270372837L;

    public static final QJob job = new QJob("job");

    public final com.core.base.model.QBaseTimeEntity _super = new com.core.base.model.QBaseTimeEntity(this);

    public final EnumPath<com.core.job.model.enums.JobCategory> category = createEnum("category", com.core.job.model.enums.JobCategory.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final ListPath<DayAndTime, QDayAndTime> dayAndTime = this.<DayAndTime, QDayAndTime>createList("dayAndTime", DayAndTime.class, QDayAndTime.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<JobImage, QJobImage> images = this.<JobImage, QJobImage>createList("images", JobImage.class, QJobImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath position = createString("position");

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public final StringPath storeName = createString("storeName");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QJob(String variable) {
        super(Job.class, forVariable(variable));
    }

    public QJob(Path<? extends Job> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJob(PathMetadata metadata) {
        super(Job.class, metadata);
    }

}

