package vn.com.unit.ep2p.enumdef;

public enum PersonalActivePolicyEnum {
    NO("0")
    , insuranceContract("1")
    , customerName("2")
    , insuranceOfCustomerMain("3")
    , STATUS("4")
    , effectiveDate("5")
    , DATEDUE("6")
    , polAgtShrPctStr("7")
    , periodicFeePayment("8")
    , estimatedRecurringFee("9")
    , recurringBasicFee("10")
    , ZPRXUNPAIDPREMAMT("11")
    , APLAMT("12")
;
    private String value;

    private PersonalActivePolicyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
