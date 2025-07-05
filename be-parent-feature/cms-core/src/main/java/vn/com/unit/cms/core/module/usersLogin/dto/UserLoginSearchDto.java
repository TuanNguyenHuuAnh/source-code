package vn.com.unit.cms.core.module.usersLogin.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.ConditionSearchCommonDto;

@Getter
@Setter
public class UserLoginSearchDto extends ConditionSearchCommonDto {
	private static final long serialVersionUID = 1L;
	/** fieldSearch */
    private String fieldSearch;
    /** fieldValues */
    private String fieldValues;
    private String searchKeyIds;
    private String userName;
    private String loginType;
    private String status;
    private String browser;
    private String device;
    private String os;
    private String startTime;
    private String endTime;
    private Date startDate;
    private Date endDate;
    private String url;
}
