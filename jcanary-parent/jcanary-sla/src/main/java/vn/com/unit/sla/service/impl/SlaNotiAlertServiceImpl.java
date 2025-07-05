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
import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.constant.SlaExceptionCodeConstant;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.dto.SlaNotiAlertDto;
import vn.com.unit.sla.dto.SlaNotiAlertHistoryDto;
import vn.com.unit.sla.entity.SlaNotiAlert;
import vn.com.unit.sla.repository.SlaNotiAlertRepository;
import vn.com.unit.sla.service.SlaNotiAlertHistoryService;
import vn.com.unit.sla.service.SlaNotiAlertService;
import vn.com.unit.sla.service.SlaNotiAlertToService;

/**
 * SlaAlertServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaNotiAlertServiceImpl extends AbstractSlaService implements SlaNotiAlertService, DbRepositoryService<SlaNotiAlert, Long> {

    @Autowired
    private SlaNotiAlertRepository slaNotiAlertRepository;
    
    @Autowired
    private SlaNotiAlertHistoryService slaAlertHistoryService;

    @Autowired
    private SlaNotiAlertToService slaAlertToService;

    private static final Logger logger = LoggerFactory.getLogger(SlaNotiAlertServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertService#getSlaAlertDtoById(java.lang.Long)
     */
    @Override
    public SlaNotiAlertDto getSlaNotiAlertDtoById(Long id) {
        SlaNotiAlertDto slaAlertDto = new SlaNotiAlertDto();
        if (null != id) {
            slaAlertDto = slaNotiAlertRepository.getSlaNotiAlertDtoById(id);
            List<SlaReceiverDto> accountList = slaAlertToService.getSlaAccountDtoListByAlertId(id, null);
            slaAlertDto.setSlaReceiverDtoList(accountList);
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
    public SlaNotiAlert saveSlaNotiAlert(SlaNotiAlert slaAlert) throws DetailException {
        Long id = slaAlert.getId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = SlaConstant.SYSTEM_ID;
        if (null != id) {
            SlaNotiAlert oldSlaAlert = slaNotiAlertRepository.findOne(id);
            if (null != oldSlaAlert) {
                slaAlert.setUpdatedDate(sysDate);
                slaAlert.setUpdatedId(userId);
                slaNotiAlertRepository.update(slaAlert);
            } else {
                logger.error("[SlaAlertServiceImpl] [saveSlaAlert] data not found, id: {}", id);
                throw new DetailException(SlaExceptionCodeConstant.E201702_SLA_DATA_NOT_FOUND_ERROR, true);
            }
        } else {
            slaAlert.setCreatedDate(sysDate);
            slaAlert.setCreatedId(userId);
            slaAlert.setUpdatedDate(sysDate);
            slaAlert.setUpdatedId(userId);
            slaNotiAlertRepository.create(slaAlert);
        }
        return slaAlert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaNotiAlertDto saveSlaNotiAlertDto(SlaNotiAlertDto slaAlertDto) throws DetailException {
        SlaNotiAlert slaAlert = mapper.convertValue(slaAlertDto, SlaNotiAlert.class);
        Long id = this.saveSlaNotiAlert(slaAlert).getId();
        List<SlaReceiverDto> accountList = slaAlertDto.getSlaReceiverDtoList();
        for (SlaReceiverDto slaAccountDto : accountList) {
            slaAccountDto.setAlertId(id);
            slaAlertToService.createSlaAlertTo(slaAccountDto);
        }
        return slaAlertDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertService#findSlaAlertByCondition(java.util.Date, java.util.Date, java.lang.String)
     */
    @Override
    public List<SlaNotiAlertDto> getSlaNotiAlertDtoListByCondition(Date fromDate, Date toDate, Integer status) {
        return slaNotiAlertRepository.getSlaNotiAlertDtoListByCondition(fromDate, toDate, status);
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
                    slaNotiAlertRepository.updateStatusByIdList(status, idList);
                    idList.removeAll(list);
                    size = idList.size();
                } else {
                    slaNotiAlertRepository.updateStatusByIdList(status, idList);
                    size = 0;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertService#saveSlaAlertDtoList(java.util.List)
     */
    @Override
    @Transactional
    public void saveSlaNotiAlertDtoList(List<SlaNotiAlertDto> slaAlertDtoList) {
        try {
            for (SlaNotiAlertDto slaAlertDto : slaAlertDtoList) {
                this.saveSlaNotiAlertDto(slaAlertDto);
            }
        } catch (Exception e) {
            logger.error("[SlaAlertServiceImpl] [saveSlaAlertDtoList] error: ", e);
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertService#moveSlaAlertToSlaAlertHistory(vn.com.unit.sla.dto.SlaAlertDto)
     */
    @Override
    @Transactional
    public void moveSlaNotiAlertToSlaNotiAlertHistory(SlaNotiAlertDto slaAlertDto, Integer status, String responseJson){
        SlaNotiAlertHistoryDto slaAlertHistoryDto = mapper.convertValue(slaAlertDto, SlaNotiAlertHistoryDto.class);
        slaAlertHistoryDto.setStatus(status);
        slaAlertHistoryDto.setResponseJson(responseJson);
        try {
            slaAlertHistoryService.createSlaAlertHistoryDto(slaAlertHistoryDto);
        } catch (DetailException e) {
            logger.error("[SlaAlertServiceImpl] [moveSlaAlertToSlaAlertHistory] error: ", e);
        }
        Long slaAlertId = slaAlertDto.getId();
        if(null != slaAlertId) {
            slaNotiAlertRepository.delete(slaAlertId);
            slaAlertToService.deleteByAlertId(slaAlertId);
        }
    }
    
    @Override
    @Transactional
    public void moveSlaNotiAlertToSlaNotiAlertHistoryById(Long slaAlertId, Integer status, String responseJson) {
        SlaNotiAlertDto slaNotiAlertDto = this.getSlaNotiAlertDtoById(slaAlertId);
        if(null != slaNotiAlertDto) {
            this.moveSlaNotiAlertToSlaNotiAlertHistory(slaNotiAlertDto, status, responseJson);
        }
    }

    @Override
    public DbRepository<SlaNotiAlert, Long> initRepo() {
        return slaNotiAlertRepository;
    }

}
