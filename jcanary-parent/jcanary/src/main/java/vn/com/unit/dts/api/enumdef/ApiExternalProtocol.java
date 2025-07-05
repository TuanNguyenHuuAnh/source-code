/*******************************************************************************
 * Class        ：ApiExternal
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.dts.api.enumdef;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ApiExternal
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ApiExternalProtocol {
    
    REST_FULL("REST_FULL", "REST_FULL"),
    SOAP("SOAP", "SOAP");
    
    private String text;
    private String value;
    
    private static final Map<String, ApiExternalProtocol> mappings = new HashMap<>(ApiExternalProtocol.values().length);
    static {
        for (ApiExternalProtocol apiExternalProtocol : values()) {
            mappings.put(apiExternalProtocol.getValue(), apiExternalProtocol);
        }
    }
    
    public static ApiExternalProtocol resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }

}
