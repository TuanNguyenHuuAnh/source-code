package vn.com.unit.ep2p.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.common.utils.CommonStringUtil;

/**
 * @author PhatLT
 *
 */
public class MultiSearchBoxUtils {
	
	private MultiSearchBoxUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static <T> void setConditionSearch(T searchDto, String searchValue, List<String> listSearchChecked, Map<String,String[]> mapFieldSearch) {
		try {
			Class<?> clazz = searchDto.getClass();
			Field [] field = populateFields(clazz);
			Map<String, Field> mapField = new HashMap<>();
			for(Field f : field) {
				mapField.put(f.getName().toUpperCase(), f);
			}
			List<String> fieldSearchs = new ArrayList<>();
			
			if(CommonMapUtil.isNotEmpty(mapFieldSearch)) {
				Set<String> setSearch = new HashSet<>();
				if(CommonCollectionUtil.isNotEmpty(listSearchChecked)) {
					for(String item : listSearchChecked) {
						String [] values = mapFieldSearch.get(item);
						if(values!= null && values.length > 0)
							setSearch.addAll(Arrays.asList(values));
					}
				}
				else {
					for(Map.Entry<String, String[]> item  : mapFieldSearch.entrySet()) {
						if(item.getValue()!=null && item.getValue().length > 0)
							setSearch.addAll(Arrays.asList(item.getValue()));
					}
				}
				fieldSearchs.addAll(setSearch);
			}
			if(CommonCollectionUtil.isNotEmpty(fieldSearchs)) {
				for(String search : fieldSearchs) {
					if(CommonStringUtil.isNotBlank(search) && mapField.get(search.trim())!= null) {
						Field f = mapField.get(search);
						f.setAccessible(true);
						f.set(searchDto, searchValue);
					}
				}
			}
		}
		catch (Exception e) {
			
		}
		
	}
	private static Field[] populateFields(Class<?> cls) {
		Field[] fieldsSuper = cls.getSuperclass().getDeclaredFields();
		Field[] fieldsChild = cls.getDeclaredFields();
		int superLength = fieldsSuper.length;
		int childLength = fieldsChild.length;
		Field[] fields = new Field[superLength + childLength];
		System.arraycopy(fieldsSuper, 0, fields, 0, superLength);
		System.arraycopy(fieldsChild, 0, fields, superLength, childLength);
		return fields;
	}
	
}
