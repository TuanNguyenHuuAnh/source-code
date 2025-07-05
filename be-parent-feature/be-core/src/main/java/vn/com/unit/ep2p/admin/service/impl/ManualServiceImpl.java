/*******************************************************************************
 * Class         : ManualServiceImpl
 * Created date  : 2017/05/31
 * Lasted date   : 2017/05/31
 * Author        : phunghn
 * Change log    : 2017/05/31 : 01-00 phunghn create a new
 * Change log	 : 2018/04/20 : 02-00 LongPNT add countWardByCondition & findWardLimitByCondition method
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.AccountListDto;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.QuartzTriggersDto;
import vn.com.unit.ep2p.admin.dto.RoleListDto;
import vn.com.unit.ep2p.admin.repository.AccountRepository;
import vn.com.unit.ep2p.admin.repository.AppAuthorityRepository;
import vn.com.unit.ep2p.admin.repository.MenuRepository;
import vn.com.unit.ep2p.admin.repository.QuartzJobRepository;
import vn.com.unit.ep2p.admin.repository.RoleRepository;
//import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.ManualService;
import vn.com.unit.ep2p.admin.sla.dto.QuartzJobSearchDto;
import vn.com.unit.ep2p.admin.sla.dto.VietnameseHolidaySearchDto;
import vn.com.unit.ep2p.admin.sla.repository.VietnameseHolidayRepository;
import vn.com.unit.sla.dto.SlaCalendarDto;
import vn.com.unit.sla.entity.SlaCalendar;

/**
 * ManualServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ManualServiceImpl implements ManualService {
    private enum DBType {
        SQLSERVER, MYSQL, ORACLE;
    }

    /** accountRepository */
    @Autowired
    private AccountRepository accRepository;

    /** systemConfig */
    @Autowired
    private SystemConfig systemConfig;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppAuthorityRepository authorityRepository;

    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private QuartzJobRepository quartzJobRepository;
    
    @Autowired
    private VietnameseHolidayRepository vietnameseHolidayRepository;
    
