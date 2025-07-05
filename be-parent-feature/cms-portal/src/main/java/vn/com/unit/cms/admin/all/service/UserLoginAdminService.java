package vn.com.unit.cms.admin.all.service;

import vn.com.unit.cms.core.module.usersLogin.dto.ChannelCountDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginDto;
import vn.com.unit.cms.core.module.usersLogin.dto.UserLoginSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.dts.exception.DetailException;

import java.util.List;
import java.util.Locale;

public interface UserLoginAdminService {
	public int countAllAccessTokenValidate();
	public void doSearch(PageWrapper<UserLoginDto> pageWrapper, List<ChannelCountDto> channelCountList,  int page, UserLoginSearchDto searchDto, int pageSize, Locale locale) throws DetailException;
}
