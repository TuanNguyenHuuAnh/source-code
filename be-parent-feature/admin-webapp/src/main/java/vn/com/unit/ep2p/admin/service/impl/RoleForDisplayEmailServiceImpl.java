package vn.com.unit.ep2p.admin.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailDto;
import vn.com.unit.ep2p.admin.dto.RoleForDisplayEmailEditDto;
import vn.com.unit.ep2p.admin.entity.RoleForDisplayEmail;
import vn.com.unit.ep2p.admin.repository.RoleForDisplayEmailRepository;
import vn.com.unit.ep2p.admin.service.RoleForDisplayEmailService;

/**
 * RoleForCompanyServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForDisplayEmailServiceImpl implements RoleForDisplayEmailService {

	@Autowired
    private RoleForDisplayEmailRepository roleForDisplayEmailRepository;

//	@Autowired
//	private CommonService comService;
	
	@Autowired
	MessageSource messageSource;

	@Override
	public List<RoleForDisplayEmailDto> getListRoleForDisplayEmail(Long roleId) {
	    return null;
//		return roleForDisplayEmailRepository.findByRoleId(roleId, UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
	}

	@Override
	public Boolean saveRoleForDisplayEmail(RoleForDisplayEmailEditDto roleDisplayEmailEditDto, Locale locale){
	    Boolean rs = false;
//		String userNameLogin = UserProfileUtils.getUserNameLogin();
//		Date systemDate = comService.getSystemDateTime();
		List<RoleForDisplayEmailDto> roleCompanyDtoList = roleDisplayEmailEditDto.getData();
		Long roleId = roleDisplayEmailEditDto.getRoleId();
		try {
		    for (RoleForDisplayEmailDto roleCompanyDto : roleCompanyDtoList) {
		        Long id = roleCompanyDto.getId();
		        RoleForDisplayEmail roleForCompany = new RoleForDisplayEmail();
		        if(id != null) {
		            roleForCompany = roleForDisplayEmailRepository.findOne(id);
		        }
		        
                roleForCompany.setCompanyId(roleCompanyDto.getCompanyId());
                roleForCompany.setOrgId(roleCompanyDto.getOrgId());
                roleForCompany.setRoleId(roleId);
                roleForCompany.setDelFlg(false);
                roleForDisplayEmailRepository.save(roleForCompany);
		    }
		    
		    rs = true;
		}catch (Exception ex) {
            ex.printStackTrace();
        }
		
		return rs;
	}

    @Override
    public void deleteById(Long id) {
//        String userNameLogin = UserProfileUtils.getUserNameLogin();
//        Date systemDate = comService.getSystemDateTime();
//        RoleForDisplayEmail roleForCompany = roleForDisplayEmailRepository.findOne(id);
//        if(null!=roleForCompany) {
//            roleForDisplayEmailRepository.save(roleForCompany); 
//        }
    }
    
    @Override
    public String validateRoleForDisplayEmail(RoleForDisplayEmailEditDto roleDisplayEmailEditDto, Locale locale) {
        String error = null;
        List<RoleForDisplayEmailDto> roleForDisplayEmailDtos = roleDisplayEmailEditDto.getData();
        if (roleForDisplayEmailDtos != null && !roleForDisplayEmailDtos.isEmpty()) {
            //check org
            for (RoleForDisplayEmailDto roleForDisplayEmailDto : roleForDisplayEmailDtos) {
                if(null == roleForDisplayEmailDto.getOrgId()) {
                    return messageSource.getMessage("message.role.for.display.email.org.null", null, locale);
                }
            }
            // check dup
            int size = roleForDisplayEmailDtos.size() - 1;
            for (int i = 0; i < size; i++) {
                Long companyId = roleForDisplayEmailDtos.get(i).getCompanyId();
                Long companyIdNext = roleForDisplayEmailDtos.get(i + 1).getCompanyId();
                Long orgId = roleForDisplayEmailDtos.get(i).getOrgId();
                Long orgIdNext = roleForDisplayEmailDtos.get(i + 1).getOrgId();

                if (companyId.equals(companyIdNext)
                        && (orgId == orgIdNext || (orgId != null && orgIdNext != null && orgId.equals(orgIdNext)))) {
                    error = messageSource.getMessage("message.role.for.display.email.exist", null, locale);
                    break;
                }
            }
        }
        return error;
    }
}
