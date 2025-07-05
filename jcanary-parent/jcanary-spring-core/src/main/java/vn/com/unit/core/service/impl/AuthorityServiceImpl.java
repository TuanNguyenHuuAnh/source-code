/*******************************************************************************
 * Class        ：AuthorityServiceImpl
 * Created date ：2021/01/28
 * Lasted date  ：2021/01/28
 * Author       ：vinhlt
 * Change log   ：2021/01/28：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.AuthorityDto;
import vn.com.unit.core.dto.MenuDto;
import vn.com.unit.core.dto.MenuInfo;
import vn.com.unit.core.dto.MenuItem;
import vn.com.unit.core.dto.MenuParam;
import vn.com.unit.core.dto.UserAuthorityDto;
import vn.com.unit.core.entity.JcaAuth;
import vn.com.unit.core.repository.AuthorityRepository;
import vn.com.unit.core.repository.JcaAuthRepository;
import vn.com.unit.core.service.AuthorityService;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.dts.utils.DtsStringUtil;

/**
 * AuthorityServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */
@Service
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService{
    
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private  JcaAuthRepository jcaAuthRepository;

    @Autowired
	@Qualifier("sqlManagerServicePr")
	private SqlManagerServiceImpl sqlManagerService;
    
    @Override
    public List<GrantedAuthority> findAuthorityDetail(UserAuthorityDto account) {

        List<AuthorityDto> listAuthorityAll = new ArrayList<>();
        List<AuthorityDto> listAuthorityRoleForAccount = new ArrayList<>();

        Long id = account.getId();

        // find all role from account
        listAuthorityRoleForAccount = authorityRepository.findAllRoleForAccountByAccountId(id);
        listAuthorityAll.addAll(listAuthorityRoleForAccount);

        List<GrantedAuthority> listGrantedAuthority = new ArrayList<>();

        for (AuthorityDto authorityDto : listAuthorityAll) {
            String functionCode = authorityDto.findFunction();
            if (DtsStringUtil.isNotBlank(functionCode)) {
                listGrantedAuthority.add(new SimpleGrantedAuthority(functionCode));
            }
        }

        return listGrantedAuthority;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.AuthorityService#getAccountById(java.lang.Long)
     */
    @Override
    public UserAuthorityDto getAccountById(Long accountId) {
        return authorityRepository.findAccountById(accountId);
    }

    @Override
    public int countByToken(String token) {
        return jcaAuthRepository.countByToken(token);
    }

    @Override
    public void insertToken(String token) {
        JcaAuth jcaAuth = JcaAuth.builder().token(token).build();
        jcaAuth.setCreateDate(new Date());
        jcaAuthRepository.create(jcaAuth);
    }
    
    @Override
    public void removeToken(String token) {
        jcaAuthRepository.removeToken(token);
    }
    
    public List<MenuInfo> getMenuInfo(Long accountId) {
    	List<MenuInfo> result = new ArrayList<MenuInfo>();
    	
    	// List<MenuDto> lstMenu = authorityRepository.getMenuInfoByAccountId(accountId);
    	List<MenuDto> lstMenu = null;
    	MenuParam param = new MenuParam();
 		try {
 			param.accountId = accountId;
 			sqlManagerService.call("SP_GET_MENU_INFO", param);
 			lstMenu = param.data;
 		} catch(Exception e) {
 			System.out.print(e);
 		}
    	for (MenuDto dto : lstMenu) {
    		MenuInfo menuInfo = null;
			if ("/".equals(dto.getUrl())) {
				menuInfo = new MenuInfo();
				menuInfo.setId(dto.getMenuId());
				menuInfo.setName(dto.getMenuName());
				menuInfo.setItemId(dto.getFunctionCode());
				menuInfo.setIcon(dto.getIcon());
				menuInfo.setLink(dto.getUrl());
				menuInfo.setLocalLink(true);
				if (dto.getFavorite() == 1) {
					menuInfo.setFavorite(true);	
				}
				result.add(menuInfo);
			} else {
				menuInfo = getMenuInfoById(dto.getParentId(), result);
				MenuItem menuItem = new MenuItem();
				menuItem.setId(dto.getMenuId());
				menuItem.setParentId(dto.getParentId());
				menuItem.setName(dto.getMenuName());
				menuItem.setLink(dto.getUrl());
				if (dto.getUrl().contains("http")) {
					menuItem.setLocalLink(false);
				} else {
					menuItem.setLocalLink(true);
				}
				menuItem.setItemId(dto.getFunctionCode());
				menuItem.setIcon(dto.getIcon());
				menuItem.setComponent(dto.getComponent());
				menuItem.setPrivate(dto.isPrivate());
				menuItem.setExact(dto.isExact());
				if (dto.getFavorite() == 1) {
					menuItem.setFavorite(true);	
				}
				
				if (menuInfo != null) {
					if (menuInfo.getSubMenuList() == null) {
						List<MenuItem> subMenuList = new ArrayList<MenuItem>();
						subMenuList.add(menuItem);
						menuInfo.setSubMenuList(subMenuList);
					} else {
						menuInfo.getSubMenuList().add(menuItem);
					}	
				}
			}
		}
    	
    	return result;
    }
    
    private MenuInfo getMenuInfoById(Long parentId, List<MenuInfo> lstMenu) {
    	for (MenuInfo menuInfo : lstMenu) {
			if (parentId.equals(menuInfo.getId())) {
				return menuInfo;
			}
		}
    	
    	return null;
    }
}
