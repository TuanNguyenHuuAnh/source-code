package vn.com.unit.ep2p.enumdef;

public enum PolicyMaturedGroupBmEnum {
    NO("0")
    , PARENTALL("1")
    , AGENTALL("2")
    , TOTALPOLICYMATURED("3")
    , TOTALMATUREDAMOUNT("4")
    , NEWPROPOSALNUM("5")
    ;
	
    private String value;
    
    private PolicyMaturedGroupBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
