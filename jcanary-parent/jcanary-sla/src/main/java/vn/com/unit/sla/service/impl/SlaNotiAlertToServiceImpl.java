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
import vn.com.unit.sla.entity.SlaNotiAlertTo;
import vn.com.unit.sla.repository.SlaNotiAlertToRepository;
import vn.com.unit.sla.service.SlaNotiAlertToService;

/**
 * SlaAlertToServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SlaNotiAlertToServiceImpl extends AbstractSlaService implements SlaNotiAlertToService {
    
    @Autowired
    private SlaNotiAlertToRepository slaNotiAlertToRepository;

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertToService#getSlaAccountDtoListByAlertId(java.lang.Long, java.lang.String)
     */
    @Override
    public List<SlaReceiverDto> getSlaAccountDtoListByAlertId(Long alertId, String userType) {
        return slaNotiAlertToRepository.getSlaAccountDtoListByAlertId(alertId, userType);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertToService#createSlaAlertTo(vn.com.unit.sla.entity.SlaAlertTo)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SlaNotiAlertTo createSlaAlertTo(SlaReceiverDto slaAccountDto) {
        SlaNotiAlertTo slaAlertTo = new SlaNotiAlertTo();
        slaAlertTo.setSlaNotiAlertId(slaAccountDto.getAlertId());
        slaAlertTo.setReceiverId(slaAccountDto.getReceiverId());
        slaAlertTo.setReceiverType(slaAccountDto.getReceiverType());
        slaAlertTo.setCreatedId(CommonConstant.SYSTEM_ID);
        slaAlertTo.setCreatedDate(commonService.getSystemDate());
        slaNotiAlertToRepository.create(slaAlertTo);
        return slaAlertTo;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.sla.service.SlaAlertToService#deleteByAlertId(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByAlertId(Long alertId) {
        if(null != alertId) {
            slaNotiAlertToRepository.deleteByAlertId(alertId);
        }
    }

    @Override
    public DbRepository<SlaNotiAlertTo, Long> initRepo() {
        return slaNotiAlertToRepository;
    }

}
