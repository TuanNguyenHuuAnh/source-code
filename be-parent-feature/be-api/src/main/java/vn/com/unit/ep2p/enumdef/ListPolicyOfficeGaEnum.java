package vn.com.unit.ep2p.enumdef;

public enum ListPolicyOfficeGaEnum{
    BRANCHNAME("0")
    , UNITNAME("1")
    , TOTALCONTRACTEFFECT("2")
    , TOTALCONTRACTDUEFEE("3")
    , TOTALCONTRACTOVERDUEFEERYP("4")
    , TOTALCONTRACTEXPIRED("5")
    , TOTALCONTRACTEXPIRED30DAYS("6")
    , TOTALCONTRACTINVALID("7")
    , TOTALCONTRACTORPHAN("8");

	private String value;
	
	private ListPolicyOfficeGaEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
