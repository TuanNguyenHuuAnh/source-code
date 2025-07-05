package vn.com.unit.ep2p.enumdef;

public enum ContractFeeGroupBmEnum {
    NO("0")
    , PARENTALL("1")
    , AGENTALL("2")
    , TOTALCONTRACT("3")
    , FEEMUSTRECEIVE("4")
    , FEEEXPECTED("5");
    
    private String value;
    
    private ContractFeeGroupBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
