/*******************************************************************************
 * Class        ：EfoFormLangDto
 * Created date ：2021/03/05
 * Lasted date  ：2021/03/05
 * Author       ：Tan Tai
 * Change log   ：2021/03/05：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractAuditTracking;

/**
 * EfoFormLangDto
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Getter
@Setter
public class EfoFormLangDto extends AbstractAuditTracking{

	private Long formId;

	private Long langId;

	private String langCode;

	private String name;
}
