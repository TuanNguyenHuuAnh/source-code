/*******************************************************************************
 * Class        :CalendarTypeService
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.service;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.sla.dto.CalendarTypeSearchDto;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SortOrderDto;
import vn.com.unit.sla.entity.SlaCalendarType;
import vn.com.unit.sla.service.SlaCalendarTypeService;

/**
 * CalendarTypeService
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@SuppressWarnings("deprecation")
public interface CalendarTypeAppService extends SlaCalendarTypeService{   

	/**
     * getCalendarTypeList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
	 * @throws DetailException 
     */
    PageWrapper<SlaCalendarTypeDto> getCalendarTypeList(CalendarTypeSearchDto search, int pageSize, int page, Pageable pageable) throws DetailException;

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    SlaCalendarTypeDto findById(Long id);

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    SlaCalendarTypeDto findByProperties1(String properties1);

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    void initScreenDetail(ModelAndView mav, SlaCalendarTypeDto objectDto, Locale locale);

	/**
     * saveCalendarType
     * @param objectDto
     * @return
     * @author HungHT
     */
    SlaCalendarType saveCalendarType(SlaCalendarTypeDto objectDto);

	/**
     * deleteCalendarType
     * @param id
     * @return
     * @author HungHT
     */
    boolean deleteCalendarType(Long id);
    
    /**
     * findMaxDisplayOrderByCompanyId
     * 
     * @param companyId
     * @return
     * @author XuanN
     */
    Long findMaxDisplayOrderByCompanyId(Long companyId);
    
    /**
     * getCalendarTypeListByYearnCompany
     * @param year
     * @param companyId
     * @return
     * @author hiepth
     */
    List<Select2Dto> getCalendarTypeListByYearnCompany( Long companyId);
    
    /**
     * findByCode
     * @param code
     * @return
     * @author trieuvd
     */
    SlaCalendarTypeDto findByCodeAndCompanyId(String code, Long companyId);
    
    public void updateSortAll(List<SortOrderDto> sortOderList);
    
}