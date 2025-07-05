/*******************************************************************************
 * Class        ：JcaSlaReceiverServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.sla.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.sla.dto.SlaReceiverDto;
import vn.com.unit.sla.service.SlaReceiverService;
import vn.com.unit.workflow.service.JpmTaskAssigneeService;

/**
 * <p>
 * JcaSlaReceiverServiceImpl
 * </p>
 * 
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaSlaReceiverServiceImpl implements SlaReceiverService {
    
    /** The jca account service. */
    @Autowired
    private JcaAccountService jcaAccountService;
    
    @Autowired
    private JpmTaskAssigneeService jpmTaskAssigneeService;
    
    @Override
    public List<SlaReceiverDto> getSlaReceiverDtoListByInvoledType(Long involedType, Long businessKey) {
        List<SlaReceiverDto> resultList = new ArrayList<>();
        List<Long> accountIdList = new ArrayList<>();
        if(null != businessKey) {
            accountIdList = jpmTaskAssigneeService.getListAccountByTaskIdAndType(businessKey, involedType);
        }
        if(CommonCollectionUtil.isNotEmpty(accountIdList)) {
            resultList = accountIdList.stream().map(f -> new SlaReceiverDto(f)).collect(Collectors.toList());
        }
        return resultList;
    }

    @Override
    public List<String> getListEmailByListReceiverId(List<Long> receiverIdList) {
        return jcaAccountService.getListEmailByAccountId(receiverIdList);
    }

}
