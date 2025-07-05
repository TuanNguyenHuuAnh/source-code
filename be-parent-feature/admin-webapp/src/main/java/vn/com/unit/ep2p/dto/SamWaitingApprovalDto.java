package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @version 01-00
 * @since 01-00
 * @author nt.tinh
 * @Last updated: 15/05/2024	nt.tinh SR16451 - Enhance SAM mADP/ADPortal
 */
@Getter
@Setter
public class SamWaitingApprovalDto {
	private Long id;
	private String zoneCode;
	private int numberWaitingApproval;
}
