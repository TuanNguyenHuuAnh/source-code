package vn.com.unit.cms.admin.all.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonSearchResultFilterDto;

@Getter
@Setter
public class BranchSearchResultDto extends CmsCommonSearchResultFilterDto {

	private String address;

	private String city;

	private String region;

	private String district;
	
	private String type;
	
	private String typeName;
	
	private int activeFlag;

}
