package vn.com.unit.cms.admin.all.enumdef.imports;

public enum FaqsCategoryImportEnum {
     NOID("0")
    ,FAQSCODE("1")
    ,CODE("2")
    ,TITLE("3")
    ,KEYWORDSDESC("4")
    ,CONTENT("5")
    ,ENABLED("6")
    ,POSTEDDATE("7")
    ,EXPIRATIONDATE("8")
    ,messageerror("9")
;
    private String value;

    private FaqsCategoryImportEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
