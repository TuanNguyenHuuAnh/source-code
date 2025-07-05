package vn.com.unit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * SelectItem
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectItem {

    /** value */
    private String value;
    
    /** name */
    private String name;

    /** selected */
    private boolean selected;
    
    
    public SelectItem(String value, String name) {
    	this.value = value;
    	this.name = name;
    }

    

}