package vn.com.unit.cms.admin.all.enumdef.imports;

public enum DocumentImportEnum {
     NOID("0")
    ,DOCUMENTCODE("1")
    ,CODE("2")
    ,TITLE("3")
    ,KEYWORDSDESC("4")
    ,ENABLED("5")
    ,PHYSICALFILENAME("6")
    ,POSTEDDATE("7")
    ,EXPIRATIONDATE("8")
    ,messageerror("9")
;
    private String value;

    private DocumentImportEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
