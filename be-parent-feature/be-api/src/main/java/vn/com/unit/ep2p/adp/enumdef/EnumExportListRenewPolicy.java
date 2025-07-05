package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportListRenewPolicy {
    NO("0"), // STT
    PARTNER("1"), // Đối Tác
    POLICYNO("2"), // SỐ HD
    POLICYOWNER("3"), // HỌ TÊN BMBH
    POLSTATUS("4"), // TRẠNG THÁI HĐ
    DOCRECEIVEDDATE("5"), // NGÀY NHẬN HS
    ISSUEDATE("6"), // Ngày phát hành
    PREMIUMDUEDATE("7"), // THỜI HẠN HỢP ĐỒNG
    BILLINGMETHOD("8"), // Định kỳ đóng phí
    TP("9"), // PHÍ CƠ BẢN (TP)
    EP("10"), // PHÍ ĐÓNG THÊM (EP)
    IPCS("11"), // Tổng phí
    RFYP("12"), // RFYP
    RYP("13"), // RYP
    POLICYYEAR("14"), // Năm HĐ
    PREMIUMYEAR("15"), // Năm phí
    PREMIUMAPPLYDATE("16"), // Ngày phát sinh phí
    REFERRALCODE("17"), // Mã NVGTCT
    REFERRALBANKCODE("18"), // Mã NH NVGTCT
    REFERRALNAME("19"), // Tên NVGTCT
    PACKAGENAME("20"), // Sản phẩm
    SALEMODE("21"), // Kênh
    REGION("22"), // Khu Vực/ Vùng
    BRANCHCODE("23"), // Mã Cụm
    BRANCHNAME("24"), // Tên Cụm
    UOCODE("25"), // Mã ĐVKD
    UONAME("26"), // Tên ĐVKD
    AGENTCODE("27"), // Mã số ĐLBH
    AGENTNAME("28"), // Tên ĐLBH
    AGENTTYPE("29"), // Loại ĐLBH
    ILAGENTCODE("30"), // Mã IL
    ILAGENTNAME("31"), // Tên IL
    ILAGENTTYPE("32"), // Loại ĐLBH (IL)
    SMAGENTCODE("33"), // Mã SM
    SMAGENTNAME("34"), // Tên SM
    SMAGENTTYPE("35"), // Loại ĐLBH (SM)
    BRANCHDLVN("36"), // Tên Cụm DLVN
    ZDNAME("37"), // Tên ZD
    REGIONDLVN("38"), // Tên Vùng DLVN
    RDNAME("39"), // Tên RD
    AREADLVN("40"), // TÊN KHU VỰC DLVN
    ADNAME("41"); // Tên AD

    private String value;

    private EnumExportListRenewPolicy(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
