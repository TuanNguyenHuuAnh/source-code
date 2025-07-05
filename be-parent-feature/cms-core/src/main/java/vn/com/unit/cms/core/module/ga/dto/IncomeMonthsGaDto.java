package vn.com.unit.cms.core.module.ga.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class IncomeMonthsGaDto {
    private String gadCode;
    private String gadName;
    private String offfice;
    private String orgCode;                                         //  orgcode
    private String officeName;                                      //  orgname
    private String manager;
    private String numberTdl;
    private String gaType;                                                  //  Cờ GA chính        1 - GA chính, 0 - GA phụ
    private String segmentGa;                                               //  Phân hạng
    private String dateCutoff;                                              //  Ngày cutoff
    private String bankAccountNumber;                                      // Số tài khoản TĐL
    private String bankAccountName;                                        // Tên ngân hàng TĐL
    private String taxCode;                                                // Mã số thuế
    private String payDate;                                                //  Ngày thanh toán

    private String bonus;													// thuong
    private String allowancePretax;                                         // Phụ cấp
    private String forwardPaid;                                             //  Số dư đầu kỳ
    private String grossMonth   ;                                           // Thu nhập phát sinh theo tháng (xuất hóa đơn)
    private String deductRequest;                                           // Các khoản khấu trừ sau xuất hóa đơn
    private String totalDeduct;                                             // Tổng khấu trừ
    private String holdAmount;                                              // Tổng thu nhập tạm giữ
    private String paymentAmount;                                           // Thực lãnh cuối kỳ
    private String debtY;                                                   // Tổng Dư nợ cuối kỳ

    private String mainCode;
    private String mainName;                                         //  Tên khoản mục chính
    private String subCode;
    private String subName;                                          //  Tên khoản mục con
    private String itemCode;
    private String itemName;                                         // Tên chi tiết thu nhập
    private BigDecimal amount;                                           //  Số tiền
    private String id;
    private String levelId;

    private Map<String, Map<String,List<IncomeMonthsGaDto>>> data;

}
