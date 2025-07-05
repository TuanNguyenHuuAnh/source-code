/*******************************************************************************
 * Class        ：HeaderTable
 * Created date ：2018/09/09
 * Lasted date  ：2018/09/09
 * Author       ：KhoaNA
 * Change log   ：2018/09/09：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.export.util;

import java.util.ArrayList;
import java.util.List;

/**
 * HeaderTable
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class HeaderTable {
	
	List<List<CellHeader>> rowHeaders;
	
	public HeaderTable() {
		this.rowHeaders = new ArrayList<List<CellHeader>>();
	}
	
	public boolean isEmpty() {
		boolean isEmpty = true;
		
		if( rowHeaders != null && !rowHeaders.isEmpty() ) {
			isEmpty = false;
		}
		
		return isEmpty;
	}
	
	public CellHeader getColumnHeader(int rowIndex, int colIndex) {
		CellHeader columnHeader = null;
		
		List<CellHeader> rowHeader = this.getRowHeader(rowIndex);
		if( rowHeader != null && !rowHeader.isEmpty() ) {
			columnHeader = rowHeader.get(colIndex);
		}
		
		return columnHeader;
	}
	
	public void addColumnHeader(int rowIndex, CellHeader columnHeader) {
		List<CellHeader> rowHeader = this.getRowHeader(rowIndex);
		int indexLast = rowHeader.size();
		rowHeader.add(indexLast, columnHeader);
	}
	
	public void addColumnHeader(int rowIndex, int colIndex, CellHeader columnHeader) {
		List<CellHeader> rowHeader = this.getRowHeader(rowIndex);
		rowHeader.add(colIndex, columnHeader);
	}
	
	public List<CellHeader> getRowHeader(int rowIndex) {
		List<CellHeader> rowHeader = null;
		
		if( !this.isEmpty() ) {
			rowHeader = rowHeaders.get(rowIndex);
		}
		
		return rowHeader;
	}
	
	public void addRowHeader(List<CellHeader> rowHeader) {
		int size = this.size();
		this.addRowHeader(size, rowHeader);
	}
	
	public void addRowHeader(int rowIndex, List<CellHeader> rowHeaders) {
		this.rowHeaders.add(rowIndex, rowHeaders);
	}
	
	public int size() {
		int size = 0;
		
		if( rowHeaders != null && !rowHeaders.isEmpty() ) {
			size = rowHeaders.size();
		}
		
		return size;
	}
	
	public int sizeRowHeader(int rowIndex) {
		List<CellHeader> rowHeader = this.getRowHeader(rowIndex);
		
		int size = 0;
		if( rowHeader != null ) {
			size = rowHeader.size();
		}
		
		return size;
	}

	public List<List<CellHeader>> getRowHeaders() {
		return rowHeaders != null ? new ArrayList<>(rowHeaders) : new ArrayList<>();
	}

	public void setRowHeaders(List<List<CellHeader>> rowHeaders) {
		this.rowHeaders = rowHeaders != null ? new ArrayList<>(rowHeaders) : new ArrayList<>();
	}
}
