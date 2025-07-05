package vn.com.unit.ep2p.enumdef;

public enum ContractFeeGroupFcEnum {
    no("0")
    , policyNo("1")
    , insuranceBuyer("2")
    , recurringBasicFee("3")
    , feeExpected("4")
    ;
    private String value;

    private ContractFeeGroupFcEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
