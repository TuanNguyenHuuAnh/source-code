package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentSAEnumViewRHOH {
    NO("0")
    , OFFICENAME("1")
    , BDOH("2")
    , GAD("3")
    , HEADOFDEPARTMENT("4")
    , MANAGER("5")
    , TVTC("6")
    //, TVTCPOSITION("7")
    , STARTDATE("7")
    //, PHONENUM("8")
    , ADDRESS("8")
    , CONTRACTNUMBER("9")
    , MONTHINACTIVENUMBER("10")
   ;
	
	private String value;

	public String toString() {
		return value;
	}
}
