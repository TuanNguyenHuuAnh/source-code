package vn.com.unit.cms.core.module.income.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthPaymentDto {
	private String mainCode;    
	private String mainName;   // chỉ tiêu 
	private BigDecimal janAmount; // dữ liệu tháng 1
	private BigDecimal febAmount;	// dữ liệu tháng 2
	private BigDecimal marAmount;	// dữ liệu tháng 3
	private BigDecimal aprAmount;	// dữ liệu tháng 4
	private BigDecimal mayAmount;	// dữ liệu tháng 5
	private BigDecimal junAmount;	//dữ liệu tháng 6
	private BigDecimal julAmount;	// dữ liệu tháng 7
	private BigDecimal augAmount;	// dữ liệu tháng 8
	private BigDecimal sepAmount;	//dữ liệu tháng 9
	private BigDecimal octAmount;	// dữ liệu tháng 10
	private BigDecimal novAmount;  //dữ liệu tháng 11
	private BigDecimal decAmount;  // dữ liệu tháng 12
	private String  style;      // màu sắc
}
