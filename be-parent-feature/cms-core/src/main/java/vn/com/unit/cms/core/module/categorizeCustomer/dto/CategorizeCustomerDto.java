package vn.com.unit.cms.core.module.categorizeCustomer.dto;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorizeCustomerDto {
	private Long id;
	private String proposalNo; // Ma so HSYCBH
    private String poName;  // Ten khach hang
    private String partnerName; // partnerName
    private String status; // Trạng thái hồ sơ
    private Integer isEdit;      // Phân quyền
    private String partnerCode;   // partnerCode
    private String csOfficeCode; // Mã đối tác
    private Date submittedDate;  // Ngày nộp hồ sơ về công ty
    private Date createdDate;    // Ngay tao HSYCBH
    private String createdBy;    // Người phân loại
    private String clientCode;
    private String businessCode;
    private String agentCode;
	private String clientName;
    private String businessName;
    private String agentName;
    private String createdName;
    private String orgCode; // Mã phòng CN/GD
    private String clientOther;
    private String businessOther;
 
}