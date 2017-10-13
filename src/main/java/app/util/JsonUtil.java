package app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.StringWriter;

public class JsonUtil {
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON");
        }
    }

    public static String jsonData(boolean success, Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            if (success) mapper.writeValue(sw, new Success(data));
            else mapper.writeValue(sw, new Error(data));
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON");
        }
    }

    static class Success {
        public Object data;
        public Success(Object d) {
            data = d;
        }
    }
    static class Error {
        public Object error;
        public Error(Object e) {
            error = e;
        }
    }
}
