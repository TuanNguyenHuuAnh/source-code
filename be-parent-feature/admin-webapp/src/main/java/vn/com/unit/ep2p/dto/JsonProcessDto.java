/*******************************************************************************
 * Class        ：jsonProcessDto
 * Created date ：2019/12/02
 * Lasted date  ：2019/12/02
 * Author       ：KhuongTH
 * Change log   ：2019/12/02：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * jsonProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class JsonProcessDto {

    private List<String> requiredFields;

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(List<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    /**
     * @author KhuongTH
     */
    public JsonProcessDto() {
    }

    /**
     * @param requiredFields
     * @author KhuongTH
     */
    public JsonProcessDto(List<String> requiredFields) {
        super();
        this.requiredFields = requiredFields;
    }

}
