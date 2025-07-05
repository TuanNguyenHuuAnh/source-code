/*******************************************************************************
 * Class        ：CommonImportExcelDto
 * Created date ：2019/02/15
 * Lasted date  ：2019/02/15
 * Author       ：VinhLT
 * Change log   ：2019/02/15：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * CommonImportExcelDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class CommonImportExcelDto {

	private String pazzword;

	private List<String> sheetNames;

	private Integer startRowData;
	
	private String language;
	
	private String unknowParam;
	
	private String unknowParam2;
	
	private String unknowParam3;

	/**
	 * Get pazzword
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getPazzword() {
		return pazzword;
	}

	/**
	 * Set pazzword
	 * 
	 * @param pazzword type String
	 * @return
	 * @author VinhLT
	 */
	public void setPazzword(String pazzword) {
		this.pazzword = pazzword;
	}

	/**
	 * Get sheetNames
	 * 
	 * @return List<String>
	 * @author VinhLT
	 */
	public List<String> getSheetNames() {
		return sheetNames;
	}

	/**
	 * Set sheetNames
	 * 
	 * @param sheetNames type List<String>
	 * @return
	 * @author VinhLT
	 */
	public void setSheetNames(List<String> sheetNames) {
		this.sheetNames = sheetNames;
	}

	/**
	 * Get startRowData
	 * 
	 * @return Integer
	 * @author VinhLT
	 */
	public Integer getStartRowData() {
		return startRowData;
	}

	/**
	 * Set startRowData
	 * 
	 * @param startRowData type Integer
	 * @return
	 * @author VinhLT
	 */
	public void setStartRowData(Integer startRowData) {
		this.startRowData = startRowData;
	}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

	public String getUnknowParam() {
		return unknowParam;
	}

	public void setUnknowParam(String unknowParam) {
		this.unknowParam = unknowParam;
	}

	public String getUnknowParam2() {
		return unknowParam2;
	}

	public void setUnknowParam2(String unknowParam2) {
		this.unknowParam2 = unknowParam2;
	}

	public String getUnknowParam3() {
		return unknowParam3;
	}

	public void setUnknowParam3(String unknowParam3) {
		this.unknowParam3 = unknowParam3;
	}

	

}
