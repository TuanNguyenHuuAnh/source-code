/*******************************************************************************
* Class        I18nLocaleDefaultDto
* Created date 2021/01/06
* Lasted date  2021/01/06
* Author       NhanNV
* Change log   2021/01/06 01-00 NhanNV create a new
******************************************************************************/

package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * I18nLocaleDefaultDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Getter
@Setter
public class I18nLocaleDefaultDto extends AbstractTracking {

	private Long id;

	private Long companyId;

	private String messageKey;

	private String messageContent;

	private String locale;

}