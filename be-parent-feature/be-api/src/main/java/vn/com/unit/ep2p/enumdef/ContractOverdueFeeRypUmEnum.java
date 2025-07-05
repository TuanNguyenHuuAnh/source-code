package vn.com.unit.ep2p.enumdef;

public enum ContractOverdueFeeRypUmEnum {
    NO("0")
    , agentAll("1")
    , TERMINATEDDATE("2")
    , POLICYNO("3")
    , INSURANCEBUYER("4")
    , FULLADDRESS("5")
    , phoneNumberNr("6")
    , phoneNumberOffice("7")
    , PHONENUMBER("8")
    , recurringFeePayment("9")
    , paymentPeriodDate("10")
    , phiDuTinhDinhKy("11")
    , phiCoBanDinhKy("12")
    , FEEMUSTRECEIVE("13")
    , PAYMENTPERIODRECENT("14")
    , EXPIREDDATE("15")
    ;
    
    private String value;
    
    private ContractOverdueFeeRypUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
