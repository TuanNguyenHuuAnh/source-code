/*******************************************************************************
 * Class        ：SlaAlertToHistoryServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：TrieuVD
 * Change log   ：2021/01/20：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.constant.SlaConstant;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaNotiAlertToHistory;
import vn.com.unit.sla.repository.SlaNotiAlertToHistoryRepository;
import vn.com.unit.sla.service.SlaNotiAlertToHistoryService;

/**
 * SlaAlertToHistoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaNotiAlertToHistoryServiceImpl extends AbstractSlaService implements SlaNotiAlertToHistoryService {

    @Autowired
    private SlaNotiAlertToHistoryRepository slaNotiAlertToHistoryRepository;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertToHistoryService#createSlaAlertToHistory(vn.com.unit.sla.dto.SlaAccountDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaNotiAlertToHistory createSlaAlertToHistory(SlaReceiverDto slaAccountDto) {
        SlaNotiAlertToHistory slaAlertToHistory = new SlaNotiAlertToHistory();
        slaAlertToHistory.setSlaNotiAlertHistoryId(slaAccountDto.getAlertId());
        slaAlertToHistory.setReceiverId(slaAccountDto.getReceiverId());
        slaAlertToHistory.setReceiverType(slaAccountDto.getReceiverType());
        slaAlertToHistory.setCreatedId(SlaConstant.SYSTEM_ID);
        slaAlertToHistory.setCreatedDate(commonService.getSystemDate());
        return slaNotiAlertToHistoryRepository.create(slaAlertToHistory);
    }

    @Override
    public DbRepository<SlaNotiAlertToHistory, Long> initRepo() {
        return slaNotiAlertToHistoryRepository;
    }

}
