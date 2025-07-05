/*******************************************************************************
 * Class        ：EfoOzDocActionEventServiceImpl
 * Created date ：2020/12/04
 * Lasted date  ：2020/12/04
 * Author       ：tantm
 * Change log   ：2020/12/04：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonMapUtil;
import vn.com.unit.core.service.HsmEfoOzDocActionEventService;
import vn.com.unit.core.workflow.dto.DocumentAction;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocActionEventService;

/**
 * EfoOzDocActionEventServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoOzDocActionEventServiceImpl implements EfoOzDocActionEventService {

    @Override
    public void processDataBeforeSave(EfoDocDto objectDto) {
        // TODO Auto-generated method stub
    }

    @Override
    public void processDataBeforeSave(EfoDoc entity, EfoDocDto objectDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processDataAfterSave(EfoDocDto objectDto, DocumentAction documentAction) {
        if (CommonMapUtil.isNotEmpty(documentAction.getActions())) {
            // Check exists HSM
            HsmEfoOzDocActionEventService hsmEfoOzDocActionEventService = (HsmEfoOzDocActionEventService) documentAction.getAction("HSM");
            if (hsmEfoOzDocActionEventService != null) {
                // Do something as execute HSM
            }
        }
    }

    @Override
    public void processDataAfterSave(EfoDoc entity, EfoDocDto objectDto) {
        // TODO Auto-generated method stub

    }
}
