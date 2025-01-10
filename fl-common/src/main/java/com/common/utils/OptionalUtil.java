package com.common.utils;

import com.core.exception.NoDataException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

public class OptionalUtil {
    public static <T> T getOrElseThrow(Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new NoDataException(message));
    }
}