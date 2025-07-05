package vn.com.unit.ep2p.enumdef;

public enum PersonalExpiredPolicyEnum {
    no("0")
    , policyKey("1")
    , poName("2")
    , insuranceOfCustomerMain("3")
    , mainProduct("4")
    , polIssueEff("5")
    , polMaturedDt("6")
    , polBaseFaceAmt("7")
    , tmpAmountOfMoney("8")
    ;
    private String value;

    private PersonalExpiredPolicyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
