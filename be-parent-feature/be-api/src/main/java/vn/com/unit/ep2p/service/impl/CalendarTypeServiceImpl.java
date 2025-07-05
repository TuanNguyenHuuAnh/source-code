/*******************************************************************************
 * Class        ：CalendarTypeServiceImpl
 * Created date ：2020/12/14
 * Lasted date  ：2020/12/14
 * Author       ：TrieuVD
 * Change log   ：2020/12/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.enumdef.param.JcaCalendarTypeSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.CalendarTypeAddReq;
import vn.com.unit.ep2p.dto.req.CalendarTypeUpdateReq;
import vn.com.unit.ep2p.service.CalendarTypeService;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SlaCalendarTypeSearchDto;
import vn.com.unit.sla.entity.SlaCalendarType;
import vn.com.unit.sla.service.SlaCalendarTypeService;

/**
 * CalendarTypeServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CalendarTypeServiceImpl extends AbstractCommonService implements CalendarTypeService {

    @Autowired
    private SlaCalendarTypeService slaCalendarTypeService;

    @Autowired
    private MasterCommonService masterCommonService;
    
    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#save(java.lang.Object)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaCalendarTypeDto save(SlaCalendarTypeDto objectDto) throws DetailException {
        return slaCalendarTypeService.saveSlaCalendarTypeDto(objectDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        slaCalendarTypeService.deleteById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
     */
    @Override
    public SlaCalendarTypeDto detail(Long id) throws DetailException {
        return slaCalendarTypeService.getCalendarTypeDtoById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.CalendarTypeService#createCalendarType(vn.com.unit.mbal.api.req.dto.CalendarTypeAddReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaCalendarTypeDto createCalendarType(CalendarTypeAddReq reqCalendarTypeAddDto) throws DetailException {
        SlaCalendarTypeDto calendarTypeDto = objectMapper.convertValue(reqCalendarTypeAddDto, SlaCalendarTypeDto.class);
        return this.save(calendarTypeDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.service.CalendarTypeService#updateCalendarType(vn.com.unit.mbal.api.req.dto.CalendarTypeUpdateReq)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaCalendarTypeDto updateCalendarType(CalendarTypeUpdateReq reqCalendarTypeUpdateDto) throws DetailException {
        SlaCalendarTypeDto calendarTypeDto = objectMapper.convertValue(reqCalendarTypeUpdateDto, SlaCalendarTypeDto.class);
        return this.save(calendarTypeDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.BaseRestService#search(org.springframework.util.MultiValueMap,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public ObjectDataRes<SlaCalendarTypeDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<SlaCalendarTypeDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, SlaCalendarType.class,
                    SlaCalendarTypeService.TABLE_ALIAS_SLA_CALENDAR_TYPE);
            /** init param search repository */
            SlaCalendarTypeSearchDto searchDto = this.buildSlaCalendarTypeSearchDto(commonSearch);

            int totalData = slaCalendarTypeService.countBySearchCondition(searchDto);
            List<SlaCalendarTypeDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = slaCalendarTypeService.getCalendarTypeDtoListByCondition(searchDto, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021701_APPAPI_CALENDAR_TYPE_LIST_ERROR);
        }
        return resObj;
    }

    /**
     * <p>
     * Builds the jca account search dto.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @return {@link JcaAccountSearchDto}
     * @author taitt
     */
    private SlaCalendarTypeSearchDto buildSlaCalendarTypeSearchDto(MultiValueMap<String, String> commonSearch) {
        SlaCalendarTypeSearchDto slaCalendarTypeSearchDto = new SlaCalendarTypeSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
//        Long orgId = null != commonSearch.getFirst("orgId") ? Long.valueOf(commonSearch.getFirst("orgId")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
        slaCalendarTypeSearchDto.setCompanyId(companyId);
//        slaCalendarTypeSearchDto.setOrgId(orgId);

        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaCalendarTypeSearchEnum.valueOf(enumValue)) {
                case CALENDAR_TYPE_CODE:
                    slaCalendarTypeSearchDto.setCalendarTypeCode(keySearch);
                    break;
                case CALENDAR_TYPE_NAME:
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
            slaCalendarTypeSearchDto.setCalendarTypeCode(keySearch);
            slaCalendarTypeSearchDto.setCalendarTypeName(keySearch);
            slaCalendarTypeSearchDto.setDescription(keySearch);
        }
        
        return slaCalendarTypeSearchDto;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaCalendarTypeSearchEnum.values());         
    }

}
