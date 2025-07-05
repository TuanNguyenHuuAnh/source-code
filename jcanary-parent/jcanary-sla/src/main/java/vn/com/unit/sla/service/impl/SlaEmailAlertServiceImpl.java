/*******************************************************************************
 * Class        ：SlaAlertServiceImpl
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaEmailAlertDto;
import vn.com.unit.sla.dto.SlaEmailAlertHistoryDto;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaEmailAlert;
import vn.com.unit.sla.repository.SlaEmailAlertRepository;
import vn.com.unit.sla.service.SlaEmailAlertHistoryService;
import vn.com.unit.sla.service.SlaEmailAlertService;
import vn.com.unit.sla.service.SlaEmailAlertToService;

/**
 * SlaAlertServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaEmailAlertServiceImpl extends AbstractSlaService implements SlaEmailAlertService {

    @Autowired
    private SlaEmailAlertRepository slaAlertRepository;
    
    @Autowired
    private SlaEmailAlertHistoryService slaAlertHistoryService;

    @Autowired
    private SlaEmailAlertToService slaAlertToService;

    private static final Logger logger = LoggerFactory.getLogger(SlaEmailAlertServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertService#getSlaAlertDtoById(java.lang.Long)
     */
    @Override
    public SlaEmailAlertDto getSlaEmailAlertDtoById(Long id) {
        SlaEmailAlertDto slaAlertDto = new SlaEmailAlertDto();
        if (null != id) {
            slaAlertDto = slaAlertRepository.getSlaEmailAlertDtoById(id);
            List<SlaReceiverDto> receiverDtoList = slaAlertToService.getSlaReceiverDtoListByAlertId(id, null);
            slaAlertDto.setSlaReceiverDtoList(receiverDtoList);
        }
        return slaAlertDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertService#saveSlaAlert(vn.com.unit.sla.entity.SlaAlert)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaEmailAlert saveSlaEmailAlert(SlaEmailAlert slaAlert) throws DetailException {
        Long id = slaAlert.getId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = SlaConstant.SYSTEM_ID;
        if (null != id) {
            SlaEmailAlert oldSlaAlert = slaAlertRepository.findOne(id);
            if (null != oldSlaAlert) {
                slaAlert.setUpdatedDate(sysDate);
                slaAlert.setUpdatedId(userId);
                slaAlertRepository.update(slaAlert);
            } else {
                logger.error("[SlaAlertServiceImpl] [saveSlaAlert] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaAlert.setCreatedDate(sysDate);
            slaAlert.setCreatedId(userId);
            slaAlert.setUpdatedDate(sysDate);
            slaAlert.setUpdatedId(userId);
            slaAlertRepository.create(slaAlert);
        }
        return slaAlert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaEmailAlertDto saveSlaEmailAlertDto(SlaEmailAlertDto slaAlertDto) throws DetailException {
        SlaEmailAlert slaAlert = mapper.convertValue(slaAlertDto, SlaEmailAlert.class);
        Long id = this.saveSlaEmailAlert(slaAlert).getId();
        slaAlertDto.setId(id);
        List<SlaReceiverDto> accountList = slaAlertDto.getSlaReceiverDtoList();
        for (SlaReceiverDto slaAccountDto : accountList) {
            slaAccountDto.setAlertId(id);
            slaAlertToService.createSlaEmailAlertTo(slaAccountDto);
        }
        return slaAlertDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertService#findSlaAlertByCondition(java.util.Date, java.util.Date, java.lang.String)
     */
    @Override
    public List<SlaEmailAlertDto> getSlaEmailAlertDtoListByCondition(Date fromDate, Date toDate, Integer status) {
        return slaAlertRepository.getSlaEmailAlertDtoListByCondition(fromDate, toDate, status);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertService#updateStatusAndCountOfErrorByIdList(java.lang.String, boolean, java.util.List)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByIdList(Integer status, List<Long> idList) {
        if (CommonCollectionUtil.isNotEmpty(idList)) {
            int size = idList.size();
            while (size > 0) {
                if (size > 999) {
                    List<Long> list = idList.subList(0, 999);
                    slaAlertRepository.updateStatusByIdList(status, idList);
                    idList.removeAll(list);
                    size = idList.size();
                } else {
                    slaAlertRepository.updateStatusByIdList(status, idList);
                    size = 0;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertService#saveSlaAlertDtoList(java.util.List)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSlaEmailAlertDtoList(List<SlaEmailAlertDto> slaAlertDtoList) {
        try {
            for (SlaEmailAlertDto slaAlertDto : slaAlertDtoList) {
                this.saveSlaEmailAlertDto(slaAlertDto);
            }
        } catch (Exception e) {
            logger.error("[SlaAlertServiceImpl] [saveSlaAlertDtoList] error: ", e);
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertService#moveSlaAlertToSlaAlertHistory(vn.com.unit.sla.dto.SlaAlertDto)
     */
    @Override
    public void moveSlaAlertToSlaAlertHistory(SlaEmailAlertDto slaAlertDto, Integer status, String responseJson){
        SlaEmailAlertHistoryDto slaAlertHistoryDto = mapper.convertValue(slaAlertDto, SlaEmailAlertHistoryDto.class);
        slaAlertHistoryDto.setStatus(status);
        slaAlertHistoryDto.setResponseJson(responseJson);
        try {
            slaAlertHistoryService.createSlaEmailAlertHistoryDto(slaAlertHistoryDto);
        } catch (DetailException e) {
            logger.error("[SlaAlertServiceImpl] [moveSlaAlertToSlaAlertHistory] error: ", e);
        }
        Long slaAlertId = slaAlertDto.getId();
        if(null != slaAlertId) {
            slaAlertRepository.delete(slaAlertId);
            slaAlertToService.deleteByAlertId(slaAlertId);
        }
    }

    @Override
    public void moveSlaEmailAlertToSlaEmailAlertHistoryById(Long slaAlertId, Integer status, String responseJson) {
        SlaEmailAlertDto slaEmailAlertDto = this.getSlaEmailAlertDtoById(slaAlertId);
        if(null != slaEmailAlertDto) {
            this.moveSlaAlertToSlaAlertHistory(slaEmailAlertDto, status, responseJson);
        }
    }
    
    @Override
    public DbRepository<SlaEmailAlert, Long> initRepo() {
        return slaAlertRepository;
    }

}
