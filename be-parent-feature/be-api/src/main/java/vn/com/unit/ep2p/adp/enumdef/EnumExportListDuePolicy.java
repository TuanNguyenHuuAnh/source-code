package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListDuePolicy {
	NO("0"), // STT
    PARTNER("1"), // Đối Tác
    POLICYNO("2"), // Số HĐBH
    POLICYOWNER("3"), // BMBH
    POLSTATUS("4"), // Tình trạng HĐ
    POHOMEADDRESS("5"), // Địa chỉ BMBH
    POHOMEPHONE("6"), // Điện thoại nhà
    POCELLPHONE("7"), // Điện thoại di động
    LIFEINSURED("8"), // Tên NĐBH
    POLBASEFACEAMT("9"), // Mệnh giá SP chính
    POLISSUEEFF("10"), // Ngày hiệu lực
    POLBILLMODECD("11"), // Định kỳ đóng phí
    APE("12"), // Phí năm
    POLMPREMAMT("13"), // Phí bảo hiểm định kỳ
    TP("14"), // Phí dự tính đóng định kỳ
    POLSUSPENSEAMT("15"), // Phí treo
    SHRTAMT("16"),	// Giá trị tài khoản hợp đồng sau khi khấu trừ các khoản chi phí bảo hiểm rủi ro và Chi phí quản lý hợp đồng
    DUETODATE("17"), // Ngày đến hạn đóng phí
    EXPECTEDPREMIUM("18"), // Phí tái tục cần phải thu    
    PREMIUMSTATUS("19"), // Tình trạng phí
    POLLSTPAIDTODATE("20"), // Kỳ phí đóng đủ gần nhất
    REFERRALCODE("21"), // Mã NVGTCT
    REFERRALBANKCODE("22"), // Mã NH NVGTCT
    REFERRALNAME("23"), // Tên NVGTCT
    PACKAGENAME("24"), // Sản phẩm
    SALEMODE("25"), // Kênh
    REGION("26"), // Khu Vực/ Vùng
    BRANCHCODE("27"), // Mã Cụm
    BRANCHNAME("28"), // Tên Cụm
    UOCODE("29"), // Mã ĐVKD
    UONAME("30"), // Tên ĐVKD
    AGENTCODE("31"), // Mã số ĐLBH
    AGENTNAME("32"), // Tên ĐLBH
    AGENTTYPE("33"), // Loại ĐLBH
    ILAGENTCODE("34"), // Mã IL
    ILAGENTNAME("35"), // Tên IL
    ILAGENTTYPE("36"), // Loại ĐLBH (IL)
    SMAGENTCODE("37"), // Mã SM
    SMAGENTNAME("38"), // Tên SM
    SMAGENTTYPE("39"), // Loại ĐLBH (SM)
    BRANCHDLVN("40"), // Tên Cụm DLVN
    ZDNAME("41"), // Tên ZD
    REGIONDLVN("42"), // Tên Vùng DLVN
    RDNAME("43"), // Tên RD
    AREADLVN("44"), // Tên Khu Vực DLVN
    ADNAME("45"); // Tên AD

    private String value;

    private EnumExportListDuePolicy(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
