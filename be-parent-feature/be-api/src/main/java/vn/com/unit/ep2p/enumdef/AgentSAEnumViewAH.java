package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentSAEnumViewAH {
    NO("0")
    // , REGIONNAME("1")
    // , BDRH("2")
    , OFFICENAME("1")
    , BDOH("2")
    , GAD("3")
    , HEADOFDEPARTMENT("4")
    , MANAGER("5")
    , TVTC("6")
    //, TVTCPOSITION("9")
    , STARTDATE("7")
    //, PHONENUM("10")
    , ADDRESS("8")
    , CONTRACTNUMBER("9")
    , MONTHINACTIVENUMBER("10")
   ;
	
	private String value;

	public String toString() {
		return value;
	}
}
