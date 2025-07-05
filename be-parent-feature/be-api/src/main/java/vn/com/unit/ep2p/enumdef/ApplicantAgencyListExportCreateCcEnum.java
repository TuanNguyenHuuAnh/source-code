package vn.com.unit.ep2p.enumdef;

public enum ApplicantAgencyListExportCreateCcEnum {
	// ERS_CANDIDATE

	NO("0"),
	
	AGENTCODE("1"), // Mã số/ CC
	
	DATEAPPOINTED("2"), // Ngày cấp code
	
	BPCODE("3"), // bp => null
	
	APPLYFORPOSITION("4"), // Vị trí ứng tuyển
	CANDIDATENAME("5"), // Họ và tên
	IDNO("6"),
	RECRUITERIDNO("7"), // Số CMT hoặc Mã số NGT
	RECRUITERNAME("8"),
	MANAGERIDNO("9"), // Số CMT hoặc Mã số QLTT
	MANAGERNAME("10"), // Họ tên quản lý trực tiếp
	MANAGERINDIRECTCODE("11"), // Mã số Quản lý cấp trên gần => giáp tiếp cấp 1
	MANAGERINDIRECTNAME("12"), // Họ tên Quản lý cấp trên gần nhất đã gia nhập Công ty => gián tiếp cấp 1
	
//	MANAGERADNAME("13"), // Họ tên Giám đốc kinh doanh khu vực (AD)
//	
//	// ERS_AGENCY_STRUCTURE
//	DIRECTRDNAME("14"), // managerName => Họ tên Giám đốc kinh doanh vùng (RD)
//	AREAMANAGERNAME("15"), // managerName => Họ tên Giám đốc kinh doanh vùng cấp cao (SRD)

	// 2021-06-11 LocLT Fix Bug #44375
	RECRUITERADNAME("13"),
	MANAGERADNAME("14"),
	MANAGERINDIRECTADNAME("15"),

	MBFUFLAG("16"), // Hoàn thành MBSU
	MBFSFLAG("17"), // Hoàn thành MBFS

	CLASSCODE("18"), // Đăng ký học lớp MBFS

	EXAMFLAG("19"),// Hoàn thành đào tạo để cấp mã số (chỉ áp dụng đối với loại đăng ký Học full MBSU và Đủ ĐK chỉ học MBFS

	// ASC kiểm tra
	CHECKAG("21"), // AR kiểm tra

	;

	private String value;

	private ApplicantAgencyListExportCreateCcEnum(String value) {
		this.value = value;
	}

	@Override // fix NoClassDefFoundError
	public String toString() {
		return value;
	}
}
