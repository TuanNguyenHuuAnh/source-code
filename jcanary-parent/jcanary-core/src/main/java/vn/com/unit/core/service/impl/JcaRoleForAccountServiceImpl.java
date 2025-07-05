/*******************************************************************************
 * Class        :JcaRoleForAccountServiceImpl
 * Created date :2021/01/22
 * Lasted date  :2021/01/22
 * Author       :SonND
 * Change log   :2021/01/22 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaRoleForAccountDto;
import vn.com.unit.core.entity.JcaRoleForAccount;
import vn.com.unit.core.repository.JcaRoleForAccountRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaRoleForAccountService;


/**
 * JcaRoleForAccountServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaRoleForAccountServiceImpl implements JcaRoleForAccountService {
	
	@Autowired
	JcaRoleForAccountRepository jcaRoleForAccountRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaRoleForAccount saveJcaRoleForAccount(JcaRoleForAccount jcaRoleForAccount) {
//		Date sysDate = CommonDateUtil.getSystemDateTime();
//		Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
//		Long id = jcaRoleForAccount.getId();
//		if(null != id) {
//		    JcaRoleForAccount oldJcaRoleForAccount =  jcaRoleForAccountRepository.findOne(id);
//			if (null != oldJcaRoleForAccount) {
//			    jcaRoleForAccount.setCreatedDate(oldJcaRoleForAccount.getCreatedDate());
//			    jcaRoleForAccount.setCreatedId(oldJcaRoleForAccount.getCreatedId());
//			    jcaRoleForAccount.setUpdatedDate(sysDate);
//			    jcaRoleForAccount.setUpdatedId(userId);
//			    jcaRoleForAccountRepository.update(jcaRoleForAccount);
//			}
//			
//		}else {
//		    jcaRoleForAccount.setCreatedDate(sysDate);
//		    jcaRoleForAccount.setCreatedId(userId);
//		    jcaRoleForAccount.setUpdatedDate(sysDate);
//		    jcaRoleForAccount.setUpdatedId(userId);
//		    jcaRoleForAccountRepository.create(jcaRoleForAccount);
//		}
		return jcaRoleForAccount;
	}
	
    @Override
    public JcaRoleForAccount saveJcaRoleForAccountDto(JcaRoleForAccountDto jcaRoleForAccountDto) {
        JcaRoleForAccount jcaRoleForAccount = objectMapper.convertValue(jcaRoleForAccountDto, JcaRoleForAccount.class);
//        jcaRoleForAccount.setId(jcaRoleForAccountDto.getRoleForAccountId());
        jcaRoleForAccount.setAccountId(jcaRoleForAccountDto.getUserId());
        // save data
        jcaRoleForAccount = this.saveJcaRoleForAccount(jcaRoleForAccount);
        // update id
//        jcaRoleForAccountDto.setRoleForAccountId(jcaRoleForAccount.getId());
        jcaRoleForAccountDto.setUserId(jcaRoleForAccount.getAccountId());
        return jcaRoleForAccount;
    }

	@Override
	public JcaRoleForAccountDto getJcaRoleForAccountDtoById(Long id) {
		return jcaRoleForAccountRepository.getJcaRoleForAccountDtoById(id);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaRoleForAccountByUserId(Long userId) {
        jcaRoleForAccountRepository.deleteJcaRoleForAccountByUserId(userId);
    }
    
    @Override
    public List<JcaRoleForAccountDto> getJcaRoleForAccountDtoByUserId(Long userId) {
        return jcaRoleForAccountRepository.getJcaRoleForAccountDtoByUserId(userId);
    }

    @Override
    public void deleteJcaRoleForAccountById(Long id) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if(null != id) {
            JcaRoleForAccount oldJcaRoleForAccount =  jcaRoleForAccountRepository.findOne(id);
            if (null != oldJcaRoleForAccount) {
//                oldJcaRoleForAccount.setDeletedDate(sysDate);
//                oldJcaRoleForAccount.setDeletedId(userId);
                jcaRoleForAccountRepository.update(oldJcaRoleForAccount);
            }
        }
    }
}
