package vn.com.unit.ep2p.enumdef;

public enum ApplicantAgencyListExportCreateBpEnum {
	no("0"),
	
	bpCode("1"), // bp => null
	
	applyForPosition("2"),// chức vụ dự kiến 
	
	classCode("3"), // lớp học
	candidateName("4"),
	genderName("5"),
	maritalStatus("6"),
	dobText("7"),
	nationality("8"),
	otherIdNo("9"),
	idType("10"),
	idNo("11"),
	
	createdDateText("12"), // ngày nhập => created date
	
	idDateOfIssueText("13"),
	
	idExpiredDateText("14"), // ngày hết hạn => ngày cấp + 15 năm
	
	idPlaceOfIssueName("15"),
	
	taxCode("16"),
	mobile("17"),
	email("18"),

	emailCompany("19"), // email công ty => null
	
	permanentAddress("20"), // số nhà <= Số nhà đường của địa chỉ thường trú
	
	permanentNest("21"), // phố/thôn/xóm <= Tổ/Xóm/Ấp/Khu phố địa chỉ thường trú
	
	permanentWardName("22"), // phường <= Tên Phường/Xã thường trú
	
	permanentDistrictName("23"), // quận/huyện <= Tên Quận/Huyện thường trú
	
	permanentProvinceName("24"), // tỉnh/thành phố <= Tên Tỉnh/Thành phố thường trú

	currentAddress("25"), // Số nhà đường của địa chỉ hiện tại
	currentNest("26"),	// Tổ/Xóm/Ấp/Khu phố của địa chỉ hiện tại
	currentWardName("27"), // Tên Phường/Xã hiện tại
	currentDistrictName("28"), //	Tên Quận/Huyện hiện tại
	currentProvinceName("29"), //	Tên Tỉnh/Thành phố hiện tại

	educationalName("30"), //Trình độ học vấn
	carrerCurrentName("31"), //	Nghề nghiệp hiện tại
	
	bankBranch("32"), // Mã chi nhánh ngân hàng
	
	accountNumber("33"), //	Số tài khoản của ứng viên
	
	accountName("34"), // Tên tài khoản - default là tên ứng viên
	
	recruiterBpCode("35"), // số BP người giới thiệu

	recruiterIdNo("36"),
	
	managerBpCode("37"), // số BP người quản lý trực tiếp
	
	managerIdNo("38"),
	
	// hồ sơ thiếu
	
	// ghi chú
	
	bpType("41"), // BP type
	
	CLASSTYPENAME("42"), // loại đăng ký
	
	OPTIONSUPPORT("43"), // Lựa chọn chính sách
	
	PARTNERMIC("44"), // Mã đơn vị - chi nhánh - vùng - miền
	
	// message error
	;
	
	private String value;

	private ApplicantAgencyListExportCreateBpEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}