package vn.com.unit.cms.core.module.ga.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeGaDto extends IncomeGaDetailDto {
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

    private String forwardPaid;                                             //  Số dư đầu kỳ
    private String grossMonth   ;                                           // Thu nhập phát sinh theo tháng (xuất hóa đơn)
    private String deductRequest;                                           // Các khoản khấu trừ sau xuất hóa đơn
    private String totalDeduct;                                             // Tổng khấu trừ
    private String holdAmount;                                              // Tổng thu nhập tạm giữ
    private String paymentAmount;                                           // Thực lãnh cuối kỳ
    private String debtY;                                                   // Tổng Dư nợ cuối kỳ

    private String bankAccountNumber;                                      // Số tài khoản TĐL
    private String bankAccountName;                                        // Tên ngân hàng TĐL
    private String taxCode;                                                // Mã số thuế
    private String payDate;                                                //  Ngày thanh toán

    private Map<String, Map<String,List<IncomeGaDto>>> data;

    private String  year ;                                            //  Năm
    private String  bonus;                                            // Thưởng
    private String  allowancePretax;                                  // Tổng số phụ cấp
    private String  grossY ;                                          //  Tổng số phát sinh
    private String  totalRecallInYear ;                               //  Tổng số thu hồi trong năm
    private String  payPeriodsDuringYear;                             //  Các kỳ chi trả trong năm
    private String  totalHould ;                                     //  Số dư cuối năm
    
    // WEEKLY 
    // Using for display drop down list Code - Name    
    private String officeNameDisplay;
    // column [Loai thu nhap] on table income weekly GA 
    private String typePaymentName;
    private Integer payAmount;  
    private BigDecimal payAmountBig;
    private Date paymentDate;


}
