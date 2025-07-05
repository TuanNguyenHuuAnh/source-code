package vn.com.unit.ep2p.enumdef;

public enum ListAgentCaoEnum{
    BDTHNAME("0")
    , BDAHNAME("1")
    , TOTAL("2")//tong so
    , TOTALHEADOFDEPARTMENT("3")//tong so cap truong phong
    , TOTALMANAGER("4")//tong nhom
    , TOTALTVTC("5")//tong tvtc
    , TOTALTVTCSA("6");//tong sa

	private String value;
	
	private ListAgentCaoEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
