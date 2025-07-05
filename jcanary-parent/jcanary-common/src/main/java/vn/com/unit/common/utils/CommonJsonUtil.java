/*******************************************************************************
 * Class        ：CommonJsonUtil
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：taitt
 * Change log   ：2020/11/10：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.exception.SystemException;

/**
 * CommonJsonUtil.
 *
 * @author taitt
 * @version 01-00
 * @since 01-00
 */
public class CommonJsonUtil {

    /**
     * convertObjectToJSON.
     *
     * @author taitt
     * @param obj type {@link Object}
     * @return {@link String}
     * @throws JsonProcessingException the json processing exception
     */
    public static String convertObjectToJSON(Object obj) throws JsonProcessingException {
        String resultJson = null;
        ObjectWriter mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).setSerializationInclusion(Include.ALWAYS)
                .writer().withDefaultPrettyPrinter();
        resultJson = mapper.writeValueAsString(obj);
        return resultJson;
    }
    
    /**
     * convertJSONToObject.
     *
     * @author taitt
     * @param <T> the generic type
     * @param json                  type String
     * @param valueType                  type Class
     * @return Class
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static <T> T convertJSONToObject(String json, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, valueType);
    }
    
    /**
     * convertJSONToMap.
     *
     * @author taitt
     * @param json                  type String
     * @return Map
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Map<String, String> convertJSONToMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<Map<String, String>>() {
        });
    }
    
    /**
     * convertObjectToJsonNode.
     *
     * @author taitt
     * @param obj                  type Object
     * @return JsonNode
     */
    public static JsonNode convertObjectToJsonNode(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, JsonNode.class);
    }
    
    
	public static String convertObjectToJsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();

		String result = CommonConstant.EMPTY;

		if (object != null) {
			try {
				result = mapper.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				throw new SystemException("Error method convertObjectToJson :" + e.getMessage());
			}
		}

		return result;
	}
    
}
