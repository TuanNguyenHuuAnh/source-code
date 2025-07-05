/*******************************************************************************
 * Class        ：JpmProcessInstActServiceImpl
 * Created date ：2021/03/05
 * Lasted date  ：2021/03/05
 * Author       ：tantm
 * Change log   ：2021/03/05：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.activiti.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.entity.JpmProcessInstAct;
import vn.com.unit.workflow.activiti.repository.JpmProcessInstActRepository;
import vn.com.unit.workflow.activiti.service.JpmProcessInstActService;

/**
 * JpmProcessInstActServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmProcessInstActServiceImpl implements JpmProcessInstActService {
    
    @Autowired
    private JpmProcessInstActRepository jpmProcessInstActRepository;
    
    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public DbRepository<JpmProcessInstAct, Long> initRepo() {
        return jpmProcessInstActRepository;
    }

    @Transactional
    @Override
    public JpmProcessInstAct saveJpmProcessInstAct(JpmProcessInstAct en) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = commonService.getSystemDate();
        Long id = en.getId();
        if(id == null ) {
            en.setCreatedId(userId);
            en.setCreatedDate(sysDate);
            en.setUpdatedId(userId);
            en.setUpdatedDate(sysDate);
            jpmProcessInstActRepository.create(en);
        } else {
            JpmProcessInstAct old = jpmProcessInstActRepository.findOne(id);
            en.setCreatedId(old.getCreatedId());
            en.setCreatedDate(old.getCreatedDate());
            en.setUpdatedId(userId);
            en.setUpdatedDate(sysDate);
            jpmProcessInstActRepository.update(en);
        }
        return en;
    }

    @Transactional
    @Override
    public JpmProcessInstAct saveJpmProcessInstActDto(JpmProcessInstActDto dto) {
        JpmProcessInstAct en = objectMapper.convertValue(dto, JpmProcessInstAct.class);
        return this.saveJpmProcessInstAct(en);
    }

    @Transactional
    @Override
    public int deleteJpmProcessInstActById(Long id) {
        return jpmProcessInstActRepository.deleteJpmProcessInstActById(id);
    }

    @Override
    public JpmProcessInstActDto getJpmProcessInstActDtoByReference(Long refId, Integer refType) {
        return jpmProcessInstActRepository.getJpmProcessInstActDtoByReference(refId, refType);
    }

}
