/*******************************************************************************
 * Class        ：AccountTypeEnum
 * Created date ：2021/02/24
 * Lasted date  ：2021/02/24
 * Author       ：vinhlt
 * Change log   ：2021/02/24：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * AccountTypeEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Getter
public enum AccountGroupEnum {

    GROUP_AGENT("GROUP_AGENT")
    , GROUP_AGENT_LEADER("GROUP_AGENT_LEADER")
    , GROUP_GAD("GROUP_GAD")
    , GROUP_CANDIDATE("GROUP_CANDIDATE")
    , GROUP_BD("GROUP_BD")
    , GROUP_SUPPORT("GROUP_SUPPORT");

    private String value;

    AccountGroupEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static List<String> getListValue() {
        List<String> result = new ArrayList<String>();
        for (AccountGroupEnum en : AccountGroupEnum.values()) {
            result.add(en.getValue());
        }
        return result;
    }
}
