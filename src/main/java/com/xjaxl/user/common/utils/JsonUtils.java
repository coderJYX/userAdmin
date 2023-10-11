package com.xjaxl.user.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;


public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private static final String TIME_ZONE = "Asia/Shanghai";

    private static final String TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    public static String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T jsonToObject(String jsonData, Class<T> beanType) throws IOException {
        return (T)objectMapper.readValue(jsonData, beanType);
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) throws IOException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, new Class[] { beanType });
        return (List<T>)objectMapper.readValue(jsonData, javaType);
    }

    public static <T> T jsonToObject(String jsonData) {
        return (T)objectMapper.convertValue(jsonData, new TypeReference<T>() {

        });
    }
}
