package vn.com.unit.ep2p.admin.dto;

public class OutMessageDto {

    private String from;
    private String functionCode;
    private String message;
    
    public OutMessageDto() {
		// TODO Auto-generated constructor stub
	}
    
    public OutMessageDto(String from, String functionCode, String message) {
    	this.from = from;
    	this.functionCode = functionCode;
    	this.message = message;
    }
    
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
