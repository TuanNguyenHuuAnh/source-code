package vn.com.unit.cms.core.module.emulate.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

/**
 * @author TaiTM
 */
@Getter
@Setter
@ToString
public class EmulateResultSearchResultDto extends CmsCommonSearchResultFilterDto {
	
    private String memoNo;//so meme

    private String contestName;
    
    private String content;

    private Date startDate;
    
    private Date endDate;
    
    private Date effectiveDate;
    
    private Date expiredDate;
    
    private String contestType; //thi dua/thach thuc

    private String isHot;
    
    private String isOds;
    
    private String createdBy;
    
    private Date createDate;
    
    private String updateBy;
    
    private Date updateDate;
    
    private String agentCode;//tvtc
    private String agentName;
    private String agentType;
    
    private String managerCode;//ql tvtc
    private String managerName;
    private String managerType;
    
    private String bmCode;
    private String bmName;
    private String bmType;
    
    private String gadCode;
    private String gadName;
    
    private String gaCode;
    
    private String bdohCode;
    private String bdohName;
    
    private String bdrhCode;
    private String bdrhName;
    
    private String bdahCode;
    private String bdahName;
    
    private String bdthCde;
    private String bdthName;
    
    private String regionName;
    private String areaName;
    private String territoryName;
    private String officeName;
    
	private String reportingtoCode;
	private String reportingtoName;
	private String reportingtoType;
	
	private String policyNo;//so hd

	private Date appliedDate;

	private Date issuedDate;

	private String result;//giai thuong

	private String advance;//tam ung

	private String bonus;//tra bo sung

	private String clawback;//thu hoi

	private String note;
	
	private String description;
	
	private String contractNo;
	
    private String contestPhysicalFile;
    
    private String contestPhysicalImg;
    
    private Integer enable;
    
    private String type; //ngan/dai
    
    private String objectType;//loai doi tuong
    
}
