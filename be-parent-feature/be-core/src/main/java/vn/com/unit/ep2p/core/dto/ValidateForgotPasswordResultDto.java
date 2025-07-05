/***************************************************************
 * @author HaND					
 * @date Apr 5, 2021	
 * @project dsuccess-webapp
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateForgotPasswordResultDto {

	@JsonProperty("Result")
	private String result;

	@JsonProperty("ErrLog")
	private String errLog;

	@JsonProperty("NewAPIToken")
	private String newAPIToken;

	@JsonProperty("Message")
	private String message;
}
