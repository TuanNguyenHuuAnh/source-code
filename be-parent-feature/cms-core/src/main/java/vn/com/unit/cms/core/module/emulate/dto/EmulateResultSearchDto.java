package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author TaiTM
 */
@Getter
@Setter
public class EmulateResultSearchDto extends EmulateSearchDto {
	
    private static final long serialVersionUID = -7143597216788347105L;
    
    private String memoCode;
    
    private String memoNo;

    
    private String contestName;

    private String code;
    
    private String agentCode;

    private String bdthCde;



}
