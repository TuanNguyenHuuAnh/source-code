package vn.com.unit.cms.admin.all.enumdef.imports;

public enum EmulateImportEnum {
    NO("0")
    , CODE("1")
    , MEMONO("2")
    , CONTESTNAME("3")
    , KEYWORDSSEO("4")
    , KEYWORDS("5")
    , KEYWORDSDESC("6")
    , DESCRIPTION("7")
    , CONTESTTYPE("8")
    , CONTESTPHYSICALIMT("9")
    , CONTESTPHYSICALFILE("10")
    , ISHOT("11")
    , ISCHALLENGE("12")
    , ENABLED("13")
    , ISODS("14")
    , STARTDATE("15")
    , ENDDATE("16")
    , EFFECTIVEDATE("17")
    , EXPIREDDATE("18")
    , APPLICABLEOBJECT("19") 
    , AGENTNAME("20")
    , AREA("22")
    , TERRITORY("21")
    , REGION("23")
    , OFFICE("24")
    , POSITION("25")
    , REPORTINGTONAME("26")
    , NOTE("27")
    , MESSAGEERROR("28");

    private String value;

    private EmulateImportEnum(String value){
        this.value = value;
    }

    public String toString (){
        return value;
    }
    }
