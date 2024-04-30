package com.core.job.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDayAndTime is a Querydsl query type for DayAndTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayAndTime extends EntityPathBase<DayAndTime> {

    private static final long serialVersionUID = 1767381216L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDayAndTime dayAndTime = new QDayAndTime("dayAndTime");

    public final EnumPath<com.core.job.model.enums.Day> day = createEnum("day", com.core.job.model.enums.Day.class);

    public final TimePath<java.time.LocalTime> endTime = createTime("endTime", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QJob job;

    public final TimePath<java.time.LocalTime> startTime = createTime("startTime", java.time.LocalTime.class);

    public QDayAndTime(String variable) {
        this(DayAndTime.class, forVariable(variable), INITS);
    }

    public QDayAndTime(Path<? extends DayAndTime> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDayAndTime(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDayAndTime(PathMetadata metadata, PathInits inits) {
        this(DayAndTime.class, metadata, inits);
    }

    public QDayAndTime(Class<? extends DayAndTime> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job")) : null;
    }

}

