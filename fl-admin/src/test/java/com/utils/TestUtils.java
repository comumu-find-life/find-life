package com.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

public class TestUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 공통으로 사용될 메서드: JSON 응답을 특정 데이터 타입으로 변환
    public static <T> List<T> extractDataListFromResponse(ResultActions resultActions, TypeReference<List<T>> typeReference) throws Exception {
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        JsonNode dataNode = root.path("data"); // "data" 필드를 추출
        return objectMapper.convertValue(dataNode, typeReference);
    }

    // 공통으로 사용될 메서드: JSON 응답을 특정 데이터 타입으로 변환 (단일 객체)
    public static <T> T extractDataFromResponse(ResultActions resultActions, Class<T> clazz) throws Exception {
        String responseString = resultActions.andReturn().getResponse().getContentAsString();
        JsonNode root = objectMapper.readTree(responseString);
        JsonNode dataNode = root.path("data"); // "data" 필드를 추출
        return objectMapper.convertValue(dataNode, clazz);
    }
}
