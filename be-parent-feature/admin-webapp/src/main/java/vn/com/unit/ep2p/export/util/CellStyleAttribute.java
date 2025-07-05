package vn.com.unit.ep2p.export.util;

public class CellStyleAttribute {

	private StyleOption styleOption;
	
	private Object value;
	
	public CellStyleAttribute(StyleOption styleOption, Object value) {
		this.styleOption = styleOption;
		this.value = value;
	}

	public StyleOption getStyleOption() {
		return styleOption;
	}

	public void setStyleOption(StyleOption styleOption) {
		this.styleOption = styleOption;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
