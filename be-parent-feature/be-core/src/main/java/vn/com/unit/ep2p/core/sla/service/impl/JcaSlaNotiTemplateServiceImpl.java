/*******************************************************************************
 * Class        ：JcaSlaTemplateServiceImpl
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：khadm
 * Change log   ：2021/01/19：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.core.sla.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.sla.noti.dto.SlaNotiTemplateDto;
import vn.com.unit.sla.noti.service.SlaNotiTemplateService;

/**
 * JcaSlaTemplateServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author khadm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaSlaNotiTemplateServiceImpl implements SlaNotiTemplateService {
    
    @Override
    public SlaNotiTemplateDto getSlaNotiTemplateDtoBytemplateId(Long templateId, Map<String, String> mapData) {
        //TODO TRIEUVD
        return null;
    }
    
}
