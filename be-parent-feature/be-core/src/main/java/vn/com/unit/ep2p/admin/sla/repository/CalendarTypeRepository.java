/*******************************************************************************
 * Class        :CalendarTypeRepository
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.ep2p.admin.sla.dto.CalendarTypeSearchDto;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SortOrderDto;
import vn.com.unit.sla.repository.SlaCalendarTypeRepository;

/**
 * CalendarTypeRepository
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@SuppressWarnings("deprecation")
public interface CalendarTypeRepository extends SlaCalendarTypeRepository {

	/**
     * countCalendarTypeList
     * @param search
     * @return
     * @author HungHT
     */
    int countCalendarTypeList(@Param("search") CalendarTypeSearchDto search);

	/**
     * getCalendarTypeList
     * @param search
     * @param offset
     * @param sizeOfPage
     * @return
     * @author HungHT
     */
    List<SlaCalendarTypeDto> getCalendarTypeList(@Param("search") CalendarTypeSearchDto search, @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage);

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    SlaCalendarTypeDto findById(@Param("id") Long id);

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    SlaCalendarTypeDto findByProperties1(@Param("properties1") String properties1);
    
    /**
     * findMaxDisplayOrderByCompanyId
     * @param companyId
     * @return
     * @author XuanN
     */
    Long findMaxDisplayOrderByCompanyId(@Param("companyId") Long companyId);
    
    
    int countByCalendarTypeSearchDto(@Param("calendarTypeSearchDto") CalendarTypeSearchDto calendarTypeSearchDto);
    
    /**
     * getCalendarTypeListByYearnCompany
     * @param year
     * @param companyId
     * @return
     * @author hiepth
     */
    List<Select2Dto> getCalendarTypeListByYearnCompany( @Param("companyId") Long companyId);
    
    /**
     * findByCodeAndCompanyId
     * @param code
     * @param companyId
     * @return
     * @author trieuvd
     */
    SlaCalendarTypeDto findByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);
    
    @Modifying
    public void updateSortAll(@Param("cond") SortOrderDto sortOderList);

}