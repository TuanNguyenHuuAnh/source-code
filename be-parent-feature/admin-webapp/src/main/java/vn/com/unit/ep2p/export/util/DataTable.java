/*******************************************************************************
 * Class        ：DataTable
 * Created date ：2018/09/09
 * Lasted date  ：2018/09/09
 * Author       ：KhoaNA
 * Change log   ：2018/09/09：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.export.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * DataTable
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@SuppressWarnings("serial")
public class DataTable extends ArrayList<Map<String,Object>> {
	
    List<Map<String,Object>> data;
	
	public DataTable() {
		this.data = new ArrayList<Map<String,Object>>();
	}
	
	public DataTable(List<Map<String,Object>> data) {
		this.data = data;
	}
	
	public Map<String,Object> getRow(int rowIndex) {
		Map<String,Object> row = null;
		
		if( !this.isEmpty() ) {
			row = data.get(rowIndex);
		}
		
		return row;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
	public void addRow(Map<String,Object> row) {
		int rowIndex = 0;
		
		if( !this.isEmpty() ) {
			rowIndex = data.size();
		}
		
		this.addRow(rowIndex, row);
	}
	
	public void addRow(int rowIndex, Map<String,Object> row) {
		if( null != data ) {
			data.add(rowIndex, row);
		}
	}
	
	public void removeRow(int rowIndex) {
		if( !this.isEmpty() ) {
			data.remove(rowIndex);
		}
	}
	
	public void setValueOfCell(String columnName, Object value) {
		this.setValueOfCell(0, columnName, value);
	}
	
	public void setValueOfCell(int rowIndex, String columnName, Object value) {
		if( !this.isEmpty() ) {
			Map<String,Object> row = data.get(rowIndex);
			row.put(columnName, value);
		}
	}
	
	public Object getObjectValueOfCell(String columnName) {
		Object result = this.getObjectValueOfCell(0, columnName);
		return result;
	}
	
	public Object getObjectValueOfCell(int rowIndex, String columnName) {
		Object result = null;
		
		if( !this.isEmpty() ) {
			Map<String,Object> row = data.get(rowIndex);
			result = row.get(columnName);
		}
		
		return result;
	}
	
	public BigDecimal getBigDecimalValueOfCell(String columnName) {
		BigDecimal result = this.getBigDecimalValueOfCell(0, columnName);
		return result;
	}
	
	public BigDecimal getBigDecimalValueOfCell(int rowIndex, String columnName) {
		BigDecimal result = null;
		
		Object objectValue = this.getObjectValueOfCell(rowIndex, columnName);
		if( null != objectValue ) {
			result = new BigDecimal(String.valueOf(((Number) objectValue)));
		}
		
		return result;
	}
	
	public String getStringValueOfCell(String columnName) {
		String result = this.getStringValueOfCell(0, columnName);
		return result;
	}
	
	public String getStringValueOfCell(int rowIndex, String columnName) {
		String result = null;
		
		Object objectValue = this.getObjectValueOfCell(rowIndex, columnName);
		if( null != objectValue ) {
			result = String.valueOf(objectValue);
		}
		
		return result;
	}
	
	public Date getDateValueOfCell(String columnName) {
		Date result = this.getDateValueOfCell(0, columnName);
		return result;
	}
	
	public Date getDateValueOfCell(int rowIndex, String columnName) {
		Date result = null;
		
		Object objectValue = this.getObjectValueOfCell(rowIndex, columnName);
		if( null != objectValue ) {
			result = (Date) objectValue;
		}
		
		return result;
	}
	
	public Boolean getBooleanValueOfCell(String columnName) {
		Boolean result = this.getBooleanValueOfCell(0, columnName);
		return result;
	}
	
	public Boolean getBooleanValueOfCell(int rowIndex, String columnName) {
		Boolean result = false;
		
		Object objectValue = this.getObjectValueOfCell(rowIndex, columnName);
		if( null != objectValue ) {
			result = new Boolean(objectValue.toString());
		}
		
		return result;
	}
	
	public Double getDoubleValueOfCell(String columnName) {
		Double result = this.getDoubleValueOfCell(0, columnName);
		return result;
	}
	
	public Double getDoubleValueOfCell(int rowIndex, String columnName) {
		Double result = 0D;
		
		Object objectValue = this.getObjectValueOfCell(rowIndex, columnName);
		if( null != objectValue ) {
			result = new Double(objectValue.toString());
		}
		
		return result;
	}
	
	public DataType getDataTypeOfCell(int rowIndex, String columnName) {
		DataType dataType = null;
		
		Object objectValue = this.getObjectValueOfCell(rowIndex, columnName);
		if( objectValue != null ) {
			if(objectValue instanceof Number) {
				dataType = DataType.NUMERIC;
			} else if( objectValue instanceof Date ) {
				dataType = DataType.DATE;
			} else if( objectValue instanceof Boolean ) {
				dataType = DataType.BOOLEAN;
			} else {
				dataType = DataType.STRING;
			}
		}
		
		return dataType;
	}
	
	@Override
	public int size() {
		int countData = 0;
		
		if( null != data ) {
			countData = data.size();
		}
		
		return countData;
	}
	
	public boolean isEmpty() {
		boolean isEmpty = false;
		
		if( null == data || data.isEmpty() ) {
			isEmpty = true;
		}
		
		return isEmpty;
	}
	
	public List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<>();
		
		if( !this.isEmpty() ) {
			Map<String, Object> row = data.get(0);
			columnNames = new ArrayList<>(row.keySet());
		}
		
		return columnNames;
	}
	
	@Override
	public boolean add(Map<String, Object> e) {
	    return data.add(e);
	}
}
