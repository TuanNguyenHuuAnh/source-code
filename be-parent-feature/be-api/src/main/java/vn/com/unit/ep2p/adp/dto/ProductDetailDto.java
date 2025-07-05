package vn.com.unit.ep2p.adp.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.module.customerManagement.dto.SubmittedDetailDto;

@Getter
@Setter
public class ProductDetailDto extends SubmittedDetailDto {

    private Date cliBthDt;//Ngay sinh NDBH
    private String cliSexCd; // Gioi tinh
    private String cvgInsAmt;// Menh gia bao hiem
    private BigDecimal cvgMpremAmt;  //Phí định kỳ/cơ bản định kỳ
    private String cvgNum; // mã Coverage
    private String cliName;// nguoi mua bao hiem
    private String cvgStatusCd; // CVG_STATUS_CD
    private String polCstatCd; // mã trạng thái
	private Date premPayXpryDt;	// Ngày hết hạn đóng phí
	private String premPayXpryDtNote;	// Note(Ngày hết hạn đóng phí)
	private String cvgIssEffDtNote ;  //Note(Ngày hiệu lực)
}
