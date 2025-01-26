package com.common.utils;

import com.core.exception.NotFoundDataException;

import java.util.Optional;

public class OptionalUtil {
    public static <T> T getOrElseThrow(final Optional<T> optional, final String message) {
        return optional.orElseThrow(() -> new NotFoundDataException(message));
    }
}