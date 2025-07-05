package vn.com.unit.cms.core.module.usersLogin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.logApi.dto.LogApiDto;
import vn.com.unit.cms.core.module.logApi.dto.LogApiSearchDto;
import vn.com.unit.cms.core.module.usersLogin.dto.ChannelCountDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginSearchDto;
import vn.com.unit.cms.core.module.usersLogin.entity.UserLogin;
import vn.com.unit.db.repository.DbRepository;


public interface UserLoginRepository extends DbRepository<UserLogin, Long> {

    public List<UserLoginDto> findAllUserLoginByCondition(
            @Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage, 
            @Param("searchDto") UserLoginSearchDto searchDto);

	int countUserLoginByConditon(@Param("searchDto") UserLoginSearchDto searchDto);
	
	UserLogin getIdByAccessToken(@Param("accessToken") String accessToken);
	
	List<ChannelCountDto> countUserLoginByConditionAndChannel(@Param("searchDto") UserLoginSearchDto searchDto);
		
	List<String> getAllAccessTokenInDay();
}
