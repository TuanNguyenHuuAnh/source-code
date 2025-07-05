package vn.com.unit.ep2p.admin.dto;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInformationDetailDto {
	private String customerNo; // ma khach hang
	private String customerName; // ben mua BH
	private String customerType; // loai khac hang
	private String nickName; //nick name
	private String gender;//gioi tinh
	private String email;//email
	private Date dateOfBirth;//ngay sinh
	private String maritalStatus;//tinh trang hon nhan
	private String phoneNumber;//dtdd
	private String homePhone;//dien thoai nha
	private String companyPhone;//dien thoai co quan
	private String address;//dia chi lien lac
	private String permanentAddress; //dia chi thuong tru
	private String rewardPoints; //diem thuong
	private String dcActivate;
	private String autoDebit;
	private String cliIdCd;
	private String cliComplCd;
	
	private boolean result; //check quyen xem detail
}
