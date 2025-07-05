package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListContactHistory {
	NO("0"), 				// STT
	PARTNER("1"), 			// Đối Tác
	POLICYNO("2"), 			// Số HĐBH
	POLICYOWNER("3"), 		// BMBH
	POLISSUEEFF("4"), 		// Ngày hiệu lực
	POLBILLMODECD("5"), 	// Định kỳ đóng phí
	POLMPREMAMT("6"), 		// Phí bảo hiểm định kỳ
	TP("7"), 				// Phí dự tính đóng định kỳ
	DUETODATE("8"), 		// Ngày đến hạn đóng phí
	EXPECTEDPREMIUM("9"), 	// Phí tái tục cần phải thu 
	REFERRALCODE("10"), 	// Mã NVGTCT
	REFERRALBANKCODE("11"), // Mã NH NVGTCT
	REFERRALNAME("12"), 	// Tên NVGTCT
	PACKAGENAME("13"), 		// Sản phẩm
	REGION("14"), 			// Khu Vực/ Vùng
	BRANCHCODE("15"), 		// Mã Cụm
	BRANCHNAME("16"), 		// Tên Cụm
	UOCODE("17"), 			// Mã ĐVKD
	UONAME("18"), 			// Tên ĐVKD
	AGENTCODE("19"), 		// Mã số ĐLBH
	AGENTNAME("20"), 		// Tên ĐLBH
	AGENTTYPE("21"), 		// Loại ĐLBH
	ILAGENTCODE("22"), 		// Mã IL
	ILAGENTNAME("23"), 		// Tên IL
	ILAGENTTYPE("24"), 		// Loại ĐLBH (IL)
	SMAGENTCODE("25"), 		// Mã SM
	SMAGENTNAME("26"), 		// Tên SM
	SMAGENTTYPE("27"), 		// Loại ĐLBH (SM)
	REGIONDLVN("28"), 		// Tên Vùng DLVN
	RDNAME("29"), 			// Tên RD
	AREADLVN("30"), 		// Tên Khu Vực DLVN
	ADNAME("31"), 			// Tên AD
	CREATEDDATE("32"),		// Ngày cập nhật
	UPDATEDDATE("33"),		// Ngày chỉnh sửa
	CONTACTDATE("34"),		// Ngày liên hệ
	CONTACTDAYS("35"),		// Ngày liên hệ - Ngày Đến hạn
	CONTACTSTAGE("36"),		// Chương trình/Giai đoạn liên hệ
	CONTACTSTAGEDAYS("37"),	// Số ngày giai đoạn liên hệ
	CONTACTMETHOD("38"),	// Hình thức liên hệ
	CONTACTRESULT("39"),	// Kết quả liên hệ 
	NOTES("40"),			// Ghi chú
	CONTACTGROUP("41"),		// Nhóm liên hệ
	CONTACTCODE("42");		// Mã Liên hệ

    private String value;

    private EnumExportListContactHistory(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
