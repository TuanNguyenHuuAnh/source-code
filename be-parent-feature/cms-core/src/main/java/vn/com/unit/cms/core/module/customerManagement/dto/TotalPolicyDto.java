package vn.com.unit.cms.core.module.customerManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalPolicyDto {
    private int totalContractEffect;
    private int totalContractInvalid;
    private int totalContractExpired30Days;
    private int totalContractExpired;
    private int totalContractDueFee;
    private int totalBusiness;
    private int sumOfPolicyHanding;
    private int sumOfPolicyClaim;
    private int totalContractOverdueFeeRyp;
    private int totalRenew;
    private int totalContractOrphan;
    private int sumOfPolicyPaidTodateAll;
    private int totalPolicyMatured;
    private int totalContractAll;
    private int totalContractCeases;
}
