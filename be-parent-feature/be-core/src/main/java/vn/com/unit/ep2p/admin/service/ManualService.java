/*******************************************************************************
 * Class        : ManualService
 * Created date : 2017/05/31
 * Lasted date  : 2017/05/31
 * Author       : phunghn
 * Change log   : 2017/05/31 : 01-00 phunghn create a new
 * Change log	: 2018/04/20 : 02-00 LongPNT add countWardByCondition & findWardLimitByCondition method
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.QuartzTriggersDto;
import vn.com.unit.ep2p.admin.dto.RoleListDto;
import vn.com.unit.ep2p.admin.sla.dto.QuartzJobSearchDto;
import vn.com.unit.ep2p.admin.sla.dto.VietnameseHolidaySearchDto;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.entity.SlaCalendar;

/**
 * ManualService
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface ManualService {

    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param accountSearchDto
     * @return
     */
    public List<AccountListDto> findListByAccountSearchDto(int offset, int sizeOfPage, JcaAccountSearchDto accountSearchDto);

    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param jcaRoleSearchDto
     * @return
     */
    public List<RoleListDto> findListByRoleSearchDto(int offset, int sizeOfPage, JcaRoleSearchDto jcaRoleSearchDto);

    /**
     * countByAccountSearchDto
     *
     * @param accountSearchDto
     * @return
     * @author phatvt
     */
    public int countByAccountSearchDto(JcaAccountSearchDto accountSearchDto);

    /**
     * deleteAuthorityDtoByRoleIdAndFunctionType
     *
     * @param roleId
     * @param functionType
     * @author phatvt
     */
    public void deleteAuthorityDtoByRoleIdAndFunctionType(Long roleId, String functionType);

    /**
     * countMenuByCondition
     *
     * @param menu
     * @param languageCode
     * @return
     * @author phatvt
     */
    public int countMenuByCondition(MenuDto menu, String languageCode);

    /**
     * findMenuListByCondition
     *
     * @param startIndex
     * @param sizeOfPage
     * @param condition
     * @param languageCode
     * @return
     * @author phatvt
     */
    public List<MenuDto> findMenuListByCondition(int startIndex, int sizeOfPage, MenuDto condition, String languageCode);

    /**
     * countByRoleSearchDto
     *
     * @param jcaRoleSearchDto
     * @return
     * @author phatvt
     */
    public int countByRoleSearchDto(JcaRoleSearchDto jcaRoleSearchDto);

    public void deleteAuthorityDtoByRoleIdAndFunctionByTypeRole(Long roleId, String functionType);

    /**
     * countByQuartzJobSearchDto
     *
     * @param quartzSearchDto
     * @return
     * @author hangnkm
     */
    public int countByQuartzJobSearchDto(QuartzJobSearchDto quartzSearchDto);

    /**
     * findListByQuartzSearchDto
     *
     * @param offset
     * @param sizeOfPage
     * @param quartzSearchDto
     * @return
     * @author hangnkm
     */
    public List<QuartzTriggersDto> findListByQuartzSearchDto(int offset, int sizeOfPage, QuartzJobSearchDto quartzSearchDto);

    /**
     * findAllVietnameseHolidayListByYear
     *
     * @param vietnameseHolidaySearchDto
     * @return List<VietnameseHolidayDto>
     * @author hiepth
     */
    public List<SlaCalendarDto> findAllVietnameseHolidayListByYear(VietnameseHolidaySearchDto vietnameseHolidaySearchDto);

    /**
     * findTop1DateByCondition
     *
     * @param vietnameseHolidayDate
     * @return VietnameseHoliday
     * @author hiepth
     */
    public SlaCalendar findTop1DateByCondition(Date vietnameseHolidayDate, Long companyId, Long calendarType);

}
