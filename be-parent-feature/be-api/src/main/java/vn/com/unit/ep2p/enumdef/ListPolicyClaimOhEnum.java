package vn.com.unit.ep2p.enumdef;

public enum ListPolicyClaimOhEnum{
    GANAME("0")
    , BRANCHNAME("1")
    , TOTALHANDLING("2")
    , TOTALWAITING("3")
    , TOTALAGREEING("4")
    , TOTALREJECTING("5")
    , TOTALERROR("6")
    , TOTAL("7");

	private String value;
	
	private ListPolicyClaimOhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
