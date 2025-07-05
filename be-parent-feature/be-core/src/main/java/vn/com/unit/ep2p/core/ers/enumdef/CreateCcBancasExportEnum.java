package vn.com.unit.ep2p.core.ers.enumdef;


public enum CreateCcBancasExportEnum {
    no("0"),
    
    bpCode("1"), // bp => null
    
    applyForPosition("2"),// chức vụ dự kiến 
    
    classCode("3"), // lớp học
    candidateName("4"),
    genderName("5"),
    maritalStatus("6"),
    dob("7"),
    nationality("8"),
    otherIdNo("9"),
    idType("10"),
    idNo("11"),
    
//    createdDate("12"), // ngày nhập => created date
    createdDateEmptyString("12"),
    
    idDateOfIssue("13"),
    
    idExpiredDate("14"), // ngày hết hạn => ngày cấp + 15 năm
    
    idPlaceOfIssueName("15"),
    
    taxCode("16"),
    mobile("17"),
    
    // Bug #44296
    emailCompany("18"), 

    email("19"), // email công ty của bancas
    
    permanentAddress("20"), // số nhà <= Số nhà đường của địa chỉ thường trú
    
    permanentNest("21"), // phố/thôn/xóm <= Tổ/Xóm/Ấp/Khu phố địa chỉ thường trú
    
    permanentWardName("22"), // phường <= Tên Phường/Xã thường trú
    
    permanentDistrictName("23"), // quận/huyện <= Tên Quận/Huyện thường trú
    
    permanentProvinceName("24"), // tỉnh/thành phố <= Tên Tỉnh/Thành phố thường trú

    currentAddress("25"), // Số nhà đường của địa chỉ hiện tại
    currentNest("26"),  // Tổ/Xóm/Ấp/Khu phố của địa chỉ hiện tại
    currentWardName("27"), // Tên Phường/Xã hiện tại
    currentDistrictName("28"), //   Tên Quận/Huyện hiện tại
    currentProvinceName("29"), //   Tên Tỉnh/Thành phố hiện tại

    educational("30"), //Trình độ học vấn
    carrerCurrent("31"), // Nghề nghiệp hiện tại
    
    bankBranch("32"), // Mã chi nhánh ngân hàng
    accountName("33"), // Tên tài khoản - default là tên ứng viên
    accountNumber("34"), // Số tài khoản của ứng viên
    
    recruiterBp("35"),// số BP người giới thiệu

    recruiterIdNo("36"),
    
    managerBp("37"),// số BP người quản lý trực tiếp
    
    managerIdNo("38"),
    
    form("39"),// hồ sơ thiếu
    
    note("40"),// ghi chú
    
    bpType("41"),// BP type
    
    registrationType("42"),// loại đăng ký
    
    policy("43"),// Lựa chọn chính sách
    
//    regionName("44"),// Mã đơn vị - chi nhánh - vùng - miền
    regionNameEmptyString("44"),
    
    //messageError("45"),// message error
    ;
    
    private String value;

    private CreateCcBancasExportEnum(String value) {
        this.value = value;
    }

    @Override // fix NoClassDefFoundError
    public String toString() {
        return value;
    }
}
