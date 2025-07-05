package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentSAEnumViewTH {
    NO("0")
    , AREANAME("1")
    , BDAH("2")
    // , REGIONNAME("3")
    // , BDRH("4")
    , OFFICENAME("3")
    , BDOH("4")
    , GAD("5")
    , HEADOFDEPARTMENT("6")
    , MANAGER("7")
    , TVTC("8")
    //, TVTCPOSITION("11")
    , STARTDATE("9")
    //, PHONENUM("12")
    , ADDRESS("10")
    , CONTRACTNUMBER("11")
    , MONTHINACTIVENUMBER("12")
   ;
	
	private String value;

	public String toString() {
		return value;
	}
}
