package vn.com.unit.ep2p.enumdef;

public enum ContractBusinessGroupUmEnum {
	NO("0")
    , agentAll("1")
    , POLICYNO("2")
    , INSURANCEBUYER("3")
    , REQUESTADJUSTMENT("4")
    , STATUS("5")
    , WAITINGADDINFORMATION("6")
    , REQUESTDATE("7")
    , STARTDATE("8")
    , ADDEXPIRATIONDATE("9")
    ;
    private String value;
    
    private ContractBusinessGroupUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
