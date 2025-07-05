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
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.dto.SlaNotiAlertHistoryDto;
import vn.com.unit.sla.entity.SlaNotiAlertHistory;
import vn.com.unit.sla.repository.SlaNotiAlertHistoryRepository;
import vn.com.unit.sla.service.SlaNotiAlertHistoryService;
import vn.com.unit.sla.service.SlaNotiAlertToHistoryService;

/**
 * SlaAlertHistoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaNotiAlertHistoryServiceImpl extends AbstractSlaService implements SlaNotiAlertHistoryService {

    @Autowired
    private SlaNotiAlertHistoryRepository slaNotiAlertHistoryRepository;

    @Autowired
    private SlaNotiAlertToHistoryService slaNotiAlertToHistoryService;
    
    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertHistoryService#saveSlaAlertHistoryDto(vn.com.unit.sla.dto.SlaAlertHistoryDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaNotiAlertHistoryDto createSlaAlertHistoryDto(SlaNotiAlertHistoryDto slaAlertHistoryDto) throws DetailException {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        SlaNotiAlertHistory slaAlertHistory = mapper.convertValue(slaAlertHistoryDto, SlaNotiAlertHistory.class);
        slaAlertHistory.setCreatedDate(sysDate);
        slaAlertHistory.setCreatedId(SlaConstant.SYSTEM_ID);
        Long id = slaNotiAlertHistoryRepository.create(slaAlertHistory).getId();
        List<SlaReceiverDto> slaAccountDtoList = slaAlertHistoryDto.getSlaReceiverDtoList();
        if(CommonCollectionUtil.isNotEmpty(slaAccountDtoList)) {
            for (SlaReceiverDto slaAccountDto : slaAccountDtoList) {
                slaAccountDto.setAlertId(id);
                slaNotiAlertToHistoryService.createSlaAlertToHistory(slaAccountDto);
            }
        }
        return slaAlertHistoryDto;
    }

    @Override
    public DbRepository<SlaNotiAlertHistory, Long> initRepo() {
        return slaNotiAlertHistoryRepository;
    }

}
