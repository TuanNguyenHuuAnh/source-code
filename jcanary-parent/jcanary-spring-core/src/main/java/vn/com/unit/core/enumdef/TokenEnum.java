package vn.com.unit.core.enumdef;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenEnum {

    ACCESS_TOKEN("1")
    , REFRESH_TOKEN("2")
    , OTHER("OTHER")
    ;

    private String type;

    private static final Map<String, TokenEnum> mappings = new HashMap<>(TokenEnum.values().length);

    static {
        for (TokenEnum token : values()) {
            mappings.put(token.getType(), token);
        }
    }

    public static TokenEnum resolveEnum(String type) {
        return (mappings.get(type) != null ? mappings.get(type) : mappings.get("OTHER"));
    }
}
