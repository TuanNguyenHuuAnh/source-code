package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AggregateReportDto {
    private String partner;
    private String area;
    private String region;
    private String zone;
	private BigDecimal ipcsInMonth;		// IPCS thu được - Trong tháng
    private BigDecimal ipcsLastMonth;	// IPCS thu được - Tháng trước chuyển sang
    private BigDecimal ipcsYesterday;	// IP_CS phát hành ngày hôm qua
    private BigDecimal ipcsRejected;	// IP_CS của HS từ chối
    private BigDecimal ipcsPending;		// IP_CS của HS pending
    private BigDecimal ipcsCancel;		// IP_CS HĐ hủy
    private BigDecimal ipcsRelease;		// IP_CS phát hành
    private BigDecimal ipcsReleaseYesterday;	// FYP phát hành ngày hôm qua
    private BigDecimal fypCancel;		// FYP HĐ hủy
    private BigDecimal fypRelease;		// FYP phát hành
    private BigDecimal fypReleaseYesterday;	// FYP phát hành ngày hôm qua
    private BigDecimal rfyp; 			// FYP Tái tục (RFYP)
    private BigDecimal fyp;				// FYP
    private BigDecimal ryp;				// RYP
    private BigDecimal tp1;				// TP năm 1
    private BigDecimal ep1;				// EP năm 1
    private BigDecimal tp2;				// TP năm 2
    private BigDecimal ep2;				// EP năm 2
    private BigDecimal tp3;				// TP năm 3
    private BigDecimal ep3;				// EP năm 3
    private BigDecimal epp;				// EPP
    private BigDecimal app;				// APP
    private BigDecimal epp2;			// EPP(K2+)
    private BigDecimal app2;			// APP(K2+)
    
    private int numFeeInMonth;			// HSYCBH - Trong tháng
    private int numFeeLastMonth;		// HSYCBH - Tháng trước chuyển sang
    private int numPenInMonth;			// HS pending - Trong tháng
    private int numPenLastMonth;		// HS pending - Tháng trước chuyển sang
    private int numRejInMonth;			// HS từ chối - Trong tháng
    private int numRejLastMonth;		// HS từ chối - Tháng trước chuyển sang
    private int numCancel;				// HĐ hủy
    private int numRelease;				// HĐ phát hành
}
