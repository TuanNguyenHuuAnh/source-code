/*******************************************************************************
 * Class        ：JpmTaskEmailAlertServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.entity.JpmTaskEmailAlert;
import vn.com.unit.workflow.repository.JpmTaskEmailAlertRepository;
import vn.com.unit.workflow.service.JpmTaskEmailAlertService;

/**
 * <p>
 * JpmTaskEmailAlertServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmTaskEmailAlertServiceImpl implements JpmTaskEmailAlertService {
    
    @Autowired
    private JpmTaskEmailAlertRepository jpmTaskEmailAlertRepository;
    
    @Autowired
    private JCommonService commonService;

    @Override
    public DbRepository<JpmTaskEmailAlert, Long> initRepo() {
        return jpmTaskEmailAlertRepository;
    }

    @Override
    public List<JpmTaskEmailAlert> getJpmTaskEmailAlertListByJpmTaskId(Long jpmTaskId) {
        return jpmTaskEmailAlertRepository.getJpmTaskEmailAlertListByJpmTaskId(jpmTaskId);
    }

    @Override
    public void createJpmTaskEmailAlertByListAlertId(Long jpmTaskId, List<Long> alertIdList) {
        Long userId = CommonConstant.SYSTEM_ID;
        Date sysDate = commonService.getSystemDate();
        if(CommonCollectionUtil.isNotEmpty(alertIdList)) {
            for (Long slaAlertId : alertIdList) {
                JpmTaskEmailAlert jpmTaskEmailAlert = new JpmTaskEmailAlert();
                jpmTaskEmailAlert.setJpmTaskId(jpmTaskId);
                jpmTaskEmailAlert.setSlaEmailAlertId(slaAlertId);
                jpmTaskEmailAlert.setCreatedId(userId);
                jpmTaskEmailAlert.setCreatedDate(sysDate);
                jpmTaskEmailAlertRepository.create(jpmTaskEmailAlert);
            }
        }
    }

}
