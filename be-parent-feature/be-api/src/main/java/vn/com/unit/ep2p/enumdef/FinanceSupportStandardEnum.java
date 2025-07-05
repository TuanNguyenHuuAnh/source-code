package vn.com.unit.ep2p.enumdef;

public enum FinanceSupportStandardEnum {
    BDOHCODE("0")
    , BDOHNAME("1")
    , OFFICE("2")
    , SALENAME("3")
    , BRANCHCODE("4")
    , BRANCHNAME("5")
    , UNITCODE("6")
    , UNITNAME("7")
    , AGENTCODE("8")
    , AGENTNAME("9")
    , AGENTTYPE("10")
    , ZIPCODE("11")
    , PROVINCE("12")
    , DISTRICT("13")
    , WARD("14")
    , ADDRESS("15")
    , MOBILENUMBER("16")
    , APPOINTANDREINSTATE("17")
    , SERVICINGMONTH("18")
    , SERVICINGPOLICY("19")
    , UL("20")
    , TL("21")
    , IL("22")
    , PL("23");
    private String value;
    
    private FinanceSupportStandardEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
