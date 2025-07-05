package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum EmulateChallengeGroupEnum {
    NO("0")
    , MEMONO("1")//SO MEMO
    , CONTESTNAME("2")
    , AGENTCODE("3")//TVTC
    , AGENTNAME("4")//HO TEN
    , OFFICENAME("5")
    , REGIONNAME("6")//vung
    , AREANAME("7")//khu
    , TERRITORYNAME("8")//mien
    , RESULT("9")//GIAI THUONG
    , ADVANCE("10")//TAM UNG
    , BONUS("11")//TRA BO SUNG
    , CLAWBACK("12")//THU HOI
    , NOTE("13");

		private String value;

		public String toString() {
			return value;
		}
}
