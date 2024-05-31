package com.service.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UpdateUtil {
    public static <T, U> void updateFields(T entity, U dto) {
        Field[] dtoFields = dto.getClass().getDeclaredFields();
        Field[] entityFields = entity.getClass().getDeclaredFields();

        Set<String> dtoFieldNames = new HashSet<>(Arrays.asList(Arrays.stream(dtoFields)
                .map(Field::getName)
                .toArray(String[]::new)));

        for (Field entityField : entityFields) {
            if (dtoFieldNames.contains(entityField.getName())) {
                try {
                    Field dtoField = dto.getClass().getDeclaredField(entityField.getName());
                    dtoField.setAccessible(true);
                    Object newValue = dtoField.get(dto);

                    if (newValue != null) {
                        entityField.setAccessible(true);
                        entityField.set(entity, newValue);
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    // 필드가 존재하지 않거나 접근할 수 없는 경우 처리
                    e.printStackTrace();
                }
            }
        }
    }
}
