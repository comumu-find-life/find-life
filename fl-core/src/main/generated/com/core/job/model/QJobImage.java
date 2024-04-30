package com.core.job.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJobImage is a Querydsl query type for JobImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobImage extends EntityPathBase<JobImage> {

    private static final long serialVersionUID = 167858294L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJobImage jobImage = new QJobImage("jobImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QJob job;

    public QJobImage(String variable) {
        this(JobImage.class, forVariable(variable), INITS);
    }

    public QJobImage(Path<? extends JobImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJobImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJobImage(PathMetadata metadata, PathInits inits) {
        this(JobImage.class, metadata, inits);
    }

    public QJobImage(Class<? extends JobImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job")) : null;
    }

}

