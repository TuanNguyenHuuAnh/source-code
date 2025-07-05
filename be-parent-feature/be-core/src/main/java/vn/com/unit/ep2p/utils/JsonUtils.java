/*******************************************************************************
 * Class        ：JsonUtils
 * Created date ：2019/04/12
 * Lasted date  ：2019/04/12
 * Author       ：HungHT
 * Change log   ：2019/04/12：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JsonUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class JsonUtils {

    /**
     * convertObjectToJSON
     * 
     * @param obj
     * @return
     * @author HungHT
     */
    public static String convertObjectToJSON(Object obj) {
        String resultJson = null;
        try {
            ObjectWriter mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).setSerializationInclusion(Include.NON_NULL)
                    .writer().withDefaultPrettyPrinter();
            resultJson = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            resultJson = null;
        }
        return resultJson;
    }

    /**
     * convertJSONToObject
     * 
     * @param json
     * @param valueType
     * @return
     * @throws IOException
     * @author HungHT
     */
    public static <T> T convertJSONToObject(String json, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, valueType);
    }

    /**
     * convertJSONToMap
     * 
     * @param json
     * @return
     * @throws IOException
     * @author HungHT
     */
    public static Map<String, String> convertJSONToMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
    }
    
    /**
     * convertObjectToJsonNode
     * @param obj
     * @return
     * @author KhoaNA
     */
    public static JsonNode convertObjectToJsonNode(Object obj) {
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.convertValue(obj, JsonNode.class);
    }
}
