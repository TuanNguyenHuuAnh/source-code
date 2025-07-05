package vn.com.unit.cms.core.module.agent.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;

@Getter
@Setter
public class CertificateAnotherSearchDto extends CommonSearchWithPagingDto {
	private String agentCode;
	private String certificateName;
	private String effectiveDate;
}
