package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaRoleForCompanyDto;
import vn.com.unit.core.entity.JcaRoleForCompany;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.impl.JcaRoleForCompanyServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.repository.RoleForCompanyRepository;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.ep2p.admin.service.RoleForCompanyService;
import vn.com.unit.ep2p.admin.utils.Utility;
import vn.com.unit.ep2p.dto.RoleForCompanyEditDto;

/**
 * RoleForCompanyServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author trieuvd
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RoleForCompanyServiceImpl extends JcaRoleForCompanyServiceImpl implements RoleForCompanyService {

	/** CompanyAuthorityRepository */
	@Autowired
	private RoleForCompanyRepository roleForCompanyRepository;

	@Autowired
	private CommonService comService;
	
//	@Autowired
//	private MessageSource messageSource;
	
	@Autowired
	private SystemConfig systemConfig;

	@Override
	public List<JcaRoleForCompanyDto> getListRoleForCompany(Long roleId) {
	    return null;
		// TODO Auto-generated method stub
//		return roleForCompanyRepository.findRoleForCompanyByRoleId(roleId, UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
	}

	@Override
	public Boolean saveRoleForCompany(RoleForCompanyEditDto roleCompany, Locale locale){
	    Boolean rs = false;
		//String userNameLogin = UserProfileUtils.getUserNameLogin();
		Date systemDate = comService.getSystemDateTime();
		List<JcaRoleForCompanyDto> roleCompanyDtoList = roleCompany.getData();
		Long roleId = roleCompany.getRoleId();
		try {
		    for (JcaRoleForCompanyDto roleCompanyDto : roleCompanyDtoList) {
//		        Long id = roleCompanyDto.getCompanyId();
//		        Long id = roleCompanyDto.getId();
		        JcaRoleForCompany roleForCompany = new JcaRoleForCompany();
		        roleForCompany.setCompanyId(roleCompanyDto.getCompanyId());
                roleForCompany.setOrgId(roleCompanyDto.getOrgId());
                roleForCompany.setRoleId(roleId);
                roleForCompany.setIsAdmin(roleCompanyDto.getIsAdmin());
                
		        JcaRoleForCompanyDto findRoleCompanyDto = roleForCompanyRepository.findByCondition(roleId, roleCompanyDto.getCompanyId(), roleCompanyDto.getOrgId());
		        if(findRoleCompanyDto != null) {
		            roleForCompanyRepository.update(roleForCompany);
		        }else {
		            roleForCompany.setCreatedId(UserProfileUtils.getAccountId());
		            roleForCompany.setCreatedDate(systemDate);
		            roleForCompanyRepository.create(roleForCompany);
		        }
                
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
        JcaRoleForCompany roleForCompany = roleForCompanyRepository.findOne(id);
        if(null!=roleForCompany) {

            roleForCompanyRepository.update(roleForCompany); 
        }
    }
    
    @Override
    public String validateRoleForCompay(RoleForCompanyEditDto roleCompany, Locale locale) {
        String error = null;
//        List<JcaRoleForCompanyDto> roleForCompanyDtos = roleCompany.getData();
//        if (roleForCompanyDtos != null) {
//            // check org
//            
//              for (JcaRoleForCompanyDto roleForCompanyDto : roleForCompanyDtos) { if (roleForCompanyDto.getOrgId() == null) { return
//              messageSource.getMessage("role.for.company.org.null", null, locale); } }
//             
//            // check dup
//            int size = roleForCompanyDtos.size() - 1;
//            for (int i = 0; i < size; i++) {
//                Long companyId = roleForCompanyDtos.get(i).getCompanyId();
//                Long companyIdNext = roleForCompanyDtos.get(i + 1).getCompanyId();
//                Long orgId = roleForCompanyDtos.get(i).getOrgId();
//                Long orgIdNext = roleForCompanyDtos.get(i + 1).getOrgId();
//
//                if (companyId.equals(companyIdNext)
//                        && (orgId == orgIdNext || (orgId != null && orgIdNext != null && orgId.equals(orgIdNext)))) {
//                    error = messageSource.getMessage("role.for.company.exist", null, locale);
//                }
//            }
//        }
//        //List<JcaRoleForCompanyDto> roleForCompanyDtos = roleCompany.getData();
//        if (CollectionUtils.isNotEmpty(roleForCompanyDtos)) {
//            JcaRoleForCompanyDto roleForCompanyNew = roleForCompanyDtos.stream().filter(f -> f.getRoleId() == 0L).findFirst().orElse(null);
//            if (null != roleForCompanyNew) {
//                JcaRoleForCompanyDto roleForCompanyDto = roleForCompanyRepository.findByCondition(roleForCompanyNew.getRoleId(),
//                        roleForCompanyNew.getCompanyId(), roleForCompanyNew.getOrgId());
//                if (null != roleForCompanyDto) {
//                    error = messageSource.getMessage("role.for.company.exist", null, locale);
//                }
//            }
//        }
        return error;
    }

    @Override
    public PageWrapper<JcaRoleForCompanyDto> getListRoleForCompanyPageWrapper(int page, int pageSize, Long roleId) {
        // Init PageWrapper
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaRoleForCompanyDto> pageWrapper = new PageWrapper<JcaRoleForCompanyDto>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        if(null != roleId) {
            int count = roleForCompanyRepository.countRoleForCompanyByRoleId(roleId, UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());

            List<JcaRoleForCompanyDto> dataList = new ArrayList<>();
            if (count > 0) {
                int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
                dataList = roleForCompanyRepository.getListRoleForCompanyPageWrapper(offsetSQL, sizeOfPage, roleId, UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
            }
            pageWrapper.setDataAndCount(dataList, count);
        }
        return pageWrapper;
    }

    @Override
    public void deleteByCompanyIdAndOrgIdAndRoleId(Long companyId, Long orgId, Long roleId) {
        roleForCompanyRepository.deleteByCompanyIdAndOrgIdAndRoleId(roleId, companyId, orgId);
    }
}
