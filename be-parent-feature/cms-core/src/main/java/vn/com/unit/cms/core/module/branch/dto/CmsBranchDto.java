package vn.com.unit.cms.core.module.branch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmsBranchDto {
	private Long id;
  
    private String code;


    private String name;


    private String note;


    private String address;


    private String latitude;


    private String longtitude;


    private boolean is_primary;


    private String type;


    private String icon;


    private String phone;


    private String fax;


    private String district;


    private String city;


    private String workingHours;


    private String email;

    private String activeFlag;
}
