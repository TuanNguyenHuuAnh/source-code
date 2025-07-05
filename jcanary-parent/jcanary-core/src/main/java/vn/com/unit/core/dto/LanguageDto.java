/*******************************************************************************
 * Class        LanguageDto
 * Created date 2017/02/23
 * Lasted date  2017/02/23
 * Author       NhanNV
 * Change log   2017/02/2301-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.dto.AbstractCompanyDto;

/**
 * LanguageDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */

@Setter
@Getter
public class LanguageDto extends AbstractCompanyDto {
    
	private Long id;

	private Long companyId;

	private String code;

	private String name;

	private Long sort;

	private String iconClasses;

	private Long version;
    
} 
