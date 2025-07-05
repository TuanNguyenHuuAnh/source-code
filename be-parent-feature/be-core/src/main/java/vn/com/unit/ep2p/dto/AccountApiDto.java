/***************************************************************
 * @author vunt					
 * @date Apr 5, 2021	
 * @project mbal-webapp
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountApiDto {
	private String username;
	private String password;
	private String responseCode;
	private String responseStrEn;
	private String responseStrVi;
	private String title;
	private Integer userId;
}
