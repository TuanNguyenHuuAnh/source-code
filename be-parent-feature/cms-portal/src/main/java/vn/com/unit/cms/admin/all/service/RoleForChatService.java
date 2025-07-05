package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.cms.admin.all.dto.ChatUserProductDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductSearchDto;
import vn.com.unit.common.dto.PageWrapper;

public interface RoleForChatService {
	public int saveRoleForChat(String role, String user);
	
	public List<ChatUserProductDto> getListUserByRole(String role);
	
	PageWrapper<ChatUserProductDto> getListUserRole(int page, ChatUserProductSearchDto userProductDto, Locale locale);
	
	boolean isRoleAdminChat(String username);
	
	boolean hasRoleChat(String username);
}
