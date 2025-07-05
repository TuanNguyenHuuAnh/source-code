package vn.com.unit.ep2p.admin.sla.enumdef;

public enum CalendarTypeSearchEnum {
	
	CODE("calendar.type.code"),
    NAME("calendar.type.name"),
    DESCRIPTION("calendar.type.description");
    
    private String value;
    
    private CalendarTypeSearchEnum(String value){
        this.value = value;
    }
    
    public String toString (){
        return value;
    }

}
