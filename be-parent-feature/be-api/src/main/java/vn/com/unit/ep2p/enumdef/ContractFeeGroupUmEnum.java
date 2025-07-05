package vn.com.unit.ep2p.enumdef;

public enum ContractFeeGroupUmEnum {
    NO("0")
    , AGENTALL("1")
    , TOTALCONTRACT("2")
    , FEEMUSTRECEIVE("3")
    , FEEEXPECTED("4");
    ;
    private String value;
    
    private ContractFeeGroupUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
