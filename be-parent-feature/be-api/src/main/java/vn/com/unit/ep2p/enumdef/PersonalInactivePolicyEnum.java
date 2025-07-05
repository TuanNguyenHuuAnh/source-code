package vn.com.unit.ep2p.enumdef;

public enum PersonalInactivePolicyEnum {
    NO("0")
    , insuranceContract("1")
    , customerName("2")
    , insuranceOfCustomerMain("3")
    , HOMEADDRESS("4")
    , CELLPHONE("5")
    , POLAGTSHRPCTSTR("6")
    , periodicFeePayment("7")
    , status("8")
    , effectiveDate ("9")
    , expirationDate("10")
    , estimatedRecurringFee("11")
    , recurringBasicFee("12")
    , hangingFee("13")
    // , zprxUnpaidPremAmt("14")
    ;
    private String value;

    private PersonalInactivePolicyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
