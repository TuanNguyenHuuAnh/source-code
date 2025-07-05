/*******************************************************************************
 * Class        :CalendarTypeServiceImpl
 * Created date :2019/06/25
 * Lasted date  :2019/06/25
 * Author       :HungHT
 * Change log   :2019/06/25:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.enumdef.CalendarTypeSortEnum;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.sla.dto.CalendarTypeSearchDto;
import vn.com.unit.ep2p.admin.sla.enumdef.CalendarTypeSearchEnum;
import vn.com.unit.ep2p.admin.sla.repository.CalendarTypeRepository;
import vn.com.unit.ep2p.admin.sla.service.CalendarTypeAppService;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SlaCalendarTypeSearchDto;
import vn.com.unit.sla.dto.SortOrderDto;
import vn.com.unit.sla.entity.SlaCalendarType;
import vn.com.unit.sla.service.impl.SlaCalendarTypeServiceImpl;

/**
 * CalendarTypeServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@SuppressWarnings("deprecation")
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class CalendarTypeAppServiceImpl extends SlaCalendarTypeServiceImpl implements CalendarTypeAppService, AbstractCommonService {  

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CalendarTypeRepository calendarTypeRepository;
    
    @Autowired
    private CommonService comService;
    
    @Autowired
    private ObjectMapper objectMapper;

	// Model mapper
	ModelMapper modelMapper = new ModelMapper();

    /**
     * getCalendarTypeList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    public PageWrapper<SlaCalendarTypeDto> getCalendarTypeList(CalendarTypeSearchDto search, int pageSize, int page, Pageable pageable) throws DetailException {
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        
        //Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending(): Sort.by(sortField).descending();
          /** init pageable */
        //Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), SlaCalendarTypeDto.class, TABLE_ALIAS_SLA_CALENDAR_TYPE);
        
        //Pageable pageableAfterBuild = 
        Sort sortForEnums = comService.buildSortEnums(pageable.getSort(), CalendarTypeSortEnum.values(), SlaCalendarTypeDto.class);
        //pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortForEnums);
        if(pageable.getPageNumber()>=1)
        	pageable = PageRequest.of(pageable.getPageNumber()-1, pageSize, sortForEnums);
        else
        	pageable = PageRequest.of(pageable.getPageNumber(), pageSize, sortForEnums);
          /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(search, objectMapper);
        SlaCalendarTypeSearchDto reqSearch = this.buildSlaCalendarTypeSearchDto(commonSearch);
        PageWrapper<SlaCalendarTypeDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        
        int count = this.countBySearchCondition(reqSearch);
        List<SlaCalendarTypeDto> result = new ArrayList<>();
        if (count > 0) {
//            int currentPage = pageWrapper.getCurrentPage();
//            int startIndex = (currentPage - 1) * sizeOfPage;
            result = this.getCalendarTypeDtoListByCondition(reqSearch, pageable);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }
    
    private SlaCalendarTypeSearchDto buildSlaCalendarTypeSearchDto(MultiValueMap<String, String> commonSearch) {
        SlaCalendarTypeSearchDto slaCalendarTypeSearchDto = new SlaCalendarTypeSearchDto();

        String keySearch = null != commonSearch.getFirst("fieldSearch") ? commonSearch.getFirst("fieldSearch") : CoreConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String description = null != commonSearch.getFirst("description") ? commonSearch.getFirst("description") : CoreConstant.EMPTY;
        String code = null != commonSearch.getFirst("code") ? commonSearch.getFirst("code") : CoreConstant.EMPTY;
        String name = null != commonSearch.getFirst("name") ? commonSearch.getFirst("name") : CoreConstant.EMPTY;
		
//        Long orgId = null != commonSearch.getFirst("orgId") ? Long.valueOf(commonSearch.getFirst("orgId")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldValues"))
                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("fieldValues"), ","))
                : null;
                
        slaCalendarTypeSearchDto.setCompanyId(companyId);
