/*******************************************************************************
 * Class        ：SlaAlertHistoryServiceImpl
 * Created date ：2021/01/14
 * Lasted date  ：2021/01/14
 * Author       ：TrieuVD
 * Change log   ：2021/01/14：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.dto.SlaEmailAlertHistoryDto;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaEmailAlertHistory;
import vn.com.unit.sla.repository.SlaEmailAlertHistoryRepository;
import vn.com.unit.sla.service.SlaEmailAlertHistoryService;
import vn.com.unit.sla.service.SlaEmailAlertToHistoryService;

/**
 * SlaAlertHistoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaEmailAlertHistoryServiceImpl extends AbstractSlaService implements SlaEmailAlertHistoryService {

    @Autowired
    private SlaEmailAlertHistoryRepository slaEmailAlertHistoryRepository;

    @Autowired
    private SlaEmailAlertToHistoryService slaEmailAlertToHistoryService;
    
    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertHistoryService#saveSlaAlertHistoryDto(vn.com.unit.sla.dto.SlaAlertHistoryDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaEmailAlertHistoryDto createSlaEmailAlertHistoryDto(SlaEmailAlertHistoryDto slaAlertHistoryDto) throws DetailException {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        SlaEmailAlertHistory slaAlertHistory = mapper.convertValue(slaAlertHistoryDto, SlaEmailAlertHistory.class);
        slaAlertHistory.setCreatedDate(sysDate);
        slaAlertHistory.setCreatedId(SlaConstant.SYSTEM_ID);
        Long id = slaEmailAlertHistoryRepository.create(slaAlertHistory).getId();
        List<SlaReceiverDto> slaAccountDtoList = slaAlertHistoryDto.getSlaReceiverDtoList();
        if(CommonCollectionUtil.isNotEmpty(slaAccountDtoList)) {
            for (SlaReceiverDto slaAccountDto : slaAccountDtoList) {
                slaAccountDto.setAlertId(id);
                slaEmailAlertToHistoryService.createSlaEmailAlertToHistory(slaAccountDto);
            }
        }
        return slaAlertHistoryDto;
    }

    @Override
    public DbRepository<SlaEmailAlertHistory, Long> initRepo() {
        return slaEmailAlertHistoryRepository;
    }

}
