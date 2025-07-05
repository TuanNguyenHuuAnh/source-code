package vn.com.unit.cms.admin.all.enumdef.exports;

public enum DocumentResultExportEnum {
      NOID("0")
     ,MESSAGEERROR("1")
     ,DOCUMENTCODE("2")
     ,CODE("3")
     ,DOCCATETITLE("4")
     ,TITLE("5")
     ,KEYWORDSSEO("6")
     ,KEYWORDS("7")
     ,KEYWORDSDESC("8")
     ,ENABLED("9")
     ,PHYSICALFILENAME("10")
     ,POSTEDDATE("11")
     ,EXPIRATIONDATE("12")
;
    private String value;

    private DocumentResultExportEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
