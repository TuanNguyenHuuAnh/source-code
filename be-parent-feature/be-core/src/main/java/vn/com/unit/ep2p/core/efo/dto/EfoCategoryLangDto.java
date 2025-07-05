/*******************************************************************************
* Class        EfoCategoryLangDto
* Created date 2021/03/03
* Lasted date  2021/03/03
* Author       TaiTT
* Change log   2021/03/03 01-00 TaiTT create a new
******************************************************************************/

package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * EfoCategoryLangDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTT
 */

@Getter
@Setter
public class EfoCategoryLangDto extends AbstractAuditTracking{

	private Long categoryId;

	private Long langId;

	private String langCode;

	private String name;


}