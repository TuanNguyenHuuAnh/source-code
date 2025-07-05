/*******************************************************************************
 * Class        MenuTypeEnum
 * Created date 2017/05/25
 * Lasted date  2017/05/25
 * Author       hand
 * Change log   2017/05/2501-00 hand create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.enumdef;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.common.dto.Select2Dto;

/**
 * MenuTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum MenuTypeEnum {

    BACK_END("1", "menu.type.back.end"),

    FRONT_END("2", "menu.type.front.end");

    /** value */
    private String value;
    private String text;

    private MenuTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String toString() {
        return value;
    }
    
    public String getText() {
        return text;
    }
    
    public static List<Select2Dto> getSelect2Dtos() {
        List<Select2Dto> res = new ArrayList<>();
         for(MenuTypeEnum value : values()) {
            Select2Dto select2Dto = new Select2Dto(value.toString(), value.getText(), value.getText());
            res.add(select2Dto);
        }
        return res;
    }

    public enum MenuTypeCatEnum {
        /** back end */
        BACK_END("1", "menu.type.back.end"),
        
        /** back end */
        FRONT_END("2", "menu.type.front.end"),
        
        /** back end */
        EXTERNAL("3", "menu.type.external"),

        /** main */
        MAIN("main", "menu.type.front.main"),

        /** reference */
        REFERENCE("reference", "menu.type.front.reference"),

        /** tool */
        TOOL("tool", "menu.type.front.tool"),

        /** left */
        LEFT("left", "menu.type.front.left"),

        /** right */
        RIGHT("right", "menu.type.front.right"),

        /** footer */
        FOOTER("footer", "menu.type.front.footer");

        /** value */
        private String value;

        /** name */
        private String name;

        private MenuTypeCatEnum(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String toString() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    public enum MenuTypePositionsEnum {
        
        /** back end */
        TOP("top", "menu.type.front.main.positions.top"),

        /** main */
        LAST("last", "menu.type.front.main.positions.last"),;

        /** value */
        private String value;

        /** name */
        private String name;

        private MenuTypePositionsEnum(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String toString() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
