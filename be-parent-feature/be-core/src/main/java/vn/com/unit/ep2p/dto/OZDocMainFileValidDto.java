package vn.com.unit.ep2p.dto;

public class OZDocMainFileValidDto {

	private String formId;
	private String invalidType;
	private int pageIndex;
	private String tooltipText;

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getInvalidType() {
		return invalidType;
	}

	public void setInvalidType(String invalidType) {
		this.invalidType = invalidType;
	}

    
    public int getPageIndex() {
        return pageIndex;
    }

    
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    
    public String getTooltipText() {
        return tooltipText;
    }

    
    public void setTooltipText(String tooltipText) {
        this.tooltipText = tooltipText;
    }


}
