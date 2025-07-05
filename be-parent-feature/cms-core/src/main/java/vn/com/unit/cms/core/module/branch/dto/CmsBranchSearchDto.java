package vn.com.unit.cms.core.module.branch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsBranchSearchDto {
	private String city;
	private String address;
	private  String branch;
	private String[] lstDistrict;
	private String district;
}
