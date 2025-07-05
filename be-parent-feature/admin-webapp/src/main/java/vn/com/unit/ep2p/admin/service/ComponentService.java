/*******************************************************************************
 * Class        :ComponentService
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.admin.dto.ComponentSearchDto;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * ComponentService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public interface ComponentService {   

	/**
     * getComponentList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    PageWrapper<EfoComponentDto> getComponentList(ComponentSearchDto search, int pageSize, int page);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    EfoComponentDto findById(Long id);

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    void initScreenDetail(ModelAndView mav, EfoComponentDto objectDto, Locale locale);

	/**
     * saveComponent
     * @param objectDto
     * @return
     * @author HungHT
     */
    EfoComponent saveComponent(EfoComponentDto objectDto);

	/**
     * deleteComponent
     * @param id
     * @return
     * @author HungHT
     */
    boolean deleteComponent(Long id);
    
    /**
     * getListcomponentByBusinessId
     * @param businessId
     * @return List<EfoComponentDto>
     * @author KhuongTH
     */
    List<EfoComponentDto> getListcomponentByBusinessId(Long businessId);
}