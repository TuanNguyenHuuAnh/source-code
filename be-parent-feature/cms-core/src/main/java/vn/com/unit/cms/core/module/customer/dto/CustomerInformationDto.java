package vn.com.unit.cms.core.module.customer.dto;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInformationDto {
	private Long id;
	private Integer no;
	private String customerNo; // ma khach hang
	private String customerName; // ben mua BH
	private String customerType; // loai khac hang
	private String nickName; //nick name
	private Date dateOfBirth;//ngay sinh
	private String address;//dia chi lien lac
	private String phoneNumber;//dtdd
	private BigDecimal totalPolicyActive;//tong hd con hieu luc
	private BigDecimal totalPolicyInactive;//tong hd mat hieu luc
	private BigDecimal rewardPoints;//diem thuong
	private String gender;//gioi tinh
	private String maritalStatus;//tinh trang hon nhan
	private String email;//email
	private String homePhone;//dien thoai nha
	private String companyPhone;//dien thoai co quan
	private String permanentAddress; //dia chi thuong tru
	private String createBy;
	private String dcActivate;
	private String autoDebit;
	private String servicingAgent;
}
