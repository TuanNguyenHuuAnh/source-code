package vn.com.unit.ep2p.enumdef;

public enum OfficePolicyExpiredUmEnum {
    NO("0")
    , AGENTALL("1")
    , TOTALCONTRACT("2")
    , TOTALCONTRACTSWILLEXPIRED("3")
    , TOTALAMOUNTPAID("4")
    , TOTALAMOUNTPAIDESTIMATE("5");
    private String value;
    
    private OfficePolicyExpiredUmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
