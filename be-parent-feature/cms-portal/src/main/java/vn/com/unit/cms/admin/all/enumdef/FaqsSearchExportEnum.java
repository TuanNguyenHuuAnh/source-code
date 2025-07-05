package vn.com.unit.cms.admin.all.enumdef;

public enum FaqsSearchExportEnum {

    //----------------------------------------------------------------------
    //  DATA FIELDS 
    //----------------------------------------------------------------------    

	STT("0"),
	PRODUCTCATEGORYNAME("1"),
	PRODUCTCATEGORYSUBNAME("2"),
	PRODUCTNAME("3"),
	CODE("4"),
	TITLE("5"),
	STATUSNAME("6"),
	ENABLEFAQS("7"),
	CREATEBY("8"),
	CREATEDATE("9");
	    
    private String value;
    
    private FaqsSearchExportEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }
}
