/*******************************************************************************
 * Class        ：AppParamConfigStepServiceImpl
 * Created date ：2019/11/29
 * Lasted date  ：2019/11/29
 * Author       ：KhuongTH
 * Change log   ：2019/11/29：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.process.workflow.repository.AppParamConfigStepRepository;
import vn.com.unit.process.workflow.service.AppParamConfigStepService;
import vn.com.unit.workflow.dto.JpmParamConfigDto;

/**
 * AppParamConfigStepServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppParamConfigStepServiceImpl implements AppParamConfigStepService {

    @Autowired
    private AppParamConfigStepRepository appParamConfigStepRepository;

    @Override
    public List<JpmParamConfigDto> getConfigsByParamId(Long paramId, Long processId) {
        return appParamConfigStepRepository.getConfigsByParamId(paramId, processId);
    }

    @Override
    public void deleteByParamId(Long paramId) {
        String user = UserProfileUtils.getUserNameLogin();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        appParamConfigStepRepository.deleteByParamId(paramId, user, sysDate);
    }

}
