package vn.com.unit.ep2p.admin.dto;


/**
 * 
 * MessageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public class MessageDto{
    
    private String sessionCount ;
    
    private String from;
    private String functionCode;
    private String message;
    private String to;
    
    public MessageDto(){
        
    }

    public MessageDto(String sessionCount){
        this.sessionCount = sessionCount;
    }
    
    public String getSessionCount() {
        return sessionCount;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}