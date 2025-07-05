package vn.com.unit.ep2p.dto;

//import java.util.Date;

public class SelectedDeptVO {

	private int formId;
	private int useGroupCode;
	private String GroupName;
	
	public int getFormId() {
		return formId;
	}
	public void setFormId(int formId) {
		this.formId = formId;
	}
	public int getUseGroupCode() {
		return useGroupCode;
	}
	public void setUseGroupCode(int useGroupCode) {
		this.useGroupCode = useGroupCode;
	}
	public String getGroupName() {
		return GroupName;
	}
	public void setGroupName(String groupName) {
		GroupName = groupName;
	}
}
