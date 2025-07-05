package vn.com.unit.cms.core.module.emulate.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class EmulationChallengeSearchDto extends CommonSearchWithPagingDto{
    private String agentCode;
    private String agentGroup;
    private Integer objectType;//agent/ga
    private Integer type; //ngan/dai han
    private Integer isChallenge;//thi dua/thach thuc
    private Object memoNo;
    
    //search group
    private String memoNoGroup;
    private String territoryCode;//miền
    private String areaCode;//vùng
    private String regionCode;//khu vực
    private String officeCode; //van phong
    private String agentChildSearch; //van phong
    
    //search personal
    private Object memoCode;
    private Object contestName;
    private Object startDate;
    private Object endDate;
    private Object content;
    private Object policyNo;
    private Object result;
    private Object advance;
    private Object bonus;
    private Object clawback;
    private Object effectiveDate;
    private Object expiredDate;
    
    private String checkPage;
    
}
