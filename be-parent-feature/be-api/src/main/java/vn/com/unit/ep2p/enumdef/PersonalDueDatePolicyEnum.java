package vn.com.unit.ep2p.enumdef;

public enum PersonalDueDatePolicyEnum {
	no("0")
    , policyNo("1")
    , insuranceBuyer("2")
    , bdbh("3")
    , address("4")
    , phoneNumber("5")
    , ngayHieuLuc("6")
    , recurringFeePayment("7")
    , feeExpected("8")
    , paymentPeriodDate("9")
    , tinhTrangThuPhi("10")
    , phiDuTinhDinhKy("11")
    , phiCoBanDinhKy("12")
    , phiDongTruoc("13")
    , feeMustReceive("14")
    , noApl("15")
    , EXPIREDDATE("16")

    
    ;
    private String value;

    private PersonalDueDatePolicyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