//        slaCalendarTypeSearchDto.setOrgId(orgId);

        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (CalendarTypeSearchEnum.valueOf(enumValue)) {
                case CODE:
                    slaCalendarTypeSearchDto.setCalendarTypeCode(keySearch);
                    break;
                case NAME:
                    slaCalendarTypeSearchDto.setCalendarTypeName(keySearch);
                    break;
                case DESCRIPTION:
                    slaCalendarTypeSearchDto.setDescription(keySearch);
                    break;

                default:
                    slaCalendarTypeSearchDto.setCalendarTypeCode(keySearch);
                    slaCalendarTypeSearchDto.setCalendarTypeName(keySearch);
                    slaCalendarTypeSearchDto.setDescription(keySearch);
                    break;
                }
            }
        }else {
        	if(keySearch != null && !keySearch.isEmpty()) {
            	slaCalendarTypeSearchDto.setCalendarTypeCode(keySearch);
                slaCalendarTypeSearchDto.setCalendarTypeName(keySearch);
            	slaCalendarTypeSearchDto.setDescription(keySearch);	
        	}else {
        		slaCalendarTypeSearchDto.setCalendarTypeCode(code);
                slaCalendarTypeSearchDto.setCalendarTypeName(name);
        		slaCalendarTypeSearchDto.setDescription(description);
        	}
        }
        
        return slaCalendarTypeSearchDto;
    }
    
	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    public SlaCalendarTypeDto findById(Long id) {
        return calendarTypeRepository.findById(id);
    }

	/**
     * findByProperties1
     * @param properties1
     * @return
     * @author HungHT
     */
    public SlaCalendarTypeDto findByProperties1(String properties1) {
        return calendarTypeRepository.findByProperties1(properties1);
    }

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    public void initScreenDetail(ModelAndView mav, SlaCalendarTypeDto objectDto, Locale locale) {
        // TODO 
    }

	/**
     * saveCalendarType
     * @param objectDto
     * @return
     * @author HungHT
     */
    public SlaCalendarType saveCalendarType(SlaCalendarTypeDto objectDto) {        
        SlaCalendarType objectResult = null;
		Date sysDate = comService.getSystemDateTime();
        Long userId = UserProfileUtils.getAccountId();
        SlaCalendarType objectSave = modelMapper.map(objectDto, SlaCalendarType.class);
        if (objectSave != null) {            
            if (objectSave.getId() == null) {
                // Add history change
            	objectSave.setCreatedId(userId);
            	objectSave.setCreatedDate(sysDate);
            } else {
                SlaCalendarType objectCurrent = calendarTypeRepository.findOne(objectDto.getId());
                // Keep value not change
                objectSave.setCreatedId(objectCurrent.getCreatedId());
                objectSave.setCreatedDate(objectCurrent.getCreatedDate());
                objectSave.setCompanyId(objectCurrent.getCompanyId());
                objectSave.setUpdatedId(userId);
                objectSave.setUpdatedDate(sysDate);
            }
//            Long displayOrder = objectDto.getDisplayOrder();
//            if( displayOrder == null ) {
//            	Long displayOrderMax = calendarTypeRepository.findMaxDisplayOrderByCompanyId(objectSave.getCompanyId());
//            	objectSave.setDisplayOrder(displayOrderMax + 1);
//            }
            if(objectSave.getId() != null) {
                objectResult = calendarTypeRepository.update(objectSave);
            }else {
                objectResult = calendarTypeRepository.create(objectSave);
            }
        }
        return objectResult;
    }

	/**
     * deleteCalendarType
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteCalendarType(Long id) {
		Date sysDate = comService.getSystemDateTime();
        Long userId = UserProfileUtils.getAccountId();
        SlaCalendarType object = calendarTypeRepository.findOne(id);
        object.setDeletedId(userId);
        object.setDeletedDate(sysDate);
        if(object.getId() != null) {
            calendarTypeRepository.update(object);
        }else {
            calendarTypeRepository.create(object);
        }
        return true;
    }
    
    /**
     * findMaxDisplayOrderByCompanyId
     * 
     * @param companyId
     * @return
     * @author XuanN
     */
    public Long findMaxDisplayOrderByCompanyId(Long companyId) {
        return calendarTypeRepository.findMaxDisplayOrderByCompanyId(companyId);
    }
    
    /**
     * getCalendarTypeListByYearnCompany
     * @param year
     * @param companyId
     * @return
     * @author hiepth
     */
    public List<Select2Dto> getCalendarTypeListByYearnCompany( Long companyId) {
    	return calendarTypeRepository.getCalendarTypeListByYearnCompany( companyId);
    }


    @Override
    public SlaCalendarTypeDto findByCodeAndCompanyId(String code, Long companyId) {
        return calendarTypeRepository.findByCodeAndCompanyId(code, companyId);
    }


    @Override
    public vn.com.unit.common.service.JCommonService getCommonService() {
        return comService;
    }
    
    @Override
	@Transactional
	public void updateSortAll(List<SortOrderDto> sortOderList) {
		for (SortOrderDto dto : sortOderList) {
			try {
				calendarTypeRepository.updateSortAll(dto);
				}
			catch (Exception e) {
				// TODO: handle exception
			}
		}

		@SuppressWarnings("unused")
		Long itemId = 0L;
		for (SortOrderDto dto : sortOderList) {
			SlaCalendarType item = calendarTypeRepository.findOne(dto.getObjectId());
			//item.setBeforeId(itemId);
			itemId = item.getId();
			calendarTypeRepository.save(item);
		}

	}
    

}