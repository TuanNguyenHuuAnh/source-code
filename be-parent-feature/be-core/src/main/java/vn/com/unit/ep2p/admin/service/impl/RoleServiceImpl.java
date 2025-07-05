/*******************************************************************************
 * Class        RoleService
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.core.enumdef.param.JcaRoleSearchEnum;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.impl.JcaRoleServiceImpl;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.JcaRoleAddDto;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.admin.exception.BusinessException;
import vn.com.unit.ep2p.admin.repository.AppAuthorityRepository;
import vn.com.unit.ep2p.admin.repository.RoleRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.RoleService;
import vn.com.unit.ep2p.dto.CommonSearchDto;
import vn.com.unit.ep2p.dto.CompanyDto;

/**
 * RoleService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service("webappRoleService")
@Transactional(readOnly = true)
public class RoleServiceImpl extends JcaRoleServiceImpl implements RoleService,AbstractCommonService {

	private static final String CODE = "CODE";
	private static final String NAME = "NAME";
	private static final String DESCRIPTION = "DESCRIPTION";
	private static final String[] ROLE_SEARCH_FIELD_DISP_NAMES = { "searchfield.role.code", "searchfield.role.name",
			"searchfield.role.description" };
	private static final String[] ROLE_SEARCH_FIELD_IDS = { CODE, NAME, DESCRIPTION };
	
	// private static final Logger logger =
	// LoggerFactory.getLogger(RoleServiceImpl.class);

	/** RoleRepository */
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MessageSource msg;

	/** AccountDepartmentService */
	// @Autowired
	// private AccountOrgService accOrgService;

	// @Autowired
	// private RoleForOrganizationRepository roleForOrgRepository;

	@Autowired
	private AppAuthorityRepository authorityRepository;

//	@Autowired
//	private ManualService manualService;

	/** SystemConfig */
	@Autowired
	private SystemConfig systemConfig;

	/** ConstantDisplayService */
//	@Autowired
//	private JcaConstantService jcaConstantService;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
    private CommonService comService;
	
