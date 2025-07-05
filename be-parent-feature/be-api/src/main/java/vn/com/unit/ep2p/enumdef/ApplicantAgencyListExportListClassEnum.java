package vn.com.unit.ep2p.enumdef;

public enum ApplicantAgencyListExportListClassEnum {
	// ERS_CANDIDATE
	no("0"),
	applyForPosition("1"),
	candidateName("2"),
	dob("3"),
	genderName("4"),
	idNo("5"),
	idDateOfIssue("6"),
	idPlaceOfIssueName("7"),
	mobile("8"),
	email("9"),
	// recruiterCode("10"), // Số CMT hoặc Mã số NGT => nếu mã số không có thì lấy cmt
	recruiterIdNo("10"), // Số CMT hoặc Mã số NGT => nếu mã số không có thì lấy cmt
	recruiterName("11"),
	managerIndirectCode("12"), // Mã số Quản lý cấp trên gần => giáp tiếp cấp 1
	managerIndirectName("13"), // Họ tên Quản lý cấp trên gần nhất đã gia nhập Công ty => gián tiếp cấp 1
	managerIdNo("14"), // Số CMT hoặc Mã số QLTT
	managerName("15"), // Họ tên quản lý trực tiếp
	managerAdName("16"), // Họ tên Giám đốc kinh doanh khu vực (AD)
	
	// ERS_AGENCY_STRUCTURE
	directRdName("17"), // managerName => Họ tên Giám đốc kinh doanh vùng (RD)
	areaManagerName("18"), // managerName => Họ tên Giám đốc kinh doanh vùng cấp cao (SRD)
	
	// ERS_CANDIDATE
	classCode("19"), // Đăng ký học lớp MBSU
	classProvinceName("20"), // Tỉnh tổ chức lớp học
	classType("21"), // (candidate)
	onlineOffline("22"), //  Bạn có đăng ký Học online tập trung
	;
	
	private String value;

	private ApplicantAgencyListExportListClassEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}