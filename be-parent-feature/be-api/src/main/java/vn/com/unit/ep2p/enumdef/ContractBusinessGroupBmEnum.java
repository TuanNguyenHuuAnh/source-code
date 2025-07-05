package vn.com.unit.ep2p.enumdef;

public enum ContractBusinessGroupBmEnum {
    NO("0")
    , parentAll("1")
    , agentAll("2")
    , POLICYNO("3")
    , INSURANCEBUYER("4")
    , REQUESTADJUSTMENT("5")
    , STATUS("6")
    , WAITINGADDINFORMATION("7")
    , REQUESTDATE("8")
    , STARTDATE("9")
    , ADDEXPIRATIONDATE("10")
    ;
    private String value;
    
    private ContractBusinessGroupBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
