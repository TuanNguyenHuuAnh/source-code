package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralReportDto {
    private BigDecimal ipcsInMonth;
    private BigDecimal ipcsLastMonth;
    private BigDecimal ipcsYesterday;
    private BigDecimal ipcsRejected;
    private BigDecimal ipcsPending;
    private BigDecimal fypCancel;
    private BigDecimal fypRelease;
    private BigDecimal fypInMonth;
    private BigDecimal rypYear1;
    private BigDecimal rypYear2;
    private BigDecimal rypYear2Nd;
    private int numFeeInMonth;
    private int numFeeLastMonth;
    private int numPenInMonth;
    private int numPenLastMonth;
    private int numRejInMonth;
    private int numRejLastMonth;
    private int numProcessing;
    private int numCancel;
    private int numRelease;
}
