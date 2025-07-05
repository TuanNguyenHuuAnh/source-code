package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingReportDto {
    private String id;           		// ID của khu vực
    private String partner;				// Doi tac
    private String areaName;			// Ten Khu vuc (Area DLVN)
    private String regionalName; 		// Ten Vung (Region DLVN)
    private String zoneName;			// Ten Cum (Zone DLVN)
    private BigDecimal tp;           	// Tổng giá trị TP
    private BigDecimal ep;           	// Tổng giá trị EP
    private BigDecimal total;        	// Tổng giá trị tổng cộng
    private BigDecimal revFypInMonth; 	// Doanh thu FYP trong tháng
    private BigDecimal revFypCanceled; 	// Doanh thu FYP đã hủy
    private BigDecimal ryp;          	// Giá trị RYP
    private BigDecimal fyp;          	// Giá trị FYP
    private BigDecimal tpRyp;        	// TP theo RYP
    private BigDecimal epRyp;        	// EP theo RYP
    private BigDecimal totalRyp;     	// Tổng theo RYP
}
