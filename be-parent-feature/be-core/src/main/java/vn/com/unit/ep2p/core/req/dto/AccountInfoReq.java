/*******************************************************************************
 * Class        ：ReqAccountDto
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：SonND
 * Change log   ：2020/12/02：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.req.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.ep2p.core.annotation.Date;
import vn.com.unit.ep2p.core.annotation.Pattern;

/**
 * ReqAuthenDto
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Getter
@Setter
public class AccountInfoReq {
	@ApiModelProperty(notes = "Full name of account on system", example = "theanh", required = true, position = 0)
    private String fullname;
	
	@ApiModelProperty(notes = "Birthday of account on system", example = "20201103142435", required = true, position = 0)
	@Date(format = CommonDateUtil.YYYYMMDDHHMMSS)
	private String birthdayStr;
	
	@ApiModelProperty(notes = "Gender of account on system", example = "M", required = true, position = 0)
    private String gender;
	
	@ApiModelProperty(notes = "Email of account on system", example = "theanh@gmail.com", required = true, position = 0)
	@Pattern(regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
	private String email;
	
	@ApiModelProperty(notes = "Phone number of account on system", example = "0123456789", required = true, position = 0)
    @Pattern(regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")
	private String phone;
	
	@ApiModelProperty(notes = "Encode file to Base64", position = 0)
    private String imgBase64;
	
	@ApiModelProperty(notes = "Name file upload", example = "avatar",  position = 0)
    private String fileName;
	
	@ApiModelProperty(notes = "Received email", example = "false",  position = 1)
    private Boolean receivedEmail;
    @ApiModelProperty(notes = "Received notification", example = "true",  position = 2)
    private Boolean receivedNotification;
    
    @ApiModelProperty(notes = "Enable account", example = "false",  position = 1)
    private Boolean enabled;
    @ApiModelProperty(notes = "Acctived acount", example = "true",  position = 2)
    private Boolean actived;
    
    @ApiModelProperty(notes = "Team of account", example = "[1]", position = 0)
    private List<Long> team;
    
    @ApiModelProperty(notes = "Type of account", example = "GUEST", position = 0)
    private String accountType;
    
    public Boolean getReceivedEmail() {
        return receivedEmail;
    }
    
    public Boolean getReceivedNotification() {
        return receivedNotification;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public Boolean getActived() {
        return actived;
    }
}
