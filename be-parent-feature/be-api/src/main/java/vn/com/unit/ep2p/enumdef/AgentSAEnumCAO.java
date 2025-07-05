package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentSAEnumCAO {
    NO("0")
    , TERRITORYNAME("1")
    , BDTH("2")
    , AREANAME("3")
    , BDAH("4")
    // , REGIONNAME("5")
    // , BDRH("6")
    , OFFICENAME("5")
    , BDOH("6")
    , GAD("7")
    , HEADOFDEPARTMENT("8")
    , MANAGER("9")
    , TVTC("10")
    //, TVTCPOSITION("13")
    , STARTDATE("11")
    //, PHONENUM("14")
    , ADDRESS("12")
    , CONTRACTNUMBER("13")
    , MONTHINACTIVENUMBER("14")
   ;
	
	private String value;

	public String toString() {
		return value;
	}
}
