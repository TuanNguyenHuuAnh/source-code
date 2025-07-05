/*******************************************************************************
 * Class        ：AbstractCompanyDto
 * Created date ：2019/05/06
 * Lasted date  ：2019/05/06
 * Author       ：HungHT
 * Change log   ：2019/05/06：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AbstractCompanyDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbstractCompanyDto {

    private Long companyId;
    private String companyName;
    private boolean companyAdmin;
    private List<Long> companyIdList;
}
