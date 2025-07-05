/*******************************************************************************
 * Class        ：ButtonType
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：tantm
 * Change log   ：2020/11/18：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * ButtonType
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public enum ButtonType {
    SAVE("SAVE"),
    DELETE("DELETE"),
    
    SUBMIT("SUBMIT"),
	APPROVE("APPROVE"),
	REJECT("REJECT"),
	RETURN("RETURN"),
	ASSIGN("ASSIGN"),
	PENDING("PENDING"),
	COMPLETE("COMPLETE"),
	RE_ACTIVE("RE_ACTIVE"),
    DENIED_CONTRACT("DENIED_CONTRACT"),
    DENIED_CUSTOMER("DENIED_CUSTOMER"),
    DE_ACTIVE("DE_ACTIVE"),
    APPROVE_AUTO("APPROVE_AUTO"),
    NOT_APPROVE("NOT_APPROVE"),
    
    SURVEY_AGREE("SURVEY_AGREE"),
    SURVEY_DENIED("SURVEY_DENIED"),
    SURVEY_NO_IDEAS("SURVEY_NO_IDEAS"),
	
	REFERENCE("REFERENCE"),
	
	CLAIM("CLAIM"),
	
	ADD_ASSIGNEE("ADD_ASSIGNEE");
	
    private String value;
    
    private ButtonType(String value){
        this.value = value;
    }
    
    public String toString(){
        return value;
    }

    private static final Map<String, ButtonType> mappings = new HashMap<>(ButtonType.values().length);

    static {
        for (ButtonType validFlagEnum : values()) {
            mappings.put(validFlagEnum.toString(), validFlagEnum);
        }
    }

    public static ButtonType resolveByValue(String value) {
        return (value != null ? mappings.get(value) : null);
    }

}