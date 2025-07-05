/*******************************************************************************
 * Class        ：RuleDto
 * Created date ：2021/03/11
 * Lasted date  ：2021/03/11
 * Author       ：tantm
 * Change log   ：2021/03/11：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import vn.com.unit.core.dto.JcaRuleSettingDto;

/**
 * RuleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Data
public class RuleSettingDto {

    private List<JcaRuleSettingDto> listRuleSettingDto;

    public RuleSettingDto() {
        this.listRuleSettingDto = new ArrayList<JcaRuleSettingDto>();
    }
}
