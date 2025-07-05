 package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentContactInfoDto {
	private String provinceCode;
	private String provinceName;
	private String districtCode; 
    private String districtName;
    private String wardCode;
    private String wardName;
    private String address;
    private String taxCode;
    private String taxGroup;
    private String businessHouseholds;
}
    