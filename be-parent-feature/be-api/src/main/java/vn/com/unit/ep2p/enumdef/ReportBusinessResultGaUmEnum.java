package vn.com.unit.ep2p.enumdef;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportBusinessResultGaUmEnum {
    AGENTALL("0")//TVTC
    
    , FYPDAY("1")
    , ACTIVEFCQUANTITY("2")
    , ACTIVEFCBYSCHEMAQUANTITY("3")
    , DYNAMICFCQUANTITY("4")
    , PROFILETOTALSUBMIT("5")//NOP VAO
    , FYPTOTALSUBMIT("6")
    , CONTRACTTOTALRELEASE("7")//PHAT HANH
    , FYPTOTALRELEASE("8")
    , CONTRACTTOTALRELEASEPURE("9")//PHAT HANH THUAN
    , FYPTOTALRELEASEPURE("10")
    , CONTRACTTOTALCANCEL("11")//HUY
    , FYPTOTALCANCEL("12")
    
    , FYPRESUME("13")//ĐC/TÁI TỤC FYP
    , RYPTOTAL("14")//RYP
    , K2("15")//%K2
    , K2PLUS("16")//%K2+
    
    , CONTRACTRELEASETOTALMONTH2M3("17")
    , FYPTOTALMONTH2M3("18")
    , CONTRACTRELEASETOTALMONTH3M2("19")
    , FYPTOTALMONTH3M2("20")
    , CONTRACTRELEASETOTALMONTH4M1("21")
    , FYPTOTALMONTH4M1("22"); 
    
    private String value;

    public String toString() {
        return value;
    }
}
