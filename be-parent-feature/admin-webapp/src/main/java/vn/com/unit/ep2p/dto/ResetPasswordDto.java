package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.constant.MessageList;

@Getter
@Setter
public class ResetPasswordDto{
	
	private Long id;
	private String code;
	private String agentStatus;
	private MessageList messageList;	
	private String emailDlvn;

	private String chooseLogin;
	private String chooseMail;
	private String agent;
	private String languageCode;
		
	private String newPassword;
	private String result;				//nessage tra ve
	private String isError;			
	private String send;				//mail gui
	private String typePassowrd;		//loai password
	private String message;

	
}
