package com.common.utils;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public class OptionalUtil {

    public static <T> T getOrElseThrow(Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new EntityNotFoundException(message));
    }
}