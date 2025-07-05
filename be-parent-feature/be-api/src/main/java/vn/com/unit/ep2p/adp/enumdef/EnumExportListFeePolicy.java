package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListFeePolicy {
	NO("0"), // STT
    PARTNER("1"), // Đối tác
    DOCNO("2"), // Số HSYCBH
    POLICYNO("3"), // SỐ HD
    POLICYOWNER("4"), // Họ tên BMBH
    POLSTATUS("5"), // Trạng thái HĐ    
    DOCRECEIVEDDATE("6"), // Ngày nhận HS
    ISSUEDATE("7"), // Ngày phát hành
    TP("8"), // TP
    EP("9"), // EP
    IPCS("10"), // Tổng phí
    IP("11"), // Phí phát sinh IP/FYP/RYP (100% TP + 10% EP)
    PREMIUMYEAR("12"), // Năm phí
    POLICYYEAR("13"), // Năm HĐ
    PREMIUMAPPLYDATE("14"), // Ngày phát sinh phí
    REFERRALCODE("15"), // Mã NVGTCT
    REFERRALBANKCODE("16"), // Mã NH NVGTCT
    REFERRALNAME("17"), // Tên NVGTCT
    PACKAGENAME("18"), // Sản phẩm
    SALEMODE("19"), // Kênh
    REGION("20"), // Khu Vực/ Vùng
    BRANCHCODE("21"), // Mã Cụm
    BRANCHNAME("22"), // Tên Cụm
    UOCODE("23"), // Mã ĐVKD
    UONAME("24"), // Tên ĐVKD
    AGENTCODE("25"), // Mã số ĐLBH
    AGENTNAME("26"), // Tên ĐLBH
    AGENTTYPE("27"), // Loại ĐLBH
    ILAGENTCODE("28"), // Mã IL
    ILAGENTNAME("29"), // Tên IL
    ILAGENTTYPE("30"), // Loại ĐLBH (IL)
    SMAGENTCODE("31"), // Mã SM
    SMAGENTNAME("32"), // Tên SM
    SMAGENTTYPE("33"), // Loại ĐLBH (SM)
    BRANCHDLVN("34"), // Tên Cụm DLVN
    ZDNAME("35"), // Tên ZD
    REGIONDLVN("36"), // Tên Vùng DLVN
    RDNAME("37"), // Tên RD
    AREADLVN("38"), // Tên Khu Vực DLVN
    ADNAME("39"); // Tên AD



    private String value;

    private EnumExportListFeePolicy(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
