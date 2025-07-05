/*******************************************************************************
 * Class        ：JcaConstantSearchDto
 * Created date ：2020/12/01
 * Lasted date  ：2020/12/01
 * Author       ：tantm
 * Change log   ：2020/12/01：01-00 tantm create a new
 ******************************************************************************/

package vn.com.unit.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * JcaConstantSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Setter
@Getter
public class JcaConstantSearchDto {

    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;
    private String groupCode;
    private String kind;
    private String langCode;
    private String code;
    private String name;
}
