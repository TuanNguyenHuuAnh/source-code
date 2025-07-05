package vn.com.unit.ep2p.enumdef;

public enum PersonalBusinessPolicyEnum {
    no("0")
    , policyNo("1")
    , insuranceBuyer("2")
    , requestAdjustment("3")
    , status("4")
    , waitingAddInformation("5")
    , requestDate("6")
    , startDate("7")
    , addExpirationDate("8")
    , SCANLOC("9")
    ;
    private String value;

    private PersonalBusinessPolicyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
