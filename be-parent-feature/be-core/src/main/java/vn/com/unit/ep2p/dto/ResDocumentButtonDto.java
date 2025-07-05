package vn.com.unit.ep2p.dto;

public class ResDocumentButtonDto {

	private String buttonId;
	
	private String buttonName;
	
	private String buttonValue;
	
	private String buttonType;
	
	private Long buttonOrder;
	
	private boolean isSave;
	
    private boolean isAuthenticate;
    
    private boolean isSign;
    
    private boolean isExportPdf;  
    
    private boolean fieldSign;

	private String buttonClass;
	
	
	private String isSaveEform;
	
	private boolean displayHistory;
	
    /**
     * Get buttonId
     * @return String
     * @author taitt
     */
    public String getButtonId() {
        return buttonId;
    }

    
    /**
     * Set buttonId
     * @param   buttonId
     *          type String
     * @return
     * @author  taitt
     */
    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

    
    /**
     * Get buttonName
     * @return String
     * @author taitt
     */
    public String getButtonName() {
        return buttonName;
    }

    
    /**
     * Set buttonName
     * @param   buttonName
     *          type String
     * @return
     * @author  taitt
     */
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    
    /**
     * Get buttonType
     * @return String
     * @author taitt
     */
    public String getButtonType() {
        return buttonType;
    }

    
    /**
     * Set buttonType
     * @param   buttonType
     *          type String
     * @return
     * @author  taitt
     */
    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

	public String getButtonValue() {
		return buttonValue;
	}


	public void setButtonValue(String buttonValue) {
		this.buttonValue = buttonValue;
	}


	public Long getButtonOrder() {
		return buttonOrder;
	}


	public void setButtonOrder(Long buttonOrder) {
		this.buttonOrder = buttonOrder;
	}


	public boolean getIsSave() {
		return isSave;
	}


	public void setIsSave(boolean isSave) {
		this.isSave = isSave;
	}


	public String getButtonClass() {
		return buttonClass;
	}


	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}


	/**
	 * Get isAuthenticate
	 * @return boolean
	 * @author taitt
	 */
	public boolean getIsAuthenticate() {
		return isAuthenticate;
	}


	/**
	 * Set isAuthenticate
	 * @param   isAuthenticate
	 *          type boolean
	 * @return
	 * @author  taitt
	 */
	public void setIsAuthenticate(boolean isAuthenticate) {
		this.isAuthenticate = isAuthenticate;
	}


	/**
	 * Get isSign
	 * @return String
	 * @author taitt
	 */
	public boolean getIsSign() {
		return isSign;
	}


	/**
	 * Set isSign
	 * @param   isSign
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setIsSign(boolean isSign) {
		this.isSign = isSign;
	}


	public boolean getIsExportPdf() {
		return isExportPdf;
	}


	public void setIsExportPdf(boolean isExportPdf) {
		this.isExportPdf = isExportPdf;
	}


    
    public String getIsSaveEform() {
        return isSaveEform;
    }


    
    public void setIsSaveEform(String isSaveEform) {
        this.isSaveEform = isSaveEform;
    }


    
    public boolean isFieldSign() {
        return fieldSign;
    }


    
    public void setFieldSign(boolean fieldSign) {
        this.fieldSign = fieldSign;
    }


    
    public boolean getDisplayHistory() {
        return displayHistory;
    }


    
    public void setDisplayHistory(boolean displayHistory) {
        this.displayHistory = displayHistory;
    }
}
