package vn.com.unit.ep2p.enumdef;

public enum OfficePolicyExpiredBmEnum {
    NO("0")
    , MANAGERALL("1")
    , AGENTALL("2")
    , TOTALCONTRACT("3")//dao han
    , TOTALCONTRACTSWILLEXPIRED("4")//se dao han
    , TOTALAMOUNTPAID("5")//chi tra
    , TOTALAMOUNTPAIDESTIMATE("6");//uoc tinh chi tra
    private String value;
    
    private OfficePolicyExpiredBmEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
