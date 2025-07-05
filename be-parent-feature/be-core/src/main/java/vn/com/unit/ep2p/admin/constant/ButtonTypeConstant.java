package vn.com.unit.ep2p.admin.constant;

import java.util.LinkedHashMap;
import java.util.Map;

import vn.com.unit.workflow.enumdef.ButtonType;

public class ButtonTypeConstant {

    public static Map<String, String> getButtonType() {
        Map<String, String> map = getAllButtonType();
        // remove delete and save button, dont use to approval process
        map.remove(ButtonType.DELETE.toString());
        map.remove(ButtonType.SAVE.toString());
        return map;
    }

    public static Map<String, String> getAllButtonType() {
        Map<String, String> map = new LinkedHashMap<>();
        for (ButtonType validFlagEnum : ButtonType.values()) {
            map.put(validFlagEnum.toString(), validFlagEnum.toString());
        }
        return map;
    }
}
