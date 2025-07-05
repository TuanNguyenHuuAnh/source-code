/*******************************************************************************
 * Class        ：SlaConfigDetailServiceImpl
 * Created date ：2020/11/11
 * Lasted date  ：2020/11/11
 * Author       ：TrieuVD
 * Change log   ：2020/11/11：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaConfigAlertToDto;
import vn.com.unit.sla.dto.SlaConfigDetailDto;
import vn.com.unit.sla.dto.SlaConfigDetailSearchDto;
import vn.com.unit.sla.entity.SlaConfigDetail;
import vn.com.unit.sla.repository.SlaConfigDetailRepository;
import vn.com.unit.sla.service.SlaConfigAlertToService;
import vn.com.unit.sla.service.SlaConfigDetailService;

/**
 * SlaConfigDetailServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaConfigDetailServiceImpl extends AbstractSlaService implements SlaConfigDetailService {

    @Autowired
    private SlaConfigDetailRepository slaConfigDetailRepository;

    @Autowired
    private SlaConfigAlertToService slaConfigAlertToService;

    private static final Logger logger = LoggerFactory.getLogger(SlaConfigDetailServiceImpl.class);

    @Override
    public SlaConfigDetailDto getSlaConfigDetailDtoById(Long id) throws DetailException {
        if (null == id) {
            logger.error("[SlaConfigDetailServiceImpl] [getSlaConfigDetailDtoById] id is null");
            throw new DetailException(SlaExceptionCodeConstant.E201701_SLA_VALIDATE_REQUIRED_ERROR, new String[] { "id" }, true);
        } else {
            SlaConfigDetailDto configDetailDto = slaConfigDetailRepository.getSlaConfigDetailDtoById(id);
            if (null != configDetailDto) {
                List<SlaConfigAlertToDto> alertToList = slaConfigAlertToService.getListByConfigDetailId(id);
                configDetailDto.setAlertToList(alertToList);
            }
            return configDetailDto;
        }
    }

    @Override
    public List<SlaConfigDetailDto> getSlaConfigDetailDtoListByCondition(SlaConfigDetailSearchDto searchDto, Pageable pageable) {
        List<SlaConfigDetailDto> slaConfigDetailDtos = slaConfigDetailRepository.getSlaConfigDetailDtoListByCondition(searchDto, pageable)
                .getContent();
        getAlertToList(slaConfigDetailDtos);
        return slaConfigDetailDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        if (null != id) {
            SlaConfigDetail slaConfigDetail = slaConfigDetailRepository.findOne(id);
            if (null != slaConfigDetail) {
                Long userId = UserProfileUtils.getAccountId();
                slaConfigDetail.setDeletedId(userId);
                slaConfigDetail.setDeletedDate(new Date());
                slaConfigDetailRepository.update(slaConfigDetail);
                return true;
            }
        }
        return false;

    }

    @Override
    public int countByCondition(SlaConfigDetailSearchDto searchDto) {
        return slaConfigDetailRepository.countByCondition(searchDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cloneSlaConfigDetailsBySlaConfigId(Long oldSlaConfigId, Long newSlaConfigId) throws DetailException {
        List<SlaConfigDetailDto> oldSlaConfigDetailDtos = this.getSlaConfigDetailDtoBySlaConfigId(oldSlaConfigId);
        if (CommonCollectionUtil.isNotEmpty(oldSlaConfigDetailDtos)) {
            for (SlaConfigDetailDto oldSlaConfigDetailDto : oldSlaConfigDetailDtos) {
                this.cloneSlaConfigDetailBySlaConfigId(newSlaConfigId, oldSlaConfigDetailDto);
            }
        }
    }

    private SlaConfigDetailDto cloneSlaConfigDetailBySlaConfigId(Long slaConfigId, SlaConfigDetailDto oldSlaConfigDetailDto)
            throws DetailException {
        Long oldSlaConfigDetailId = oldSlaConfigDetailDto.getId();
        oldSlaConfigDetailDto.setSlaConfigId(slaConfigId);
        oldSlaConfigDetailDto.setId(null);
        SlaConfigDetailDto newSlaConfigDetailDto = this.saveSlaConfigDetailDto(oldSlaConfigDetailDto);
        Long newSlaConfigDetailId = newSlaConfigDetailDto.getId();
        // clone sla config alert
        slaConfigAlertToService.cloneSlaConfigAlertTosBySlaConfigDetailId(oldSlaConfigDetailId, newSlaConfigDetailId);
        return newSlaConfigDetailDto;
    }

    @Override
    public List<SlaConfigDetailDto> getSlaConfigDetailDtoBySlaConfigId(Long slaConfigId) {
        return slaConfigDetailRepository.getSlaConfigDetailDtoBySlaConfigId(slaConfigId);
    }

    @Override
    public List<SlaConfigDetailDto> findAllByConfigId(Long slaConfigId) {
        List<SlaConfigDetailDto> slaConfigDetailDtos = new ArrayList<>();
        if (null != slaConfigId) {
            slaConfigDetailDtos = slaConfigDetailRepository.findAllByConfigId(slaConfigId);
            getAlertToList(slaConfigDetailDtos);
        }
        return slaConfigDetailDtos;
    }

    private void getAlertToList(List<SlaConfigDetailDto> slaConfigDetailDtos) {
        if (null != slaConfigDetailDtos && !slaConfigDetailDtos.isEmpty()) {
            slaConfigDetailDtos.stream().map(slaConfigDetailDto -> {
                List<SlaConfigAlertToDto> alertToList = slaConfigAlertToService.getListByConfigDetailId(slaConfigDetailDto.getId());
                slaConfigDetailDto.setAlertToList(alertToList);
                return slaConfigDetailDto;
            }).collect(Collectors.toList());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaConfigDetailService#saveSlaConfigDetail(vn.com.unit.sla.entity.SlaConfigDetail)
     */
    @Override
    public SlaConfigDetail saveSlaConfigDetail(SlaConfigDetail slaConfigDetail) throws DetailException {
        Long id = slaConfigDetail.getId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getAccountId();
        if (null != id) {
            SlaConfigDetail oldSlaConfigDetail = slaConfigDetailRepository.findOne(id);
            if (null != oldSlaConfigDetail) {
                CommonObjectUtil.copyPropertiesNonNull(slaConfigDetail, oldSlaConfigDetail);
                oldSlaConfigDetail.setUpdatedDate(sysDate);
                oldSlaConfigDetail.setUpdatedId(userId);
                slaConfigDetail = slaConfigDetailRepository.update(oldSlaConfigDetail);
            } else {
                logger.error("[SlaCalendarTypeServiceImpl] [saveSlaCalendarType] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaConfigDetail.setCreatedDate(sysDate);
            slaConfigDetail.setCreatedId(userId);
            slaConfigDetail.setUpdatedDate(sysDate);
            slaConfigDetail.setUpdatedId(userId);
            slaConfigDetailRepository.create(slaConfigDetail);
        }
        return slaConfigDetail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaConfigDetailService#saveSlaConfigDetailDto(vn.com.unit.sla.dto.SlaConfigDetailDto)
     */
    @Override
    public SlaConfigDetailDto saveSlaConfigDetailDto(SlaConfigDetailDto slaConfigDetailDto) throws DetailException {
        SlaConfigDetail slaConfigDetail = mapper.convertValue(slaConfigDetailDto, SlaConfigDetail.class);
        Long slaConfigDetailId = this.saveSlaConfigDetail(slaConfigDetail).getId();

        slaConfigAlertToService.deleteByConfigDetailId(slaConfigDetailId);
        List<SlaConfigAlertToDto> alertToDtoList = slaConfigDetailDto.getAlertToList();
        if (CommonCollectionUtil.isNotEmpty(alertToDtoList)) {
            for (SlaConfigAlertToDto slaConfigAlertToDto : alertToDtoList) {
                slaConfigAlertToDto.setSlaConfigDetailId(slaConfigDetailId);
                slaConfigAlertToService.saveSlaConfigAlertToDto(slaConfigAlertToDto);
            }
        }

        slaConfigDetailDto.setId(slaConfigDetailId);
        return slaConfigDetailDto;
    }

    @Override
    public DbRepository<SlaConfigDetail, Long> initRepo() {
        return slaConfigDetailRepository;
    }

    @Override
    public void deleteBySlaConfigId(Long slaConfigId) {
        // TODO Auto-generated method stub
        
    }
}
