/*******************************************************************************
 * Class        ：ResSaveOZDFile
 * Created date ：2019/11/05
 * Lasted date  ：2019/11/05
 * Author       ：HungHT
 * Change log   ：2019/11/05：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.core.res;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.dts.api.dto.AbstractResApiExternalDto;

/**
 * ResSaveOZDFile
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Getter
@Setter
public class ResSaveOZDFile extends AbstractResApiExternalDto {

    private ResFileResult resultObj;
}
