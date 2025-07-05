/*******************************************************************************
 * Class        ：JcaAppInboxLangServiceImpl
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：SonND
 * Change log   ：2021/02/01：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAppInboxLangDto;
import vn.com.unit.core.entity.JcaAppInboxLang;
import vn.com.unit.core.repository.JcaAppInboxLangRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAppInboxLangService;

/**
 * JcaAppInboxLangServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAppInboxLangServiceImpl implements JcaAppInboxLangService{

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JcaAppInboxLangRepository jcaAppInboxLangRepository;
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaAppInboxService#saveJcaAppInboxDto(vn.com.unit.core.dto.JcaAppInboxDto)
     */
    @Override
    public JcaAppInboxLangDto saveJcaAppInboxLangDto(JcaAppInboxLangDto jcaAppInboxLangDto) {
        JcaAppInboxLang jcaAppInboxLang = objectMapper.convertValue(jcaAppInboxLangDto, JcaAppInboxLang.class);
        jcaAppInboxLang.setId(jcaAppInboxLangDto.getAppInboxLangId());
        // save entity
        this.saveJcaAppInboxLang(jcaAppInboxLang);
        jcaAppInboxLangDto.setAppInboxLangId(jcaAppInboxLang.getId());
        return jcaAppInboxLangDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAppInboxLang saveJcaAppInboxLang(JcaAppInboxLang jcaAppInboxLang) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = jcaAppInboxLang.getId();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if(null != id) {
            JcaAppInboxLang oldJcaAppInboxLang =  jcaAppInboxLangRepository.findOne(id);
            if (null != oldJcaAppInboxLang) {
                jcaAppInboxLang.setCreatedDate(oldJcaAppInboxLang.getCreatedDate());
                jcaAppInboxLang.setCreatedId(oldJcaAppInboxLang.getCreatedId());
                jcaAppInboxLang.setUpdatedDate(sysDate);
                jcaAppInboxLang.setUpdatedId(userId);
                jcaAppInboxLangRepository.update(jcaAppInboxLang);
            }
            
        }else {
            jcaAppInboxLang.setCreatedDate(sysDate);
            jcaAppInboxLang.setCreatedId(userId);
            jcaAppInboxLang.setUpdatedDate(sysDate);
            jcaAppInboxLang.setUpdatedId(userId);
            jcaAppInboxLangRepository.create(jcaAppInboxLang);
        }
        return jcaAppInboxLang;
    }

    @Override
    public JcaAppInboxLangDto getJcaAppInboxLangDtoByAppInboxId(Long appInboxId) {
        return jcaAppInboxLangRepository.getJcaAppInboxLangDtoByAppInboxId(appInboxId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaAppInboxLangDtoByAppInboxId(Long appInboxId) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        jcaAppInboxLangRepository.deleteJcaAppInboxLangDtoByAppInboxId(appInboxId, userId, sysDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllJcaAppInboxLangDtoByAppInboxId(Long userId) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        jcaAppInboxLangRepository.deleteAllJcaAppInboxLangDtoByAppInboxId(userId, sysDate);
    }

}
