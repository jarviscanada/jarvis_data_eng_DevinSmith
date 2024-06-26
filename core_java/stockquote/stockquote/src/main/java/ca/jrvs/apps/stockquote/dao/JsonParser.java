package ca.jrvs.apps.stockquote.dao;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class JsonParser {
    /**
     * Convert a java object to JSON string
     * @param object input object
     * @return JSON string
     * @throws JsonProcessingException
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
        throws JsonProcessingException {
            ObjectMapper m = new ObjectMapper();
            if (!includeNullValues) {
                m.setSerializationInclusion(Include.NON_NULL);
            }
            if (prettyJson) {
                m.enable(SerializationFeature.INDENT_OUTPUT);
            }
            return m.writeValueAsString(object);
        }

    /**
     * Parse JSON string to a object
     * @param json JSON str
     * @param clazz object class
     * @param <T> Type
     * @return Object
     * @throws IOException
     */
    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
        ObjectMapper m = new ObjectMapper();
        //m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T) m.readValue(json, clazz);
    } 
}
