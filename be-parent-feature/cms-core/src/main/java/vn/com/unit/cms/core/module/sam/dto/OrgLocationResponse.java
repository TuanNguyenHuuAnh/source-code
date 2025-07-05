package vn.com.unit.cms.core.module.sam.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.dto.OrgLocationDto;

@Getter
@Setter
public class OrgLocationResponse {

	private List<OrgLocationDto> orgLocationLst;
	
	private List<String> partnerLst;
}
