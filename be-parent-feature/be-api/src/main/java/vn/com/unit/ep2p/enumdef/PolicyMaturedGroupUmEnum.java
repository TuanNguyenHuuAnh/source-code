package vn.com.unit.ep2p.enumdef;

public enum PolicyMaturedGroupUmEnum {
    NO("0")
    , AGENTALL("1")
    , TOTALPOLICYMATURED("2")
    , TOTALMATUREDAMOUNT("3")
    , NEWPROPOSALNUM("4")
    ;
	
    private String value;
    
    private PolicyMaturedGroupUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
