package vn.com.unit.ep2p.enumdef;

/**
 * Created by quangnd on 7/27/2018.
 */
public enum ItemManagementSearchEnum {

    FUNCTION_CODE("item.management.function.code"),

    FUNCTION_NAME("item.management.function.name"),

    DESCRIPTION("description");

    private String value;

    private ItemManagementSearchEnum(String value){
        this.value = value;
    }

    public String toString (){
        return value;
    }
}
