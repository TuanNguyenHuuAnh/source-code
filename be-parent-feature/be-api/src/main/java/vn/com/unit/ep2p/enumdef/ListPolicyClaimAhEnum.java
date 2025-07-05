package vn.com.unit.ep2p.enumdef;

public enum ListPolicyClaimAhEnum{
    BDOHNAME("0")
    , GANAME("1")
    , TOTALHANDLING("2")
    , TOTALWAITING("3")
    , TOTALAGREEING("4")
    , TOTALREJECTING("5")
    , TOTALERROR("6")
    , TOTAL("7");

	private String value;
	
	private ListPolicyClaimAhEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
