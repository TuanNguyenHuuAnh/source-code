package vn.com.unit.cms.admin.all.enumdef.exports;

public enum FaqsCategoryExportEnum {
      NOID("0")
     ,MESSAGEERROR("1")
     ,FAQSCODE("2")
     ,CODE("3")
     ,FAQSCATETITLE("4")
     ,TITLE("5")
     ,KEYWORDSSEO("6")
     ,KEYWORDS("7")
     ,KEYWORDSDESC("8")
     ,CONTENT("9")
     ,ENABLED("10")
     ,POSTEDDATE("11")
     ,EXPIRATIONDATE("12")
;
    private String value;

    private FaqsCategoryExportEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
