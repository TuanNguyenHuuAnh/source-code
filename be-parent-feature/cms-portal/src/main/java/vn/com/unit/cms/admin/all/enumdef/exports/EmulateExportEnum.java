package vn.com.unit.cms.admin.all.enumdef.exports;

public enum EmulateExportEnum {
    NO("0")
    , MESSAGEERROR("1")
    , CODE("2")
    , MEMONO("3")
    , CONTESTNAME("4")
    , KEYWORDSSEO("5")
    , KEYWORDS("6")
    , KEYWORDSDESC("7")
    , DESCRIPTION("8")
    , CONTESTTYPE("9")
    , CONTESTPHYSICALIMT("10")
    , CONTESTPHYSICALFILE("11")
    , ISHOT("12")
    , ISCHALLENGE("13")
    , ENABLED("14")
    , ISODS("15")
    , STARTDATE("16")
    , ENDDATE("17")
    , EFFECTIVEDATE("18")
    , EXPIREDDATE("19")
    , APPLICABLEOBJECT("20")
    , AGENTNAME("21")
    , AREA("22")
    , TERRITORY("23")
    , REGION("24")
    , OFFICE("25")
    , POSITION("26")
    , REPORTINGTONAME("27")
    , NOTE("28");


    private String value;

    private EmulateExportEnum(String value){
        this.value = value;
    }

    public String toString (){
        return value;
    }
    }
