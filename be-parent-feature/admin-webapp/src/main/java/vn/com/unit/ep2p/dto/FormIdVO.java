package vn.com.unit.ep2p.dto;

//import java.util.Date;

public class FormIdVO {

	private int formId;
	private String compFormId;
	private String displayName;
	private int displayOrder;
	private String usedFlag;
	
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public String getCompFormId() {
		return compFormId;
	}
	public void setCompFormId(String compFormId) {
		this.compFormId = compFormId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getUsedFlag() {
		return usedFlag;
	}
	public void setUsedFlag(String usedFlag) {
		this.usedFlag = usedFlag;
	}
}
