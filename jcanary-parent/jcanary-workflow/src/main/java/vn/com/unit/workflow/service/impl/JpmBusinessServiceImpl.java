/*******************************************************************************
* Class        JpmBusinessServiceImpl
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.workflow.dto.JpmBusinessDto;
import vn.com.unit.workflow.dto.JpmBusinessSearchDto;
import vn.com.unit.workflow.dto.JpmProcessDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.entity.JpmBusiness;
import vn.com.unit.workflow.repository.JpmBusinessRepository;
import vn.com.unit.workflow.service.JpmBusinessService;
import vn.com.unit.workflow.service.JpmHiBusinessService;
import vn.com.unit.workflow.service.JpmProcessDeployService;
import vn.com.unit.workflow.service.JpmProcessService;

/**
 * JpmBusinessServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JpmBusinessServiceImpl implements JpmBusinessService {

    @Autowired
    private JpmBusinessRepository jpmBusinessRepository;
    
    @Autowired
    private JpmProcessDeployService jpmProcessDeployService;
    
    @Autowired
    private JpmProcessService jpmProcessService;
    
    @Autowired
    private JpmHiBusinessService jpmHiBusinessService;

    // Object mapper
    @Autowired
    protected ObjectMapper objectMapper;

    @Override
    public JpmBusinessDto getJpmBusinessDtoById(Long id) {
        JpmBusinessDto jpmBusinessDto = new JpmBusinessDto();
        if (Objects.nonNull(id)) {
            JpmBusiness jpmBusiness = jpmBusinessRepository.findOne(id);
            if (Objects.nonNull(jpmBusiness) && jpmBusiness.getDeletedId() == 0) {
                jpmBusinessDto = objectMapper.convertValue(jpmBusiness, JpmBusinessDto.class);
                jpmBusinessDto.setBusinessId(id);
            }
        }
        return jpmBusinessDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        boolean res = false;
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if (Objects.nonNull(id)) {
            JpmBusiness jpmBusiness = jpmBusinessRepository.findOne(id);

            // Tìm các process deploy có sử dụng bussiness
            JpmProcessDeployDto deploy = jpmProcessDeployService
                    .getJpmProcessDeployLasted(UserProfileUtils.getCompanyId(), id);

            JpmProcessDto process = jpmProcessService.getJpmProcessDtoByBusinessId(id, UserProfileUtils.getCompanyId());

            if (deploy == null && process == null) {
                if (Objects.nonNull(jpmBusiness) && Long.valueOf(0L).equals(jpmBusiness.getDeletedId())) {
                    jpmBusiness.setDeletedId(userId);
                    jpmBusiness.setDeletedDate(sysDate);
                    jpmBusinessRepository.update(jpmBusiness);
                    res = true;
                }
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmBusiness saveJpmBusiness(JpmBusiness jpmBusiness) {
        Long userId = UserProfileUtils.getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jpmBusiness.getId();
        if (null != id) {
            JpmBusiness oldJpmBusiness = jpmBusinessRepository.findOne(id);
            if (null != oldJpmBusiness) {
                jpmBusiness.setCreatedId(oldJpmBusiness.getCreatedId());
                jpmBusiness.setCreatedDate(oldJpmBusiness.getCreatedDate());
                jpmBusiness.setUpdatedId(userId);
                jpmBusiness.setUpdatedDate(sysDate);
                jpmBusinessRepository.update(jpmBusiness);
            }
        } else {
            jpmBusiness.setCreatedId(userId);
            jpmBusiness.setCreatedDate(sysDate);
            jpmBusiness.setUpdatedId(userId);
            jpmBusiness.setUpdatedDate(sysDate);
            jpmBusinessRepository.create(jpmBusiness);
        }
        
        // save history
        jpmHiBusinessService.saveJpmHiBusiness(jpmBusiness);
        
        return jpmBusiness;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JpmBusiness saveJpmBusinessDto(JpmBusinessDto jpmBusinessDto) {
        JpmBusiness jpmBusiness = objectMapper.convertValue(jpmBusinessDto, JpmBusiness.class);
        jpmBusiness.setId(jpmBusinessDto.getBusinessId());
        
        // save data
        jpmBusiness = this.saveJpmBusiness(jpmBusiness);
        
        // update id
        jpmBusinessDto.setBusinessId(jpmBusiness.getId());
        
        return jpmBusiness;
    }

    @Override
    public int countBySearchCondition(JpmBusinessSearchDto jpmBusinessSearchDto) {
        return jpmBusinessRepository.countBySearchCondition(jpmBusinessSearchDto);
    }

    @Override
    public List<JpmBusinessDto> getBusinessDtosByCondition(JpmBusinessSearchDto jpmBusinessSearchDto,Pageable pageable) {
        return jpmBusinessRepository.getBusinessDtosByCondition(jpmBusinessSearchDto, pageable).getContent();
    }

    @Override
    public JpmBusinessDto getBusinessDtoByCodeAndCompanyId(String businessCode, Long companyId) {
        return jpmBusinessRepository.getBusinessDtoByCodeAndCompanyId(businessCode, companyId);
    }

    @Override
    public JpmBusinessDto getJpmBusinessDtoByBusinessCode(String businessCode) {
        return jpmBusinessRepository.getJpmBusinessDtoByBusinessCode(businessCode);
    }

}