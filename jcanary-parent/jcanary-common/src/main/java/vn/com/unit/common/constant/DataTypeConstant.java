/*******************************************************************************
 * Class        DataTypeConstant
 * Created date 2018/07/12
 * Lasted date  2018/07/12
 * Author       VinhLT
 * Change log   2018/07/1201-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.common.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * DataTypeConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Service
public class DataTypeConstant {

	public static final String DATA_TYPE_STRING = "String";

	public static final String DATA_TYPE_LONG = "Long";

	public static final String DATA_TYPE_DOUBLE = "Double";

	public static final String DATA_TYPE_FLOAT = "Float";

	public static final String DATA_TYPE_INTEGER = "Integer";

	public static final String DATA_TYPE_DATE = "Date";

	public static final String DATA_TYPE_BOOLEAN = "Boolean";
	
	public static final String DATA_TYPE_BIGDECIMAL = "BigDecimal";

	public Map<String, String> getDataType() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(DATA_TYPE_STRING, DATA_TYPE_STRING);
		map.put(DATA_TYPE_LONG, DATA_TYPE_LONG);
		map.put(DATA_TYPE_DOUBLE, DATA_TYPE_DOUBLE);
		map.put(DATA_TYPE_FLOAT, DATA_TYPE_FLOAT);
		map.put(DATA_TYPE_INTEGER, DATA_TYPE_INTEGER);
		map.put(DATA_TYPE_DATE, DATA_TYPE_DATE);
		map.put(DATA_TYPE_BOOLEAN, DATA_TYPE_BOOLEAN);
		map.put(DATA_TYPE_BIGDECIMAL, DATA_TYPE_BIGDECIMAL);
		return map;
	}
}
