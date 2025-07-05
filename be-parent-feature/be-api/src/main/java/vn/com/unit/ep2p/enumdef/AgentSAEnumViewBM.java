package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentSAEnumViewBM {
    NO("0")
    , MANAGER("1")
    , TVTC("2")
   // , TVTCPOSITION("3")
    , IDCARD("3")
    , STARTDATE("4")
    , PHONENUM("5")
    , ADDRESS("6")
    , CONTRACTNUMBER("7")
    , MONTHINACTIVENUMBER("8")
   ;
	
	private String value;

	public String toString() {
		return value;
	}
}
