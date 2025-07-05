package vn.com.unit.cms.core.module.customer.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.customerManagement.dto.SubmittedDetailDto;

@Getter
@Setter
public class ProductInformationDto extends SubmittedDetailDto {

    private String planId; // ten san pham 	
    private String polInsrdName;//NĐBH
    private Date cliBthDt;//Ngay sinh NDBH
    private String cliSexCd; // Gioi tinh
    private String cvgFaceAmt;// so tien bao hiem
    private Date cvgIssEffDt;// ngay hieu luc
    private Date cvgMatXpryDt;//Ngay dao han
    private String cvgCstatCd;//Trang thai CVG_CSTAT_CD
    private BigDecimal cvgMpremAmt;  //Phí định kỳ/cơ bản định kỳ
    private String cvgNum; // mã Coverage
    private String cliName;// nguoi mua bao hiem
    private String cvgPlanId;// loai san pham
    private String mainProduct; // san pham chinh
    private String cvgStatusCd; // CVG_STATUS_CD
    private String polCstatCd; // mã trạng thái
	private BigDecimal cvgBasicPremAmt; // Phí bảo hiểm theo kỳ
	private Date premPayXpryDt;	// Ngày hết hạn đóng phí
	private String premPayXpryDtNote;	// Note(Ngày hết hạn đóng phí)
	private String cvgIssEffDtNote ;  //Note(Ngày hiệu lực)
}
