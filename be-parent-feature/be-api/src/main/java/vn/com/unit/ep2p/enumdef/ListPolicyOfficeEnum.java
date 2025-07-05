package vn.com.unit.ep2p.enumdef;

public enum ListPolicyOfficeEnum{
    GANAME("0")
    , BRANCHNAME("1")
    , TOTALCONTRACTEFFECT("2")
    , TOTALCONTRACTDUEFEE("3")
    , TOTALCONTRACTOVERDUEFEERYP("4")
    , TOTALCONTRACTEXPIRED("5")
    , TOTALCONTRACTEXPIRED30DAYS("6")
    , TOTALCONTRACTINVALID("7")
    , TOTALCONTRACTORPHAN("8");

	private String value;
	
	private ListPolicyOfficeEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
