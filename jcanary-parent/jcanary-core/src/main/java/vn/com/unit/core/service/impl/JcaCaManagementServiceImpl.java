/*******************************************************************************
 * Class        ：JcaCaManagementServiceImpl
 * Created date ：2020/12/16
 * Lasted date  ：2020/12/16
 * Author       ：minhnv
 * Change log   ：2020/12/16：01-00 minhnv create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaCaManagementDto;
import vn.com.unit.core.dto.JcaCaManagementSearchDto;
import vn.com.unit.core.entity.JcaCaManagement;
import vn.com.unit.core.repository.JcaCaManagementRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaCaManagementService;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaCaManagementServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author minhnv
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaCaManagementServiceImpl implements JcaCaManagementService {

    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JcaCaManagementRepository jcaCaManagementRepository;

    @Override
    public int countCaManagementDtoByCondition(JcaCaManagementSearchDto jcaCaManagementSearchDto) {
        return jcaCaManagementRepository.countCaManagementDtoByCondition(jcaCaManagementSearchDto);
    }

    @Override
    public List<JcaCaManagementDto> getCaManagementDtoByCondition(JcaCaManagementSearchDto jcaCaManagementSearchDto,
            Pageable pageable) {
        return jcaCaManagementRepository.getCaManagementDtoByCondition(jcaCaManagementSearchDto, pageable).getContent();
    }

    @Override
    public JcaCaManagement getJcaCaManagementById(Long id) {
        return jcaCaManagementRepository.findOne(id);
    }

    @Override
    public JcaCaManagement saveJcaCaManagement(JcaCaManagement jcaCaManagement) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        String password = jcaCaManagement.getCaPassword();
        Long id = jcaCaManagement.getId();
        
        String passwordEncrypt = null;
        if (CommonStringUtil.isNotBlank(password)){
            passwordEncrypt = passwordEncoder.encode(password);
        }
        if (null != id) {
            JcaCaManagement oldJcaCaManagement = jcaCaManagementRepository.findOne(id);
            
            if (null != oldJcaCaManagement) {
                if (!CoreConstant.PASSWORD_ENCRYPT.equals(password)) {
                    jcaCaManagement.setCaPassword(passwordEncrypt);
                } else {
                    jcaCaManagement.setCaPassword(oldJcaCaManagement.getCaPassword());
                }

                jcaCaManagement.setCreatedDate(oldJcaCaManagement.getCreatedDate());
                jcaCaManagement.setCreatedId(oldJcaCaManagement.getCreatedId());
                jcaCaManagement.setUpdatedDate(sysDate);
                jcaCaManagement.setUpdatedId(userId);
                jcaCaManagementRepository.update(jcaCaManagement);
            }

        } else {
            jcaCaManagement.setCaPassword(passwordEncrypt);
            jcaCaManagement.setCreatedDate(sysDate);
            jcaCaManagement.setCreatedId(userId);
            jcaCaManagement.setUpdatedDate(sysDate);
            jcaCaManagement.setUpdatedId(userId);
            jcaCaManagementRepository.create(jcaCaManagement);
        }
        return jcaCaManagement;
    }

    @Override
    public JcaCaManagement saveJcaCaManagementDto(JcaCaManagementDto jcaCaManagementDto) {
        JcaCaManagement jcaCaManagement = objectMapper.convertValue(jcaCaManagementDto, JcaCaManagement.class);
        jcaCaManagement.setId(jcaCaManagementDto.getCaManagementId());

        // save data
        jcaCaManagement = this.saveJcaCaManagement(jcaCaManagement);

        // update id
        jcaCaManagementDto.setCaManagementId(jcaCaManagement.getId());
        return jcaCaManagement;
    }

    @Override
    public JcaCaManagementDto getJcaCaManagementDtoById(Long id) {
        return jcaCaManagementRepository.getJcaCaManagementDtoById(id);
    }

    @Override
    public DbRepository<JcaCaManagement, Long> initRepo() {
        return jcaCaManagementRepository;
    }

}