//    @Autowired
//    private CommonService comService;

    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param accountSearchDto
     * @return
     */
    @Override
    public List<AccountListDto> findListByAccountSearchDto(int offset, int sizeOfPage, JcaAccountSearchDto accountSearchDto) {
        List<AccountListDto> list = new ArrayList<AccountListDto>();
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            list = accRepository.findListByAccountSearchDto(offset, sizeOfPage, accountSearchDto);
            // list = accRepository.findListByAccountSearchDtoSQLServer(offset, sizeOfPage, accountSearchDto);
            break;
        case MYSQL:
            list = accRepository.findListByAccountSearchDto(offset, sizeOfPage, accountSearchDto);
            // list = accRepository.findListByAccountSearchDtoSQLServer(offset, sizeOfPage, accountSearchDto);
            break;
        case ORACLE:
            list = accRepository.findListByAccountSearchDto(offset, sizeOfPage, accountSearchDto);
            // list = accRepository.findListByAccountSearchDtoOracle(offset, sizeOfPage, accountSearchDto);
            break;
        default:
            list = accRepository.findListByAccountSearchDto(offset, sizeOfPage, accountSearchDto);
            break;
        }
        return list;
    }



   


    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param jcaRoleSearchDto
     * @return
     */
    @Override
    public List<RoleListDto> findListByRoleSearchDto(int offset, int sizeOfPage, JcaRoleSearchDto jcaRoleSearchDto) {
        List<RoleListDto> list = new ArrayList<RoleListDto>();
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            list = roleRepository.findListByRoleSearchDto(offset, sizeOfPage, jcaRoleSearchDto);
            // list = roleRepository.findListByRoleSearchDtoSQLServer(offset, sizeOfPage, roleSearchDto);
            break;
        case MYSQL:
            list = roleRepository.findListByRoleSearchDto(offset, sizeOfPage, jcaRoleSearchDto);
            // list = roleRepository.findListByRoleSearchDtoSQLServer(offset, sizeOfPage, roleSearchDto);
            break;
        case ORACLE:
            list = roleRepository.findListByRoleSearchDto(offset, sizeOfPage, jcaRoleSearchDto);
            // list = roleRepository.findListByRoleSearchDtoOracle(offset, sizeOfPage, roleSearchDto);
            break;
        default:
            list = roleRepository.findListByRoleSearchDto(offset, sizeOfPage, jcaRoleSearchDto);
        }
        return list;
    }

    @Override
    public int countByAccountSearchDto(JcaAccountSearchDto accountSearchDto) {
        int count = 0;
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            count = accRepository.countByAccountSearchDto(accountSearchDto);
            break;
        case MYSQL:
            count = accRepository.countByAccountSearchDto(accountSearchDto);
            break;
        case ORACLE:
            count = accRepository.countByAccountSearchDto(accountSearchDto);
            // count = accRepository.countByAccountSearchDtoOracle(accountSearchDto);
            break;
        default:
            count = accRepository.countByAccountSearchDto(accountSearchDto);
            break;
        }
        return count;
    }

    /**
     * deleteAuthorityDtoByRoleIdAndFunctionType
     *
     * @param roleId
     * @param functionType
     * @author phatvt
     */
    @Override
    public void deleteAuthorityDtoByRoleIdAndFunctionType(Long roleId, String functionType) {
        authorityRepository.deleteAuthorityDtoByRoleIdAndFunctionType(roleId, functionType);
    }

    /**
     * countMenuByCondition
     *
     * @param menu
     * @param languageCode
     * @return
     * @author phatvt
     */
    @Override
    public int countMenuByCondition(MenuDto menu, String languageCode) {
        int count = 0;
        // don't change switch case here
        // java.lang.NoClassDefFoundError
        try {
        	// don't change switch case here
            // java.lang.NoClassDefFoundError
			DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
			switch (dataType.toString()) {
			case "SQLSERVER":
				count = menuRepository.countMenuByCondition(menu, languageCode);
				break;
			case "MYSQL":
				count = menuRepository.countMenuByCondition(menu, languageCode);
				break;
			case "ORACLE":
				count = menuRepository.countMenuByCondition(menu, languageCode);
				// count = menuRepository.countMenuByConditionOracle(menu, languageCode);
				break;
			default:
				count = menuRepository.countMenuByCondition(menu, languageCode);
				break;
			}
		} catch (Exception e) {
			count = menuRepository.countMenuByCondition(menu, languageCode);
		}

        return count;
    }

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
    @Override
    public List<MenuDto> findMenuListByCondition(int startIndex, int sizeOfPage, MenuDto condition, String languageCode) {

        List<MenuDto> result = new LinkedList<MenuDto>();
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType.toString()) { // fix java.lang.NoClassDefFoundError
        case "SQLSERVER":
            result = menuRepository.findMenuListByCondition(startIndex, sizeOfPage, condition, languageCode);
            break;
        case "MYSQL":
            result = menuRepository.findMenuListByCondition(startIndex, sizeOfPage, condition, languageCode);
            break;
        case "ORACLE":
            result = menuRepository.findMenuListByCondition(startIndex, sizeOfPage, condition, languageCode);
            // result = menuRepository.findMenuListByConditionOracle(startIndex, sizeOfPage, condition, languageCode);
            break;
        default:
            result = menuRepository.findMenuListByCondition(startIndex, sizeOfPage, condition, languageCode);
            break;
        }
        return result;
    }

    /**
     * countByRoleSearchDto
     *
     * @param jcaRoleSearchDto
     * @return
     * @author phatvt
     */
    @Override
    public int countByRoleSearchDto(JcaRoleSearchDto jcaRoleSearchDto) {
        int count = 0;
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            count = roleRepository.countByRoleSearchDto(jcaRoleSearchDto);
            break;
        case MYSQL:
            count = roleRepository.countByRoleSearchDto(jcaRoleSearchDto);
            break;
        case ORACLE:
            count = roleRepository.countByRoleSearchDto(jcaRoleSearchDto);
            // count = roleRepository.countByRoleSearchDtoOracle(roleSearchDto);
            break;
        default:
            count = roleRepository.countByRoleSearchDto(jcaRoleSearchDto);
            break;
        }
        return count;
    }

    @Override
    public void deleteAuthorityDtoByRoleIdAndFunctionByTypeRole(Long roleId, String functionType) {
        authorityRepository.deleteAuthorityDtoByRoleIdAndRoleType(
                roleId, functionType);
    }

    @Override
    public int countByQuartzJobSearchDto(QuartzJobSearchDto quartzSearchDto) {
        int count = 0 ;
        DBType dataType = DBType
                .valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            count = quartzJobRepository.countByQuartzJobSearchDto(quartzSearchDto);
            break;
        case MYSQL:
            count = quartzJobRepository.countByQuartzJobSearchDto(quartzSearchDto);
            break;
        default:
            break;
        }
        return count;
    }

    @Override
    public List<QuartzTriggersDto> findListByQuartzSearchDto(int offset, int sizeOfPage, QuartzJobSearchDto quartzSearchDto) {
        List<QuartzTriggersDto> list = new ArrayList<QuartzTriggersDto>();
        DBType dataType = DBType
                .valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
            list = quartzJobRepository.findListByQuartzSearchDtoSQLServer(offset,
                    sizeOfPage, quartzSearchDto);
            break;
        default:
            break;
        }
        return list;
    }
    
    @Override
    public List<SlaCalendarDto> findAllVietnameseHolidayListByYear(VietnameseHolidaySearchDto vietnameseHolidaySearchDto) {
    	List<SlaCalendarDto> list = new ArrayList<SlaCalendarDto>();
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
        	list = vietnameseHolidayRepository.findAllVietnameseHolidayListByYear(vietnameseHolidaySearchDto);
            break;
        case ORACLE:
        	list =  vietnameseHolidayRepository.findAllVietnameseHolidayListByYearOracle(vietnameseHolidaySearchDto);
            break;
        default:
            break;
        }
        return list;
    }
    
    @Override
    public SlaCalendar findTop1DateByCondition(Date vietnameseHolidayDate, Long companyId, Long calendarType) {
    	SlaCalendar date = new SlaCalendar();
        DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
        switch (dataType) {
        case SQLSERVER:
        	date = vietnameseHolidayRepository.findTop1DateByCondition(vietnameseHolidayDate, companyId, calendarType);
            break;
        case ORACLE:
        	date =  vietnameseHolidayRepository.findTop1DateByConditionOracle(vietnameseHolidayDate, companyId, calendarType);
            break;
        default:
            break;
        }
        return date;
    }
}
