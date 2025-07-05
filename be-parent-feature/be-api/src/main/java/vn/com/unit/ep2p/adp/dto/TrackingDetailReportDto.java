package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingDetailReportDto {
	private Integer no;					// STT
    private String partner;				// Doi tac
    private String areaName;			// Ten Khu vuc (Area DLVN)
    private String regionalName; 		// Ten Vung (Region DLVN)
    private String zoneName;			// Ten Cum (Zone DLVN)
    private BigDecimal tp;           	// Tổng giá trị TP
    private BigDecimal ep;           	// Tổng giá trị EP
    private String regionalPartnerName; // Ten Vung
    private String zonePartnerName;		// Ten Cum
    private String uoName;				// Ten DVKD
    private String policyNo;			// So HD
    private String policyStatus;		// Tình Trạng HĐ
    private int premiumYear;			// Năm phí
}
