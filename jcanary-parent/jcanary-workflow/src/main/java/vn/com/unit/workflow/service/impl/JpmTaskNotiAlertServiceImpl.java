/*******************************************************************************
 * Class        ：JpmTaskNotiAlertServiceImpl
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
import vn.com.unit.workflow.entity.JpmTaskNotiAlert;
import vn.com.unit.workflow.repository.JpmTaskNotiAlertRepository;
import vn.com.unit.workflow.service.JpmTaskNotiAlertService;

/**
 * <p>
 * JpmTaskNotiAlertServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmTaskNotiAlertServiceImpl implements JpmTaskNotiAlertService {
    
    @Autowired
    private JpmTaskNotiAlertRepository jpmTaskNotiAlertRepository;
    
    @Autowired
    private JCommonService commonService;

    @Override
    public DbRepository<JpmTaskNotiAlert, Long> initRepo() {
        return jpmTaskNotiAlertRepository;
    }

    @Override
    public List<JpmTaskNotiAlert> getJpmTaskNotiAlertListByJpmTaskId(Long jpmTaskId) {
        return jpmTaskNotiAlertRepository.getJpmTaskNotiAlertListByJpmTaskId(jpmTaskId);
    }

    @Override
    public void createJpmTaskNotiAlertByListAlertId(Long jpmTaskId, List<Long> alertIdList) {
        Long userId = CommonConstant.SYSTEM_ID;
        Date sysDate = commonService.getSystemDate();
        if(CommonCollectionUtil.isNotEmpty(alertIdList)) {
            for (Long slaNotiAlertId : alertIdList) {
                JpmTaskNotiAlert jpmTaskNotiAlert = new JpmTaskNotiAlert();
                jpmTaskNotiAlert.setJpmTaskId(jpmTaskId);
                jpmTaskNotiAlert.setSlaNotiAlertId(slaNotiAlertId);
                jpmTaskNotiAlert.setCreatedId(userId);
                jpmTaskNotiAlert.setCreatedDate(sysDate);
                jpmTaskNotiAlertRepository.create(jpmTaskNotiAlert);
            }
        }
    }

}
