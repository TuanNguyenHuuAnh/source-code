package vn.com.unit.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class AaGaOfficeDto {

    private String id;
    private String text;
    private String note;
    private String name;
    private String clazz;
    private String kind;
    private Long orders;
    private String title;
	private String orgCode;

	private Long gadCode;

	private String segmentGa;


	private String gaDfaFlag;


	private String reportToGa;

	private String gaMotherCode;

	private String bankCode;

	private String bankAccountCode;
	
	private String bankAccountName;

	private String bankBranch;

	private String bankAccountNumber;
	
	private String transactionNo;
	
	private Integer startupFlag;
	
	private String gadName;

}

