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
import vn.com.unit.sla.entity.SlaEmailAlertToHistory;
import vn.com.unit.sla.repository.SlaEmailAlertToHistoryRepository;
import vn.com.unit.sla.service.SlaEmailAlertToHistoryService;

/**
 * SlaAlertToHistoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaEmailAlertToHistoryServiceImpl extends AbstractSlaService implements SlaEmailAlertToHistoryService {

    @Autowired
    private SlaEmailAlertToHistoryRepository slaEmailAlertToHistoryRepository;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.sla.service.SlaAlertToHistoryService#createSlaAlertToHistory(vn.com.unit.sla.dto.SlaAccountDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaEmailAlertToHistory createSlaEmailAlertToHistory(SlaReceiverDto slaAccountDto) {
        SlaEmailAlertToHistory slaAlertToHistory = new SlaEmailAlertToHistory();
        slaAlertToHistory.setSlaEmailAlertHistoryId(slaAccountDto.getAlertId());
        slaAlertToHistory.setReceiverId(slaAccountDto.getReceiverId());
        slaAlertToHistory.setReceiverType(slaAccountDto.getReceiverType());
        slaAlertToHistory.setCreatedId(SlaConstant.SYSTEM_ID);
        slaAlertToHistory.setCreatedDate(commonService.getSystemDate());
        return slaEmailAlertToHistoryRepository.create(slaAlertToHistory);
    }

    @Override
    public DbRepository<SlaEmailAlertToHistory, Long> initRepo() {
        return slaEmailAlertToHistoryRepository;
    }

}
