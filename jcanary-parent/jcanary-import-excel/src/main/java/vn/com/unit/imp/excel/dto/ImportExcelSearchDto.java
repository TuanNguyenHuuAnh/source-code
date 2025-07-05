/*******************************************************************************
s * Class        ：ImportExcelSearchDto
 * Created date ：2017/10/12
 * Lasted date  ：2017/10/12
 * Author       ：TaiTM
 * Change log   ：2017/10/12：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.dto;

import java.util.List;

import vn.com.unit.core.dto.ConditionSearchCommonDto;

/**
 * ImportExcelSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public class ImportExcelSearchDto extends ConditionSearchCommonDto {

    private String functionCode;

    private String langeCode;

    private String fieldSearch;

    private List<String> fieldValues;

    private String templateName;

    private String fileName;

    private String sessionKey;

    private boolean execData;

    private boolean isError;

    private boolean isSubmit;

    private boolean hasEdit = true;

    private String errorTempFile;
    
    private String transactionNo;
    
    private String channel;
    
    private String url;
    
    private boolean hasReload = true;
    
    private boolean hasCheck = false;
    
    private boolean showDownline;
    
    private String movementIdList;
    
    private List<Long> ids;
    
    private boolean deadLock = false;
    
    private boolean timeout = false;
    
    private boolean isDetail = false;
    
    private boolean excelFileInvalid = false;
    
    private String isMultiple;
    
    public String getIsMultiple() {
    	return isMultiple;
    }
    
    public void setIsMultiple(String isMultiple) {
    	this.isMultiple = isMultiple;
    }

    public String getErrorTempFile() {
        return errorTempFile;
    }

    public void setErrorTempFile(String errorTempFile) {
        this.errorTempFile = errorTempFile;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the functionCode
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param functionCode the functionCode to set
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the langeCode
     */
    public String getLangeCode() {
        return langeCode;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param langeCode the langeCode to set
     */
    public void setLangeCode(String langeCode) {
        this.langeCode = langeCode;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the fieldSearch
     */
    public String getFieldSearch() {
        return fieldSearch;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param fieldSearch the fieldSearch to set
     */
    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the fieldValues
     */
    public List<String> getFieldValues() {
        return fieldValues;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param fieldValues the fieldValues to set
     */
    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param sessionKey the sessionKey to set
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the execData
     */
    public boolean isExecData() {
        return execData;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param execData the execData to set
     */
    public void setExecData(boolean execData) {
        this.execData = execData;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the isError
     */
    public boolean isError() {
        return isError;
    }

    public boolean getIsError() {
        return isError;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param isError the isError to set
     */
    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    /**
     * @author TaiTM
     * @date Jun 11, 2020
     * @return the isSubmit
     */
    public boolean isSubmit() {
        return isSubmit;
    }

    public boolean getIsSubmit() {
        return isSubmit;
    }

    /**
     * @author TaiTM
     * @date Jun 11, 2020
     * @param isSubmit the isSubmit to set
     */
    public void setIsSubmit(boolean isSubmit) {
        this.isSubmit = isSubmit;
    }

    /**
     * @author TaiTM
     * @date Jul 6, 2020
     * @return the hasEdit
     */
    public boolean getHasEdit() {
        return hasEdit;
    }

    /**
     * @author TaiTM
     * @date Jul 6, 2020
     * @param hasEdit the hasEdit to set
     */
    public void setHasEdit(boolean hasEdit) {
        this.hasEdit = hasEdit;
    }

    /**
     * @author TaiTM
     * @date Jul 24, 2020
     * @return the transactionNo
     */
    public String getTransactionNo() {
        return transactionNo;
    }

    /**
     * @author TaiTM
     * @date Jul 24, 2020
     * @param transactionNo the transactionNo to set
     */
    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    /**
     * @author TaiTM
     * @date Jul 29, 2020
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @author TaiTM
     * @date Jul 29, 2020
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

	/**
	 * Get hasReload
	 * @return boolean
	 * @author TuyenNX
	 */
	public boolean getHasReload() {
		return hasReload;
	}

	/**
	 * Set hasReload
	 * @param   hasReload
	 *          type boolean
	 * @return
	 * @author  TuyenNX
	 */
	public void setHasReload(boolean hasReload) {
		this.hasReload = hasReload;
	}

	/**
	 * Get hasCheck
	 * @return boolean
	 * @author TuyenNX
	 */
	public boolean isHasCheck() {
		return hasCheck;
	}

	/**
	 * Set hasCheck
	 * @param   hasCheck
	 *          type boolean
	 * @return
	 * @author  TuyenNX
	 */
	public void setHasCheck(boolean hasCheck) {
		this.hasCheck = hasCheck;
	}

    /**
     * @author TaiTM
     * @date 19-08-2020
     * @return the channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @author TaiTM
     * @date 19-08-2020
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @author TaiTM
     * @date 19-08-2020
     * @return the showDownline
     */
    public boolean isShowDownline() {
        return showDownline;
    }
    
    public boolean getShowDownline() {
        return showDownline;
    }

    /**
     * @author TaiTM
     * @date 19-08-2020
     * @param showDownline the showDownline to set
     */
    public void setShowDownline(boolean showDownline) {
        this.showDownline = showDownline;
    }

    /**
     * @author TaiTM
     * @date 26-08-2020
     * @return the movementIdList
     */
    public String getMovementIdList() {
        return movementIdList;
    }

    /**
     * @author TaiTM
     * @date 26-08-2020
     * @param movementIdList the movementIdList to set
     */
    public void setMovementIdList(String movementIdList) {
        this.movementIdList = movementIdList;
    }

    /**
     * @author TaiTM
     * @date 08-09-2020
     * @return the deadLock
     */
    public boolean isDeadLock() {
        return deadLock;
    }
    
    public boolean getDeadLock() {
        return deadLock;
    }

    /**
     * @author TaiTM
     * @date 08-09-2020
     * @param deadLock the deadLock to set
     */
    public void setDeadLock(boolean deadLock) {
        this.deadLock = deadLock;
    }

    /**
     * @author TaiTM
     * @date 09-09-2020
     * @return the timeout
     */
    public boolean isTimeout() {
        return timeout;
    }
    
    public boolean getTimeout() {
        return timeout;
    }

    /**
     * @author TaiTM
     * @date 09-09-2020
     * @param timeout the timeout to set
     */
    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

	public boolean isDetail() {
		return isDetail;
	}

	public void setDetail(boolean isDetail) {
		this.isDetail = isDetail;
	}

    /**
     * @author TaiTM
     * @date 25 thg 11, 2020
     * @return the ids
     */
    public List<Long> getIds() {
        return ids;
    }

    /**
     * @author TaiTM
     * @date 25 thg 11, 2020
     * @param ids the ids to set
     */
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

	public boolean isExcelFileInvalid() {
		return excelFileInvalid;
	}

	public void setExcelFileInvalid(boolean excelFileInvalid) {
		this.excelFileInvalid = excelFileInvalid;
	}
    

}