package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.ChatUserProductDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductSearchDto;
import vn.com.unit.cms.admin.all.entity.RoleForChat;
import vn.com.unit.cms.admin.all.enumdef.ChatUserProductSearchEnum;
import vn.com.unit.cms.admin.all.repository.RoleForChatRepository;
import vn.com.unit.cms.admin.all.service.RoleForChatService;
import vn.com.unit.cms.core.constant.CmsRoleConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.cms.admin.all.jcanary.dto.AccountAddDto;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForChatServiceImpl implements RoleForChatService{

	@Autowired
	private RoleForChatRepository roleForChatRepository;
	
    @Autowired
    private SystemConfig systemConfig;
	
	@Override
	public int saveRoleForChat(String role, String lstUserString) {
		int rs = 0;
		try{
			String userLogin = UserProfileUtils.getUserNameLogin();
			// delete all data old
			roleForChatRepository.deleteUserByRole(role, userLogin);
			
			ObjectMapper mapper = new ObjectMapper();
			List<String> listUser = new ArrayList<String>();
			listUser = mapper.readValue(lstUserString, new TypeReference<List<String>>() {});
			
			for(String user : listUser){
				RoleForChat entity = new RoleForChat();
				entity.setCreateBy(userLogin);
				entity.setCreateDate(new Date());
				entity.setRoleCode(role);
				entity.setUserName(user);
				roleForChatRepository.save(entity);
			}	
			
			rs = 1;
		}catch(Exception ex){
			rs = 0;
		}
		return rs;
	}

	@Override
	public List<ChatUserProductDto> getListUserByRole(String role) {
		List<ChatUserProductDto> result = roleForChatRepository.findListUserByRole(role);
		return result;
	}

	@Override
	public PageWrapper<ChatUserProductDto> getListUserRole(int page, ChatUserProductSearchDto userProductDto,
			Locale locale) {
		int sizeOfPage = userProductDto.getPageSize() != null ? userProductDto.getPageSize() : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        
        PageWrapper<ChatUserProductDto> pageWrapper =new PageWrapper<ChatUserProductDto>(page, sizeOfPage);
        setSearchParamForUserProduct(userProductDto);
        
        int count = roleForChatRepository.countUserRoleAllActive(userProductDto, locale.toString());
        List<ChatUserProductDto> list = new ArrayList<ChatUserProductDto>();
        if(count > 0){
            int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
			list = roleForChatRepository.findAllUserRoleActive(offsetSQL, sizeOfPage, userProductDto, locale.toString());
			
			for(ChatUserProductDto item : list){
				List<AccountAddDto> listUser = roleForChatRepository.findFullNameByRole(item.getRoleName());
				String fullname = "";
				if(listUser != null && listUser.size() > 0){
					for(AccountAddDto acc : listUser){
						fullname += acc.getFullname() + "; ";
					}
					fullname = fullname.substring(0, fullname.length() - 2);
				}
				item.setFullname(fullname);
			}
        }
        
        pageWrapper.setDataAndCount(list, count);
        return pageWrapper;
	}
	
	 private void setSearchParamForUserProduct(ChatUserProductSearchDto userProductdto) {
		 	if (null == userProductdto.getFieldValues()) {
		 		userProductdto.setFieldValues(new ArrayList<String>());
	        }

	        if (userProductdto.getFieldValues().isEmpty()) {
	        	userProductdto.setUser(userProductdto.getFieldSearch() == null ? null  : userProductdto.getFieldSearch().trim());    
	        	userProductdto.setRoleName(userProductdto.getFieldSearch() == null ? null  : userProductdto.getFieldSearch().trim());
	        }else{
	            for (String item : userProductdto.getFieldValues()) {
	            	
	                if (item.equals(ChatUserProductSearchEnum.USER.name())) {
	                	userProductdto.setUser(userProductdto.getFieldSearch().trim());
	                }
	                else if (item.equals(ChatUserProductSearchEnum.ROLE.name())) {
	                	userProductdto.setRoleName(userProductdto.getFieldSearch().trim());
	                }
	            }
	        }
	    }

	@Override
	public boolean isRoleAdminChat(String username) {
		boolean result = false;
		RoleForChat chat = roleForChatRepository.findRoleForChatByRoleAndUsername(CmsRoleConstant.ROLE_ADMIN.toString(), username);
		
		if (chat != null) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean hasRoleChat(String username) {
		boolean result = false;
		RoleForChat chat = roleForChatRepository.findRoleForChatByRoleAndUsername(CmsRoleConstant.ROLE_AGENT.toString(), username);
		
		if (chat != null) {
			result = true;
		}
		
		return result;
	}
}
