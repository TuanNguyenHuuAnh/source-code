package vn.com.unit.ep2p.enumdef;

public enum ListPolicyOfficeAhEnum{
    BDOHNAME("0")
    , GANAME("1")
    , TOTALCONTRACTEFFECT("2")
    , TOTALCONTRACTDUEFEE("3")
    , TOTALCONTRACTOVERDUEFEERYP("4")
    , TOTALPOLICYMATURED("5")
    , TOTALCONTRACTINVALID("6");
	
	private String value;
	
	private ListPolicyOfficeAhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
