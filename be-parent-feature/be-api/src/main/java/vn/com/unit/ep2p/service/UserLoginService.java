package vn.com.unit.ep2p.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginDto;
import vn.com.unit.cms.core.module.usersLogin.entity.UserLogin;

@Service
public interface UserLoginService {
                   
    public void saveUserLogin(UserLogin userLogin);
  
    public UserLogin getUserLoginIdByAccessToken(String accessToken);
    
 }
