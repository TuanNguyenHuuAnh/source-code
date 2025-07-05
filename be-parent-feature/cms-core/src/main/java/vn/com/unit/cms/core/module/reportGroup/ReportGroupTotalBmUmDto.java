package vn.com.unit.cms.core.module.reportGroup;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGroupTotalBmUmDto{

    private Integer countBdthCode;
    private Integer countBdahCode;
    private Integer countBdrhCode;
    private Integer countBdohCode;
    private Integer countOfficeCode;
    private Integer countGadCode;

    private BigDecimal countUmTypeAgentcode;	//	Tổng số UM theo cây cấu trúc của BM
    private BigDecimal countFcAgentcode;		//	Tổng số FC theo cây cấu trúc của BM
    private BigDecimal countUmAgentcode;		//	Tổng số FC theo cây cấu trúc của BM
    private BigDecimal countNewRecruitment;	//	Tổng số tuyển dụng mới (MTD) theo cây cấu trúc của BM
    private BigDecimal countReinstate;			//	Tổng số khôi phục mã số (MTD) theo cây cấu trúc của BM
    private BigDecimal countActive;			//	Tổng số hoạt động (MTD) theo cây cấu trúc của BM
    private BigDecimal sumFyp;					//	Tổng số FYP (MTD)theo cây cấu trúc của BM
    private BigDecimal sumRyp;					//	Tổng số RYP (MTD)theo cây cấu trúc của BM



}
