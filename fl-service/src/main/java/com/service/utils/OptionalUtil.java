package com.service.utils;

import java.util.Optional;

public class OptionalUtil {

    public static <T> T getOrElseThrow(Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new EntityNotFoundException(message));
    }
}