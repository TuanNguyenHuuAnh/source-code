package vn.com.unit.ep2p.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginDto;
import vn.com.unit.cms.core.module.usersLogin.entity.UserLogin;
import vn.com.unit.cms.core.module.usersLogin.repository.UserLoginRepository;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.service.UserLoginService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserLoginServiceImpl extends AbstractCommonService implements UserLoginService {
    
    @Autowired
    private UserLoginRepository userLoginRepository;
    
	@Override
	public void saveUserLogin(UserLogin userLogin) {
		userLoginRepository.save(userLogin);		
	}

	@Override
	public UserLogin getUserLoginIdByAccessToken(String accessToken) {
		return userLoginRepository.getIdByAccessToken(accessToken);
	}  
}
