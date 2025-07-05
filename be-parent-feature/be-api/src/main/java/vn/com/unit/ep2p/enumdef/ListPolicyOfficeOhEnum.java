package vn.com.unit.ep2p.enumdef;

public enum ListPolicyOfficeOhEnum{
    GANAME("0")
    , BRANCHNAME("1")
    , TOTALCONTRACTEFFECT("2")
    , TOTALCONTRACTDUEFEE("3")
    , TOTALCONTRACTOVERDUEFEERYP("4")
    , TOTALPOLICYMATURED("5")
    , TOTALCONTRACTINVALID("6");

	private String value;
	
	private ListPolicyOfficeOhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
