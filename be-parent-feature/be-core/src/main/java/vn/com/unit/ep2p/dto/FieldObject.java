/*******************************************************************************
 * Class        FieldObject
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * 
 * FieldObject
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class FieldObject {

    private String fieldName;

    private Object value1;
    
    private Object value2;
    
    public FieldObject(String fieldName, Object value1, Object value2) {
    	this.fieldName = fieldName;
    	this.value1 = value1;
    	this.value2 = value2;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Object getValue1() {
		return value1;
	}

	public void setValue1(Object value1) {
		this.value1 = value1;
	}

	public Object getValue2() {
		return value2;
	}

	public void setValue2(Object value2) {
		this.value2 = value2;
	}
}