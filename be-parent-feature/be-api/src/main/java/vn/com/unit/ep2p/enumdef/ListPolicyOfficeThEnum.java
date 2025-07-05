package vn.com.unit.ep2p.enumdef;

public enum ListPolicyOfficeThEnum{
    BDAHNAME("0")
    , BDOHNAME("1")
    , TOTALCONTRACTEFFECT("2")
    , TOTALCONTRACTDUEFEE("3")
    , TOTALCONTRACTOVERDUEFEERYP("4")
    , TOTALPOLICYMATURED("5")
    , TOTALCONTRACTINVALID("6");

	private String value;
	
	private ListPolicyOfficeThEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
