package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class CustomerChargeDto {
    private Date paymentDate;
    private String policyNo;
    private String customerId;
    private String customerName;
    private BigDecimal amount;
    private String no;
    private String status;

    private Date polPaidToDate;
    private String proposalNo;
}