//	@Autowired
//	private DelegateService delegateService; 
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Find all role from authority.
	 * 
	 * @param account
	 *            : Account
	 * @return listGrantedAuthority : List<GrantedAuthority>
	 * @author trieunh <trieunh@unit.com.vn>
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaRole create(JcaRoleAddDto roleAddDto) {
		// Create role
		JcaRole role = new JcaRole();

		// set code
		role.setCode(roleAddDto.getCode());

		// set name
		role.setName(roleAddDto.getName());

		// set description
		role.setDescription(roleAddDto.getDescription());

		// set active
		role.setActived(roleAddDto.isActive());

		// set default 1 for display
//		role.setRoleType(ConstantCore.STR_ONE);

		// set created at
		role.setCreatedDate(comService.getSystemDateTime());

		// string create by =
//		role.setCreatedBy(UserProfileUtils.getUserNameLogin());
		
		// Add company_id
		role.setCompanyId(roleAddDto.getCompanyId());

		return roleRepository.create(role);
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRole createWithRoleType(JcaRoleAddDto roleAddDto) {
        // Create role
	    JcaRole role = new JcaRole();

        // set code
        role.setCode(roleAddDto.getCode());

        // set name
        role.setName(roleAddDto.getName());

        // set description
        role.setDescription(roleAddDto.getDescription());

        // set active
        role.setActived(roleAddDto.isActive());

        // set role type
//        role.setRoleType(roleAddDto.getRoleType());

        // set created at
        role.setCreatedDate(comService.getSystemDateTime());

        // string create by =
//        role.setCreatedBy(UserProfileUtils.getUserNameLogin());

        return roleRepository.create(role);
    }

	@Transactional(rollbackFor = Exception.class)
	public void update(RoleEditDto roleEditDto) {

		// get data
	    JcaRole role = null;

		Long roleId = roleEditDto.getId();

		if (roleId != null) {
			role = roleRepository.findOne(roleId);
		}

		if (role == null) {
			throw new BusinessException("Not found role by id: " + roleId);
		}

		// set name
		String name = roleEditDto.getName();
		role.setName(name);

		// set description
		String description = roleEditDto.getDescription();
		role.setDescription(description);

		// set active
		boolean active = roleEditDto.isActived();
		role.setActived(active);

		// Set updated at
		role.setUpdatedDate(comService.getSystemDateTime());

//		String usernameLogin = UserProfileUtils.getUserNameLogin();
//		role.setUpdatedBy(usernameLogin);

		// Add company_id
		role.setCompanyId(roleEditDto.getCompanyId());
		roleRepository.update(role);
	}
	
	@Transactional(rollbackFor = Exception.class)
    public void updateWithRoleType(RoleEditDto roleEditDto) {

        // get data
	    JcaRole role = null;

        Long roleId = roleEditDto.getId();

        if (roleId != null) {
            role = roleRepository.findOne(roleId);
        }

        if (role == null) {
            throw new BusinessException("Not found role by id: " + roleId);
        }

        // set name
        String name = roleEditDto.getName();
        role.setName(name);

        // set description
        String description = roleEditDto.getDescription();
        role.setDescription(description);

        // set active
        boolean active = roleEditDto.isActived();
        role.setActived(active);

        // Set updated at
        role.setUpdatedDate(comService.getSystemDateTime());
        
        // set role type
//        String roleType = roleEditDto.getRoleType();
//        role.setRoleType(roleType);
        
//        String usernameLogin = UserProfileUtils.getUserNameLogin();
//        role.setUpdatedBy(usernameLogin);

        roleRepository.update(role);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteRole(Long id) {
		// Get data
	    JcaRole role = roleRepository.findOne(id);

		if (null == role) {
			throw new BusinessException("Not found Role with id: " + id);
		}
		this.deletedJcaRoleById(id);
		// Set delete
		//role.setDeletedDate(comService.getSystemDateTime());
//		role.setDeletedBy(UserProfileUtils.getUserNameLogin());

		//roleRepository.update(role);
	}

	@Override
	@Transactional(readOnly = true)
	public JcaRole findByCodeAndCompanyId(String code, Long companyId) {
		return roleRepository.findByCode(code, companyId);
	}

	@Override
	@Transactional(readOnly = true)
	public RoleEditDto findRoleEditDtoById(Long id) {
		RoleEditDto roleEditDto = null;

		if (id != null) {
		    JcaRole role = roleRepository.findOne(id);

			if (role != null) {
				roleEditDto = this.roleToRoleEditDto(role);
			}
		}

		return roleEditDto;
	}

	@Override
	@Transactional(readOnly = true)
	public RoleEditDto roleToRoleEditDto(JcaRole role) {
		RoleEditDto roleEditDto = new RoleEditDto();

		Long id = role.getId();
		roleEditDto.setId(id);

		roleEditDto.setCode(role.getCode());

		String name = role.getName();
		roleEditDto.setName(name);

		String description = role.getDescription();
		roleEditDto.setDescription(description);

		boolean active = role.isActived();
		roleEditDto.setActived(active);
		
//		String roleType = role.getRoleType();
//		roleEditDto.setRoleType(roleType);
		
		// Add company_id
		roleEditDto.setCompanyId(role.getCompanyId());
        CompanyDto company = companyService.findById(role.getCompanyId());
        if (null != company) {
            roleEditDto.setCompanyName(company.getName());
        }
		return roleEditDto;
	}
	
    @Override
    @Transactional(readOnly = true)
    public PageWrapper<JcaRoleDto> search(CommonSearchDto searchDto, int page, int pageSize) throws DetailException {
        // Init PageWrapper
        PageWrapper<JcaRoleDto> pageWrapper = new PageWrapper<JcaRoleDto>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);

        String searchValue = searchDto.getSearchValue() == null ? null : searchDto.getSearchValue().replace(" ", "");
        if (searchValue != null) {
            searchDto.setSearchValue(searchValue.replace(" ", ""));
        }

        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaRole.class,
                TABLE_ALIAS_JCA_ROLE);

        /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaRoleSearchDto reqSearch = this.buildJcaRoleSearchDto(commonSearch);

        int count = this.countRoleByCondition(reqSearch);
        List<JcaRoleDto> roleList = new ArrayList<JcaRoleDto>();
        if (count > 0) {
            roleList = this.getRoleByCondition(reqSearch, pageableAfterBuild);
        }

        pageWrapper.setDataAndCount(roleList, count);

        return pageWrapper;
    }

    @Override
    @Transactional(readOnly = true)
    public PageWrapper<JcaRoleDto> searchWithRoleType(int page, CommonSearchDto searchDto) throws DetailException {
        // Init PageWrapper
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        if (searchDto == null) {
            searchDto = new CommonSearchDto();
        }
        String searchValue = searchDto.getSearchValue() == null ? null : searchDto.getSearchValue().replace(" ", "");
        if (searchValue != null) {
            searchDto.setSearchValue(searchValue.replace(" ", ""));
        }
        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaRole.class, TABLE_ALIAS_JCA_ROLE);

        /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaRoleSearchDto reqSearch = this.buildJcaRoleSearchDto(commonSearch);
        PageWrapper<JcaRoleDto> pageWrapper = new PageWrapper<JcaRoleDto>(page, sizeOfPage);

        int count = this.countRoleByCondition(reqSearch);
        List<JcaRoleDto> roleList = new ArrayList<JcaRoleDto>();
        if (count > 0) {
            roleList = this.getRoleByCondition(reqSearch, pageableAfterBuild);
        }

        pageWrapper.setDataAndCount(roleList, count);
        return pageWrapper;
    }

	 private JcaRoleSearchDto buildJcaRoleSearchDto(MultiValueMap<String, String> commonSearch) {
	        JcaRoleSearchDto reqSearch = new JcaRoleSearchDto();

	        String keySearch = null != commonSearch.getFirst("searchValue") ? commonSearch.getFirst("searchValue") : DtsConstant.EMPTY;
	        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
	        Boolean active = null != commonSearch.getFirst("active") ? Boolean.valueOf(commonSearch.getFirst("active")) : null;
	        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("searchKeyIds"))
	                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("searchKeyIds"), ","))
	                : null;
	        reqSearch.setActive(active);
	        reqSearch.setCompanyId(companyId);
	        if (companyId!= null && companyId==0) {
	            reqSearch.setCompanyIdList(UserProfileUtils.getCompanyIdList());
            }
	        
	        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
	            for (String enumValue : enumsValues) {
	                switch (JcaRoleSearchEnum.valueOf(enumValue)) {
	                case CODE:
	                    reqSearch.setCode(keySearch);
	                    break;
	                case NAME:
	                    reqSearch.setName(keySearch);
	                    break;
	                    
	                case DESCRIPTION:
	                    reqSearch.setDescription(keySearch);
	                    break;

	                default:
	                    reqSearch.setCode(keySearch);
	                    reqSearch.setName(keySearch);
	                    reqSearch.setDescription(keySearch);
	                    break;
	                }
	            }
	        }else {
	            reqSearch.setCode(keySearch);
	            reqSearch.setName(keySearch);
	            reqSearch.setDescription(keySearch);
	        }
	        
	        return reqSearch;
	    }

	@Override
	@Transactional(readOnly = true)
	public List<SearchKeyDto> genSearchKeyList(Locale locale) {
		List<SearchKeyDto> searchKeys = CommonSearchUtil.genSearchKeyList(ROLE_SEARCH_FIELD_IDS, ROLE_SEARCH_FIELD_DISP_NAMES,
				locale, msg);
		return searchKeys;
	}

	@Override
	@Transactional(readOnly = true)
	public void initScreenRoleList(ModelAndView mav) {
		// Init account status
//		List<ConstantDisplay> statusList = constDispService.findByType(ConstantDisplayType.M04);
//		mav.addObject("statusList", statusList);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleEditDto> findRoleListByRoleType(String roleType, Long companyId, boolean companyAdmin) {
		return roleRepository.findRoleListByRoleType(roleType, companyId, companyAdmin);
	}
	
    @Override
    @Transactional(readOnly = true)
    public int checkRoleUsedByGroup(Long id) {
        return roleRepository.checkRoleUsedByGroup(id);
    }

	@Override
    @Transactional(readOnly = true)
	public List<RoleEditDto> findRoleListByRoleTypeForProcess(String roleType, Long companyId, boolean companyAdmin) {
		return roleRepository.findRoleListByRoleTypeForProcess(roleType, companyId, companyAdmin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoleEditDto> findRoleListByCompanyId(Long companyId) {
		return roleRepository.findRoleListByCompanyId(companyId);
	}

    @Override
    public List<JcaAuthorityDto> getAllRoleForAccountByAccountId(long accountId) {
        return authorityRepository.findAllRoleForAccountByAccountId(accountId);
    }
    
    @Override
    public List<GrantedAuthority> addAuthorityDelegate(JcaAccount account) {

        List<GrantedAuthority> listGrantedAuthority =  new ArrayList<>();

//        Long id = account.getId();
//        List<String> listFunction = delegateService.getFunctionListDelegateByDelegator(id);
//
//        if (null != listFunction && !listFunction.isEmpty()){
//            for (String function : listFunction) {
//                if (StringUtils.isNotBlank(function)) {
//                    // logger.debug("ROLE_FUNCTION : " +
//                    // authorityDto.findFunction());
//                    listGrantedAuthority.add(new SimpleGrantedAuthority(function));
//                }
//            }
//        }
        return listGrantedAuthority;
    }

    @Transactional(readOnly = true)
    @Override
    public Select2ResultDto getListSelect2Dto(String key, Long accountId, Long companyId, boolean isPaging) throws SQLException {
        Select2ResultDto obj = new Select2ResultDto();
        List<Select2Dto> result = roleRepository.findListSelect2Dto(key, accountId, companyId, isPaging);
        if(result == null) {
            result = new ArrayList<>();
        }
        obj.setTotal(result.size());
        obj.setResults(result);
        return obj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.admin.service.AbstractCommonService#getCommonService()
     */
    @Override
    public vn.com.unit.common.service.JCommonService getCommonService() {
        return commonService;
    }

}
