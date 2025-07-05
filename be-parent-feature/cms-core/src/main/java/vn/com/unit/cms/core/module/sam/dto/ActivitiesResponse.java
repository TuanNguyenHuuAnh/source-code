package vn.com.unit.cms.core.module.sam.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivitiesResponse {

	private String partner;

	private String zone;
	
	private int isApproved = 0;
	
	private List<BuDto> buLst = new ArrayList<>();

	@Getter(value = AccessLevel.NONE)
	private String partnerAndZone;

	public String getPartnerAndZone() {
		return partner + "|" + zone;
	}
}
