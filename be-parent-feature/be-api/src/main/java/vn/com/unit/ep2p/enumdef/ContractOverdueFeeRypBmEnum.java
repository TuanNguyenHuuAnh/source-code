package vn.com.unit.ep2p.enumdef;

public enum ContractOverdueFeeRypBmEnum {
    NO("0")
    , managerAll("1")
    , agentAll("2")
    , TERMINATEDDATE("3")
    , POLICYNO("4")
    , INSURANCEBUYER("5")
    , FULLADDRESS("6")
    , phoneNumberNr("7")
    , phoneNumberOffice("8")
    , PHONENUMBER("9")
    , recurringFeePayment("10")
    , paymentPeriodDate("11")
    , phiDuTinhDinhKy("12")
    , phiCoBanDinhKy("13")
    , FEEMUSTRECEIVE("14")
    , PAYMENTPERIODRECENT("15")
    , EXPIREDDATE("16")

    ;
    
    private String value;
    
    private ContractOverdueFeeRypBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
