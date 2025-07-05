/*******************************************************************************
 * Class        CustomRoleService
 * Created date 2018/01/31
 * Lasted date  2018/01/31
 * Author       Phucdq
 * Change log   2018/01/3101-00 Phucdq create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.admin.dto.MenuDto;
import vn.com.unit.ep2p.admin.dto.RoleCustomizableDto;
import vn.com.unit.ep2p.admin.entity.JcaForRoleLangdingPage;
import vn.com.unit.ep2p.admin.repository.CustomRoleRepository;
import vn.com.unit.ep2p.admin.repository.MenuRepository;
import vn.com.unit.ep2p.admin.service.CustomRoleService;

/**
 * CustomRoleService
 * 
 * @version 01-00
 * @since 01-00
 * @author Phucdq
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomRoleServiceIpml implements CustomRoleService {
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    CustomRoleRepository customRoleRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    // Model mapper
    ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public List<MenuDto> getListMenuByRoleID(Long roleId, Long companyId, String languageCode) {
        try {
            return customRoleRepository.getListMenuByRoleID(roleId, companyId, languageCode);
        } catch (Exception e) {
        	logger.error("Exception ", e);
            return new ArrayList<>();
        }

    }

    @Override
    public RoleCustomizableDto findRoleCustomByRoleId(Long roleId) {
        try {
            RoleCustomizableDto data = customRoleRepository.findOneByRoleId(roleId);
            return data != null ? data : new RoleCustomizableDto();
        } catch (Exception e) {
            return new RoleCustomizableDto();
        }
    }

    @Override
    public boolean updateRole(List<RoleCustomizableDto> roles) {
        try{
            for (RoleCustomizableDto role : roles) {
                JcaForRoleLangdingPage roleDetail = customRoleRepository.findJcaForRoleLangdingPageByRoleId(role.getRoleId());
                
                JcaForRoleLangdingPage entity = objectMapper.convertValue(role, JcaForRoleLangdingPage.class);
                if (roleDetail==null) {
                    entity.setCreatedDate(new Date());
                    entity.setCreatedId(UserProfileUtils.getAccountId());
                    customRoleRepository.create(entity);
                } else {
                    entity.setId(roleDetail.getId());
                    customRoleRepository.update(entity);
                }
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public List<RoleCustomizableDto> getListRole() {
        return customRoleRepository.getAllRole();
    }

    /* (non-Javadoc)
     * @see vn.com.unit.dmcs.service.CustomRoleService#findMenuByAccountId(java.lang.Long)
     */
    @Override
    public MenuDto findMenuByAccountId(Long accountId) {
        return customRoleRepository.findMenuByAccountId(accountId);
    }

	@Override
	public List<RoleCustomizableDto> getListByRole(Long companyId) {
		// TODO Auto-generated method stub
		return customRoleRepository.getListRole(companyId);
	}
}
