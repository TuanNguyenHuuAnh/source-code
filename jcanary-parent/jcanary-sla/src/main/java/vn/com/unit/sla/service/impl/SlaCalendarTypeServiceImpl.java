/*******************************************************************************
 * Class        ：SlaCalendarTypeServiceImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaCalendarTypeDto;
import vn.com.unit.sla.dto.SlaCalendarTypeSearchDto;
import vn.com.unit.sla.entity.SlaCalendarType;
import vn.com.unit.sla.repository.SlaCalendarTypeRepository;
import vn.com.unit.sla.service.SlaCalendarTypeService;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * SlaCalendarTypeServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaCalendarTypeServiceImpl extends AbstractSlaService implements SlaCalendarTypeService {

    /** The sla calendar type repository. */
    @Autowired
    private SlaCalendarTypeRepository slaCalendarTypeRepository;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SlaCalendarTypeServiceImpl.class);

    /** The Constant CALENDAR_TYPE_ID. */
    private static final String CALENDAR_TYPE_ID = "calendarTypeId";

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#getCalendarTypeById(java.lang.Long)
     */
    @Override
    public SlaCalendarType getCalendarTypeById(Long calendarTypeId) throws DetailException {
        if (null == calendarTypeId) {
            logger.error("[SlaCalendarTypeServiceImpl] [findCalendarTypeById] calendarTypeId is null");
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { CALENDAR_TYPE_ID },
                    true);
        } else {
            return slaCalendarTypeRepository.findOne(calendarTypeId);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#getCalendarTypeDtoById(java.lang.Long)
     */
    @Override
    public SlaCalendarTypeDto getCalendarTypeDtoById(Long calendarTypeId) throws DetailException {
        if (null == calendarTypeId) {
            logger.error("[SlaCalendarTypeServiceImpl] [findCalendarTypeById] calendarTypeId is null");
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { CALENDAR_TYPE_ID },
                    true);
        } else {
            SlaCalendarTypeDto calendarTypeDto = new SlaCalendarTypeDto();
            calendarTypeDto = slaCalendarTypeRepository.getCalendarTypeDtoById(calendarTypeId);
            return calendarTypeDto;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#saveSlaCalendarType(vn.com.unit.sla.entity.SlaCalendarType)
     */
    @Override
    @Transactional
    public SlaCalendarType saveSlaCalendarType(SlaCalendarType slaCalendarType) throws DetailException {
        Long id = slaCalendarType.getId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = SlaConstant.SYSTEM_ID;
        if (null != id) {
            SlaCalendarType oldCalendarType = this.getCalendarTypeById(id);
            if (null != oldCalendarType) {
                slaCalendarType.setUpdatedDate(sysDate);
                slaCalendarType.setUpdatedId(userId);
                slaCalendarTypeRepository.update(slaCalendarType);
            } else {
                logger.error("[SlaCalendarTypeServiceImpl] [saveSlaCalendarType] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaCalendarType.setCreatedDate(sysDate);
            slaCalendarType.setCreatedId(userId);
            slaCalendarType.setUpdatedDate(sysDate);
            slaCalendarType.setUpdatedId(userId);
            slaCalendarTypeRepository.create(slaCalendarType);
        }
        return slaCalendarType;
    }


    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#saveSlaCalendarTypeDto(vn.com.unit.sla.dto.SlaCalendarTypeDto)
     */
    @Override
    @Transactional
    public SlaCalendarTypeDto saveSlaCalendarTypeDto(SlaCalendarTypeDto slaCalendarTypeDto) throws DetailException {
        SlaCalendarType calendarType = mapper.convertValue(slaCalendarTypeDto, SlaCalendarType.class);
        calendarType = this.saveSlaCalendarType(calendarType);
        slaCalendarTypeDto.setId(calendarType.getId());
        return slaCalendarTypeDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#countBySearchCondition(vn.com.unit.sla.service.SlaCalendarTypeSearchDto)
     */
    @Override
    public int countBySearchCondition(SlaCalendarTypeSearchDto slaCalendarTypeSearchDto) {
        return slaCalendarTypeRepository.countBySearchCondition(slaCalendarTypeSearchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.sla.service.SlaCalendarTypeService#getCalendarTypeDtoListByCondition(vn.com.unit.sla.service.SlaCalendarTypeSearchDto,
     * int, int, boolean)
     */
    @Override
    public List<SlaCalendarTypeDto> getCalendarTypeDtoListByCondition(SlaCalendarTypeSearchDto slaCalendarTypeSearchDto,
            Pageable pageable) {
        return slaCalendarTypeRepository.getCalendarTypeDtoListByCondition(slaCalendarTypeSearchDto, pageable).getContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#deleteById(java.lang.Long)
     */
    @Override
    @Transactional
    public boolean deleteById(Long calendarTypeId) throws DetailException {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = SlaConstant.SYSTEM_ID;
        boolean result = false;
        SlaCalendarType calendarType = this.getCalendarTypeById(calendarTypeId);
        if (null != calendarType) {
            calendarType.setDeletedDate(sysDate);
            calendarType.setDeletedId(userId);
            slaCalendarTypeRepository.update(calendarType);
            result = true;
        } else {
            logger.error("[SlaCalendarTypeServiceImpl] [deleteById] data not found, id: {} ", calendarTypeId);
            throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
        }

        return result;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaCalendarTypeService#getCalendarTypeDtoByOrgId(java.lang.Long)
     */
    @Override
    public SlaCalendarTypeDto getCalendarTypeDtoByOrgId(Long orgId) {
        SlaCalendarTypeDto calendarTypeDto = new SlaCalendarTypeDto();
        if (null != orgId) {
            calendarTypeDto = slaCalendarTypeRepository.getCalendarTypeDtoByOrgId(orgId);
        }
        return calendarTypeDto;
    }

}
