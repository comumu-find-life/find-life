package com.api.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 공통 헬퍼 메서드: ResultActions에서 응답을 추출하고 data 필드를 원하는 객체로 변환하는 메서드
    public static  <T> T extractDataFromResult(ResultActions resultActions, Class<T> valueType) throws Exception {
        String responseString = resultActions.andReturn().getResponse().getContentAsString();  // 응답 문자열 추출
        JsonNode root = objectMapper.readTree(responseString);  // JSON 파싱
        JsonNode dataNode = root.path("data");  // data 필드 추출
        return objectMapper.treeToValue(dataNode, valueType);  // 원하는 타입으로 변환
    }
}