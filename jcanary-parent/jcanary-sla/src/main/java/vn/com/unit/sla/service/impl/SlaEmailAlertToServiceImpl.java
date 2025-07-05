/*******************************************************************************
 * Class        ：SlaAlertToServiceImpl
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：TrieuVD
 * Change log   ：2021/01/20：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.entity.SlaEmailAlertTo;
import vn.com.unit.sla.repository.SlaEmailAlertToRepository;
import vn.com.unit.sla.service.SlaEmailAlertToService;

/**
 * SlaAlertToServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaEmailAlertToServiceImpl extends AbstractSlaService implements SlaEmailAlertToService {
    
    @Autowired
    private SlaEmailAlertToRepository slaEmailAlertToRepository;

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertToService#getSlaReceiverDtoListByAlertId(java.lang.Long, java.lang.String)
     */
    @Override
    public List<SlaReceiverDto> getSlaReceiverDtoListByAlertId(Long alertId, String userType) {
        return slaEmailAlertToRepository.getSlaReceiverDtoListByAlertId(alertId, userType);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertToService#createSlaAlertTo(vn.com.unit.sla.entity.SlaAlertTo)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaEmailAlertTo createSlaEmailAlertTo(SlaReceiverDto slaReceiverDto) {
        SlaEmailAlertTo slaAlertTo = new SlaEmailAlertTo();
        slaAlertTo.setSlaEmailAlertId(slaReceiverDto.getAlertId());
        slaAlertTo.setReceiverId(slaReceiverDto.getReceiverId());
        slaAlertTo.setReceiverType(slaReceiverDto.getReceiverType());
        slaAlertTo.setCreatedId(CommonConstant.SYSTEM_ID);
        slaAlertTo.setCreatedDate(commonService.getSystemDate());
        slaEmailAlertToRepository.create(slaAlertTo);
        return slaAlertTo;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertToService#deleteByAlertId(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByAlertId(Long alertId) {
        if(null != alertId) {
            slaEmailAlertToRepository.deleteByAlertId(alertId);
        }
    }

    @Override
    public DbRepository<SlaEmailAlertTo, Long> initRepo() {
        return slaEmailAlertToRepository;
    }

}
