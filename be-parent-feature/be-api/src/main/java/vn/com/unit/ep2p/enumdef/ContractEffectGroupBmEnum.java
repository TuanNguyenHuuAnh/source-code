package vn.com.unit.ep2p.enumdef;

public enum ContractEffectGroupBmEnum {
    NO("0")
    , PARENTALL("1")
    , AGENTALL("2")
    , TOTALCONTRACT("3");
    ;
    private String value;
    
    private ContractEffectGroupBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
