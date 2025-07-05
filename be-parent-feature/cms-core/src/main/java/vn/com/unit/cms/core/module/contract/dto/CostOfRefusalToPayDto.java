package vn.com.unit.cms.core.module.contract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostOfRefusalToPayDto {
	String serviceTariffName;
	Double declineAmount;
	String remarks;
}
