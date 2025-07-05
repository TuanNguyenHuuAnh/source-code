/*******************************************************************************
 * Class        ConstantDisplayService
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.dts.exception.DetailException;

/**
 * ConstantDisplayService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface ConstantDisplayService extends JcaConstantService{

    
    /**
     * findJcaConstantByTypeAndLang
     * @param type
     * @param locale
     * @return
     * @author trieuvd
     */
    List<JcaConstantDto> findConstantDisplayByTypeAndLang(String type, Locale locale);

	/**
	 * findType
	 * @return
	 * @author Tan Tai
	 */
	List<Select2Dto> findType();
	
	/**
	 * @author vunt
	 * @param page
	 * @param searchDto
	 * @param pageSize
	 * @return
	 * @throws DetailException
	 */
	PageWrapper<JcaConstantDto> doSearch(int page, JcaConstantSearchDto searchDto, int pageSize) throws DetailException;

	/**
	 * @param dto
	 */
	void submit(@Valid JcaGroupConstantDto dto);
    
}
