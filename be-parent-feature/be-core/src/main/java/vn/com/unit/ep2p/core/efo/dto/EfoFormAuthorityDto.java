/*******************************************************************************
 * Class        ：EfoFormDto
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractCreatedTracking;

/**
 * EfoFormDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class EfoFormAuthorityDto extends AbstractCreatedTracking{
    
	private Long formId;
	private Long roleId;

	private String formName;
	private boolean accessFlag;
}
