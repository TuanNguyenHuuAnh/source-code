/*******************************************************************************
 * Class        :CompanySearchDto
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * CompanySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class CompanySearchDto extends CommonSearchDto {

    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private String strFieldValues;

    private String name;
    private String description;
    private String systemCode;
    private String systemName;
}