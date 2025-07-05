package vn.com.unit.cms.core.module.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResultViewUMManpowerDto {
	private String officeCode;
	private String officeName;
	private String umCode;
	private String umName;
	private String reportingDate;
	private String agentCode;                      //tvtc
	private String agentName;
	private String agentType;
	//sumary
    private Integer fcQuantityByUm;                     //so luong fc
    private Integer newRecruitmentQuantityByUm;         //Tổng số tuyển dụng mới
    private Integer recoveryCodeQuantityByUm;           //Số lượng khôi phục mã số
    private Integer activeQuantityByUm;                 //Số lượng hoạt động
    private Integer fypTotalByUm;                       //Tổng số FYP 
    private Integer rypTotalByUm;                       //Tổng số RYP
    //detail
    private String activeFcQuantity;                //Số lượng FC hoạt động
    private String activeFcBySchemaQuantity;        //Số lượng FC hoạt động theo scheme
    private String dynamicFcQuantity;               //Số lượng FC năng động (PFC)
    private String numberOfturnActive;              //Số lượt hoạt động
    private String numberOfturnActiveLastYear;      //Số lượt hoạt động năm trước
}
