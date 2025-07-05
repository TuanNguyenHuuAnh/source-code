/*******************************************************************************
 * Class        :AccountServiceImpl
 * Created date :2020/12/01
 * Lasted date  :2020/12/01
 * Author       :SonND
 * Change log   :2020/12/01 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.constant.CoreExceptionCodeConstant;
import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.core.dto.JcaAccountSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaAccountRegister;
import vn.com.unit.core.repository.JcaAccountRegisterRepository;
import vn.com.unit.core.repository.JcaAccountRepository;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.dts.exception.DetailException;


/**
 * AccountServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAccountServiceImpl implements JcaAccountService {
	
	@Autowired
	JcaAccountRepository jcaAccountRepository;
	@Autowired
	JcaAccountRegisterRepository jcaAccountRegisterRepository;
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private JCommonService commonService;

	@Override
	public int countAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto) {
		return jcaAccountRepository.countAccountDtoByCondition(jcaAccountSearchDto);
	}

	@Override
	public List<JcaAccountDto> getAccountDtoByCondition(JcaAccountSearchDto jcaAccountSearchDto, Pageable pagable) {
		return jcaAccountRepository.getAccountDtoByCondition(jcaAccountSearchDto, pagable).getContent();
	}

	@Override
	public JcaAccount getAccountById(Long id) {
		return jcaAccountRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaAccount saveJcaAccount(JcaAccount jcaAccount) {
		Date sysDate = commonService.getSystemDate();
		
		Long userId = 1L;
		if (UserProfileUtils.getUserPrincipal() != null) {
		    userId = UserProfileUtils.getUserPrincipal().getAccountId();
		}
		
		Long id = jcaAccount.getId();
		if(null != id) {
			JcaAccount oldJcaAccount =  jcaAccountRepository.findOne(id);
			if (null !=oldJcaAccount) {
				if (ObjectUtils.isNotEmpty(oldJcaAccount.getPassword())) {
					jcaAccount.setPassword(oldJcaAccount.getPassword());	
				}
				jcaAccount.setCreatedDate(oldJcaAccount.getCreatedDate());
				jcaAccount.setCreatedId(oldJcaAccount.getCreatedId());
				jcaAccount.setUpdatedDate(sysDate);
				jcaAccount.setUpdatedId(userId);
				jcaAccountRepository.update(jcaAccount);
			}
			
		}else {
			jcaAccount.setCreatedDate(sysDate);
			jcaAccount.setCreatedId(userId);
			jcaAccount.setUpdatedDate(sysDate);
			jcaAccount.setUpdatedId(userId);
			jcaAccountRepository.create(jcaAccount);
		}
		return jcaAccount;
	}
	
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAccount saveJcaAccountDto(JcaAccountDto jcaAccountDto) throws DetailException {
        JcaAccount jcaAccount = new JcaAccount();
        try {
            //encrypt password
//            if(null != jcaAccountDto.getPassword()) {
//                jcaAccountDto.setPassword(CommonPasswordUtil.encryptString(jcaAccountDto.getPassword()));
//            }
            jcaAccount = objectMapper.convertValue(jcaAccountDto, JcaAccount.class);
            jcaAccount.setId(jcaAccountDto.getUserId());
            // save data
            jcaAccount = this.saveJcaAccount(jcaAccount);
            // update id
            jcaAccountDto.setUserId(jcaAccount.getId());
        } catch (Exception e) {
            throw new DetailException(CoreExceptionCodeConstant.E301901_ENCRYPT_PASSWORD_ERROR, true);
        }
        return jcaAccount;
    }

	@Override
	public JcaAccountDto getJcaAccountDtoById(Long id) {
		return jcaAccountRepository.getJcaAccountDtoById(id);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long accountId, String passwordNew) throws DetailException {
//        String enPassword;
        try {
//            enPassword = CommonPasswordUtil.encryptString(passwordNew);
        } catch (Exception e) {
            throw new DetailException(CoreExceptionCodeConstant.E301901_ENCRYPT_PASSWORD_ERROR, true);
        }
        jcaAccountRepository.updatePassword(accountId, passwordNew);
    }

    @Override
    public boolean checkPasswordOld(String passwordOld, String password) throws DetailException {
        boolean result = false;
        try {
//            String passwordDb = CommonPasswordUtil.decryptString(passwordOld);
            if (CommonStringUtil.isNotBlank(passwordOld) && CommonStringUtil.isNotBlank(password) && password.equals(passwordOld)) {
                result = true;
            }
        } catch (Exception e) {
            throw new DetailException(CoreExceptionCodeConstant.E301901_ENCRYPT_PASSWORD_ERROR, true);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaAccountById(Long id) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        if(null != id) {
            JcaAccount jcaAccount =  jcaAccountRepository.findOne(id);
            if (null != jcaAccount && !"candidate".equals(jcaAccount.getUsername())) {
                jcaAccount.setDeletedDate(sysDate);
                jcaAccount.setDeletedId(userId);
                jcaAccountRepository.update(jcaAccount);
            }
            
        }
    }

    @Override
    public List<String> getListEmailByAccountId(List<Long> accountIds) {
        List<String> result = new ArrayList<>();
        if (null != accountIds) {
            int size =  accountIds.size();
            int step = size / 1000;
            
            for (int i = 0; i <= step; i++) {
                List<Long> stepAccountIds = new ArrayList<>();
                int start = i * 1000;
                int end = (i * 1000) + 1000;
                if (size < end) {
                    end = (i * 1000) + (size % 1000);
                }
                for (int j = start; j < end; j++) {
                    stepAccountIds.add(accountIds.get(j));
                }
                List<String> stepEmail = jcaAccountRepository.getListEmailByAccountId(stepAccountIds);
                result.addAll(stepEmail);
            }
        }
        return result;
    }

    @Override
    public int countJcaAccountDtoByUsername(String username, Long userId) {
        return jcaAccountRepository.countJcaAccountDtoByUsername(username, userId);
    }

    @Override
    public int countJcaAccountDtoByEmail(String email, Long userId) {
        return jcaAccountRepository.countJcaAccountDtoByEmail(email, userId);
    }
    
    @Override
    public int countJcaAccountDtoByCode(String code) {
        return jcaAccountRepository.countJcaAccountDtoByCode(code);
    }

    @Override
    public DbRepository<JcaAccount, Long> initRepo() {
        return jcaAccountRepository;
    }
    
    @Override
    public List<Long> getAccIdsByRoleIds(List<Long> roleId) {
		return jcaAccountRepository.getAccIdsByRoleIds(roleId);
	}

	@Override
	public List<JcaAccount> getListByUserName(String userName) {
		return jcaAccountRepository.getListByUserName(userName);
	}

	public UserPrincipal buildUserProfile(JcaAccount account, List<GrantedAuthority> authorities) {
		String username = account.getUsername();
		String password = account.getPassword();
		UserPrincipal uProfile = new UserPrincipal(username, password, true, true, true, true, authorities,
				account.getCreatedDate(), account.getPositionId(), account.getFaceMask());

		Long accountId = account.getId();
		uProfile.setAccountId(accountId);

		String fullname = account.getFullname();
		uProfile.setFullname(fullname);

		String email = account.getEmail();
		uProfile.setEmail(email);

		Date birthday = account.getBirthday();
		uProfile.setBirthday(birthday);

		String avatar = account.getAvatar();
		uProfile.setAvatar(avatar);

		uProfile.setAvatarRepoId(account.getAvatarRepoId());
		
		List<Long> companyIdList = new ArrayList<>();
		List<Long> companyIdDataList = new ArrayList<>();
		List<Long> companyIdEmailList = new ArrayList<>();

		uProfile.setCompanyId(account.getCompanyId());

//		uProfile.setDepartmentList(departmentList);
		uProfile.setPositionId(account.getPositionId());
		
		uProfile.setCompanyIdList(companyIdList);
		uProfile.setCompanyIdDataList(companyIdDataList);
		uProfile.setCompanyIdEmailList(companyIdEmailList);
		uProfile.setChannel(account.getChannel());
        
        uProfile.setCode(account.getCode());
        
		return uProfile;
	
	}

	@Override
	public boolean syncADLDAP() {
		// TODO Auto-generated method stub
		return true;
	}

    @Override
    public List<String> getListEmailByAccountId(String username) {
        return jcaAccountRepository.findListEmailByUserName(username);
    }

    @Override
    public int countJcaAccountDtoByPhone(String phone, Long userId) {
        return jcaAccountRepository.countJcaAccountDtoByPhone(phone, userId);
    }

	@Override
	public void saveJcaAccountRegister(JcaAccountDto jacAccount) {
		if(jacAccount != null) {
			JcaAccountRegister jcaAccountRegister = jcaAccountRegisterRepository.findByAccountId(jacAccount.getUserId());
			if(jcaAccountRegister != null) {
				jcaAccountRegister.setFullName(jacAccount.getFullname());
				jcaAccountRegister.setEmail(jacAccount.getEmail());
				jcaAccountRegister.setPhone(jacAccount.getPhone());
				jcaAccountRegister.setGender(jacAccount.getGender());
			} else {
				jcaAccountRegister = new JcaAccountRegister();
				jcaAccountRegister.setAccountId(jacAccount.getUserId());
				jcaAccountRegister.setFullName(jacAccount.getFullname());
				jcaAccountRegister.setEmail(jacAccount.getEmail());
				jcaAccountRegister.setPhone(jacAccount.getPhone());
				jcaAccountRegister.setGender(jacAccount.getGender());
				jcaAccountRegister.setProvince(jacAccount.getProvince());
				jcaAccountRegister.setProvinceReg(jacAccount.getProvinceCity());
				jcaAccountRegister.setOfficeReg(jacAccount.getOffice());
				jcaAccountRegister.setOfficeNameReg(jacAccount.getOfficeName());
				jcaAccountRegister.setRegisterDate(new Date());
			}
			jcaAccountRegisterRepository.save(jcaAccountRegister);
			}
		}

	@Override
	public JcaAccount getAccountByUid(String uid) {
		return jcaAccountRepository.findAccountByUid(uid);
	}

	@Override
	public JcaAccountRegister saveJcaAccountRegisterContacted(Long id) throws Exception {
		if (ObjectUtils.isNotEmpty(id)) {
			JcaAccountRegister jcaAccountRegister = jcaAccountRegisterRepository.findOne(id);
			if (jcaAccountRegister != null) {
				jcaAccountRegister.setSendMail(true);
			}
			jcaAccountRegisterRepository.save(jcaAccountRegister);
			return jcaAccountRegister;
		} else {
			 throw new Exception("Tài khoản của bạn đã bị tạm khoá do vi phạm. Vui lòng liên hệ Chatbot hoặc Bộ phận Quản lý Đại lý để được hỗ trợ");
		}
	}

	@Override
	public List<JcaAccount> getListByUserNameList(String userNameList) {
		return jcaAccountRepository.getListByUserNameList(userNameList);
	}

}
