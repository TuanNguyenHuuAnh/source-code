/***************************************************************
 * @author vunt					
 * @date Apr 5, 2021	
 * @project mbal-webapp
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendSmsApiRes {
	@JsonProperty("responseMessage")
	private String responseMessage;
	
	@JsonProperty("responseCode")
	private String responseCode;
}
