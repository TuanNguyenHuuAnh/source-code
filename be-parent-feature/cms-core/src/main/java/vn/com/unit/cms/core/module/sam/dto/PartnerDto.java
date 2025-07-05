package vn.com.unit.cms.core.module.sam.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartnerDto {

	private String partner;

	private String zone;
	
	@Getter(value = AccessLevel.NONE)
	private String partnerAndZone;

	public String getPartnerAndZone() {
		return partner + "|" + zone;
	}
}
