package vn.com.unit.ep2p.enumdef;

import java.util.HashMap;
import java.util.Map;

public enum CalendarTypeSortEnum {
	
	NAME("name","NAME"),
	CODE("code","CODE"),
	WORKINGHOURS("workingHours","WORKINGHOURS"),
	DESCRIPTION("description","DESCRIPTION"),
	COMPANYNAME("companyName","COMPANYNAME"),
	;
	
	private String value;
	private String valueMapping;
	
	private CalendarTypeSortEnum(String value, String valueMapping) {
		this.value = value;
		this.valueMapping = valueMapping;
	}
	
	private static final Map<String, CalendarTypeSortEnum> mappings = new HashMap<>(CalendarTypeSortEnum.values().length);
	
	static {
		for(CalendarTypeSortEnum docSort: values()) {
			mappings.put(docSort.getValueMapping(), docSort);
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValueMapping() {
		return valueMapping;
	}

	public void setValueMapping(String valueMapping) {
		this.valueMapping = valueMapping;
	}
	
	

}
