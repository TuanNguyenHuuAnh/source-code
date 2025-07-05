package vn.com.unit.cms.core.module.customer.dto;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerPotentialDto {
	private Long id;
	private Integer no;							// STT
	private String customerNo; 					// Ma KH
	private String customerName; 				// Ho ten KH
	private String gender;						// Gioi tinh
	private String customerType;				// Nhom khach hang
	private String nickName; 					// Biet danh
	private String clientStatus;				// Tinh trang
	private Date birthDate;						// Ngay sinh
	private String maritalStatus;				// Tinh trang hon nhan
	private String nationality;					// Quoc tich
	private String phoneNumber;					// So dien thoai
	private String email;						// Email
	private Date createdDate;					// Ngay tao ma so KH
	private Date reachDate;						// Ngay het han tiep can KH
	private BigDecimal totalProposalSubmited;	// Tong HSYCBH da nop
	private BigDecimal totalProposalReject;		// Tong HSYCBH bi tu choi
	private BigDecimal totalPolicyActive;		// Tong hd con hieu luc
	private BigDecimal totalPolicyInactive;		// Tong hd mat hieu luc
	private String address;						// Dia chi lien lac
	private String notes;						// Ghi chu
}
