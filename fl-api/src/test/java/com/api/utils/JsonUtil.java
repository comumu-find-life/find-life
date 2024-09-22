package com.api.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

//    public static <T> T extractData(ResultActions resultActions, Class<T> valueType) throws Exception {
//        String jsonString = resultActions.andReturn().getResponse().getContentAsString();
//        JsonNode root = objectMapper.readTree(jsonString);
//        JsonNode dataNode = root.path("data");
//        return objectMapper.treeToValue(dataNode, valueType);
//    }
}