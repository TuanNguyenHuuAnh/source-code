package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EmulateResultPersonalEnum {
    NO("0")
    , MEMONO("1")//SO MEMO
    , CONTESTNAME("2")
    , AGENTCODE("3")//TVTC
    , AGENTNAME("4")//HO TEN
    , OFFICENAME("5")
    , REGIONNAME("6")//vung
    , AREANAME("7")//khu
    , TERRITORYNAME("8")//mien
    , POLICYNO("9")
    , RESULT("10")//GIAI THUONG
    , ADVANCE("11")//TAM UNG
    , BONUS("12")//TRA BO SUNG
    , CLAWBACK("13")//THU HOI
    , NOTE("14");
    
    private String value;

    public String toString() {
        return value;
    }
}
