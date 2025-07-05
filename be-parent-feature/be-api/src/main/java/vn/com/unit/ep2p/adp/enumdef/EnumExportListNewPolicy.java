package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListNewPolicy {
	NO("0"), // STT
    PARTNER("1"), // Đối Tác
    POLICYNO("2"), // SỐ HD
    POLICYOWNER("3"), // HỌ TÊN BMBH
    POLSTATUS("4"), // Trạng thái HĐ
    DOCRECEIVEDDATE("5"), // NGÀY NHẬN HS
    ISSUEDATE("6"), // Ngày phát hành
    PREMIUMDUEDATE("7"), // Thời hạn hợp đồng
    BILLINGMETHOD("8"), // Định kỳ đóng phí
    TP("9"), // PHÍ CƠ BẢN (TP)
    EP("10"), // PHÍ ĐÓNG THÊM (EP)
    IPCS("11"), // Tổng phí
    IP("12"), // IP
    POLICYYEAR("13"), // Năm HĐ
    PREMIUMYEAR("14"), // Năm phí
    PREMIUMAPPLYDATE("15"), // Ngày phát sinh phí
    REFERRALCODE("16"), // Mã NVGTCT
    REFERRALBANKCODE("17"), // Mã NH NVGTCT
    REFERRALNAME("18"), // Tên NVGTCT
    PACKAGENAME("19"), // Sản phẩm
    SALEMODE("20"), // Kênh
    REGION("21"), // Khu Vực/ Vùng
    BRANCHCODE("22"), // Mã Cụm
    BRANCHNAME("23"), // Tên cụm
    UOCODE("24"), // Mã ĐVKD
    UONAME("25"), // Tên ĐVKD
    AGENTCODE("26"), // Mã số ĐLBH
    AGENTNAME("27"), // Tên ĐLBH
    AGENTTYPE("28"), // Loại ĐLBH
    ILAGENTCODE("29"), // Mã IL
    ILAGENTNAME("30"), // Tên IL
    ILAGENTTYPE("31"), // Loại ĐLBH (IL)
    SMAGENTCODE("32"), // Mã SM
    SMAGENTNAME("33"), // Tên SM
    SMAGENTTYPE("34"), // Loại ĐLBH (SM)
    BRANCHDLVN("35"), // Tên Cụm DLVN
    ZDNAME("36"), // Tên ZD
    REGIONDLVN("37"), // Tên Vùng DLVN
    RDNAME("38"), // Tên RD
    AREADLVN("39"), // Tên Khu Vực DLVN
    ADNAME("40"); // Tên AD


    private String value;

    private EnumExportListNewPolicy(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
