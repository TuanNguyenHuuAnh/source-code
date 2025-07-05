package vn.com.unit.ep2p.adp.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmPolicyDeliveryResultDto {
	private boolean result;
	private Date confirmDate;
}
