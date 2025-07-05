package vn.com.unit.cms.core.module.ga.dto.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeSearchGa {

    private String agentCode;
    private String gadCode;
    private String orgCode;
    private String month;
    private String year;
    private boolean total;
    private boolean detail;
    private String paymentPeriod;
    private String cutOffDateYYYYMM;
    private String note2;
    private String note3;
    private String rejectReason;
}
