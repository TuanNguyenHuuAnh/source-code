package vn.com.unit.ep2p.enumdef;

public enum ContractEffectGroupUmEnum {
    NO("0")
    , AGENTALL("1")
    , TOTALCONTRACT("2");
    private String value;
    
    private ContractEffectGroupUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
