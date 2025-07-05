package vn.com.unit.ep2p.enumdef;

public enum OfficePolicyOrphanEnum {
    NO("0")
    , POLICYNO("1")
    , INSURANCEBUYER("2")
    , ADDRESS("3")
    , PHONENUMBER("4")
    , PAYMENTPERIOD("5")
    , SERVICINGAGENTNAME("6")//TEN TVTC PHUC VU
    , TERMINATEDDATE("7")
    , NEWSERVICINGAGENTCODE("8")//MA TVTC PHUC VU MOI
    , NEWSERVICINGAGENTNAME("9");//TEN TVTC PHUC VU MOI
    
    private String value;
    
    private OfficePolicyOrphanEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
