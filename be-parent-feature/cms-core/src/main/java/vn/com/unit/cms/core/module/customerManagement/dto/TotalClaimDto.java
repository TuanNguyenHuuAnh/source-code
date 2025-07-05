package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalClaimDto {
    private int totalHandling;
    private int totalWaiting;
    private int totalAgreeing;
    private int totalRejecting;
    private int totalError;
}
