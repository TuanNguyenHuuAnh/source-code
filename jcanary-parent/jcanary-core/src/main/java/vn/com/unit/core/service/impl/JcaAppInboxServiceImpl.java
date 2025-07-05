/*******************************************************************************
 * Class        ：JcaAppInboxServiceImpl
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAppInboxDto;
import vn.com.unit.core.entity.JcaAppInbox;
import vn.com.unit.core.repository.JcaAppInboxRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAppInboxLangService;
import vn.com.unit.core.service.JcaAppInboxService;

/**
 * JcaAppInboxServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAppInboxServiceImpl implements JcaAppInboxService{

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JcaAppInboxRepository appInboxRepository;
    
    @Autowired
    private JcaAppInboxLangService jcaAppInboxLangService;
    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaAppInboxService#saveJcaAppInboxDto(vn.com.unit.core.dto.JcaAppInboxDto)
     */
    @Override
    public JcaAppInboxDto saveJcaAppInboxDto(JcaAppInboxDto appInboxDto) {
        JcaAppInbox appInbox = objectMapper.convertValue(appInboxDto, JcaAppInbox.class);
        appInbox.setId(appInboxDto.getAppInboxId());
        // save entity
        this.saveJcaAppInbox(appInbox);
        appInboxDto.setAppInboxId(appInbox.getId());
        return appInboxDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAppInbox saveJcaAppInbox(JcaAppInbox appInbox) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long id = appInbox.getId();
//        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long accountId = CommonConstant.SYSTEM_ID;
        if(null != id) {
            JcaAppInbox oldJcaAppInbox =  appInboxRepository.findOne(id);
            if (null != oldJcaAppInbox) {
                appInbox.setCreatedDate(oldJcaAppInbox.getCreatedDate());
                appInbox.setCreatedId(oldJcaAppInbox.getCreatedId());
                appInbox.setUpdatedDate(sysDate);
                appInbox.setUpdatedId(accountId);
                appInboxRepository.update(appInbox);
            }
            
        }else {
            appInbox.setCreatedDate(sysDate);
            appInbox.setCreatedId(accountId);
            appInbox.setUpdatedDate(sysDate);
            appInbox.setUpdatedId(accountId);
            appInboxRepository.create(appInbox);
        }
        
        return appInbox;
    }
    
    @Override
    public int countJcaAppInbox() {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        return appInboxRepository.countJcaAppInbox(userId);
    }


    @Override
    public List<JcaAppInboxDto> getJcaAppInboxDto(Pageable pageable) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        return appInboxRepository.getJcaAppInboxDtoByUserId(userId, pageable).getContent();
    }

    @Override
    public JcaAppInboxDto getJcaAppInboxDtoByAppInboxId(Long appInboxId) {
        return appInboxRepository.getJcaAppInboxDtoByAppInboxId(appInboxId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaAppInboxDtoByAppInboxId(Long appInboxId) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if(null != appInboxId) {
            JcaAppInbox oldJcaAppInbox =  appInboxRepository.findOne(appInboxId);
            if (null != oldJcaAppInbox) {
                oldJcaAppInbox.setDeletedDate(sysDate);
                oldJcaAppInbox.setDeletedId(userId);
                appInboxRepository.update(oldJcaAppInbox);
            }
        }
        
        // delete lang
        jcaAppInboxLangService.deleteJcaAppInboxLangDtoByAppInboxId(appInboxId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllJcaAppInboxDto() {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        // delete all lang by account id
        jcaAppInboxLangService.deleteAllJcaAppInboxLangDtoByAppInboxId(userId);
        // delete all app inbox
        appInboxRepository.deleteAllJcaAppInboxDtoByUserId(userId, sysDate);
      
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void readJcaAppInboxById(Long appInboxId, boolean readFlag) {
        if(null != appInboxId) {
            JcaAppInbox oldJcaAppInbox =  appInboxRepository.findOne(appInboxId);
            if (null != oldJcaAppInbox) {
                oldJcaAppInbox.setReadFlag(readFlag);
                appInboxRepository.update(oldJcaAppInbox);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void readAllJcaAppInbox(boolean readFlag) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        appInboxRepository.readAllJcaAppInbox(userId, readFlag);
    }

}
