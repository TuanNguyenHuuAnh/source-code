package vn.com.unit.ep2p.core.ers.dto;

//import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantAgencyListExportCreateBpDto {
	private String no;

	private String bpCode; // bp => null

	private String applyForPosition;// chức vụ dự kiến

	private String classCode; // lớp học
	private String candidateName;
	private String genderName;
	private String maritalStatus;

	private String dobText;

	private String nationality;
	private String otherIdNo;
	private String idType;
	private String idNo;

	private String createdDateText; // ngày nhập => created date

	private String idDateOfIssueText;

	private String idExpiredDateText; // ngày hết hạn => ngày cấp + 15 năm

	private String idPlaceOfIssueName;

	private String taxCode;
	private String mobile;
	private String email;

	private String emailCompany; // email công ty => null

	private String permanentAddress; // số nhà <= Số nhà đường của địa chỉ thường trú

	private String permanentNest; // phố/thôn/xóm <= Tổ/Xóm/Ấp/Khu phố địa chỉ thường trú

	private String permanentWardName; // phường <= Tên Phường/Xã thường trú

	private String permanentDistrictName; // quận/huyện <= Tên Quận/Huyện thường trú

	private String permanentProvinceName; // tỉnh/thành phố <= Tên Tỉnh/Thành phố thường trú

	private String currentAddress; // Số nhà đường của địa chỉ hiện tại
	private String currentNest; // Tổ/Xóm/Ấp/Khu phố của địa chỉ hiện tại
	private String currentWardName; // Tên Phường/Xã hiện tại
	private String currentDistrictName; // Tên Quận/Huyện hiện tại
	private String currentProvinceName;// Tên Tỉnh/Thành phố hiện tại

	private String educationalName; // Trình độ học vấn
	private String carrerCurrentName; // Nghề nghiệp hiện tại

	private String bankBranch; // Mã chi nhánh ngân hàng
	private String accountName; // Tên tài khoản - default là tên ứng viên
	private String accountNumber; // Số tài khoản của ứng viên

	private String recruiterBpCode; // số BP người giới thiệu

	private String recruiterIdNo;

	private String managerBpCode; // số BP người quản lý trực tiếp

	private String managerIdNo;

	// hồ sơ thiếu

	// ghi chú

	private String bpType; // BP type

	private String classTypeName; // loại đăng ký

	private String optionSupport; // Lựa chọn chính sách

	private String partnerMic; // Mã đơn vị - chi nhánh - vùng - miền

	// message error
}
