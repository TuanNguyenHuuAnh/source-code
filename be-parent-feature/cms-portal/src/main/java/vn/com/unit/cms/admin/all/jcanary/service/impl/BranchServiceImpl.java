/*******************************************************************************
 * Class        BranchServiceImpl
 * Created date 2017/03/10
 * Lasted date  2017/03/10
 * Author       TranLTH
 * Change log   2017/03/1001-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.service.impl;

import java.util.ArrayList;
import java.util.Date;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchSearchDto;
import vn.com.unit.cms.admin.all.jcanary.entity.Branch;
import vn.com.unit.cms.admin.all.jcanary.enumdef.BranchSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.repository.BranchRepository;
import vn.com.unit.cms.admin.all.jcanary.service.BranchService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.config.SystemConfig;
//import vn.com.unit.ep2p.admin.enumdef.DatabaseTypeEnum;
//import vn.com.unit.ep2p.admin.service.ManualService;
import vn.com.unit.ep2p.admin.exception.BusinessException;

//import vn.com.unit.exception.BusinessException;
//import vn.com.unit.jcanary.authentication.UserProfileUtils;
//import vn.com.unit.jcanary.common.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.dto.BranchSearchDto;
//import vn.com.unit.jcanary.entity.Branch;
//import vn.com.unit.jcanary.entity.ConstantDisplay;
//import vn.com.unit.jcanary.enumdef.BranchSearchEnum;
//import vn.com.unit.jcanary.enumdef.ConstDispType;
//import vn.com.unit.jcanary.repository.BranchRepository;
//import vn.com.unit.jcanary.service.BranchService;
//import vn.com.unit.jcanary.service.CityService;
//import vn.com.unit.jcanary.service.ConstantDisplayService;
//import vn.com.unit.jcanary.service.FileService;
//import vn.com.unit.jcanary.service.ManualService;

/**
 * BranchServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	SystemConfig systemConfig;

//    @Autowired
//    private ConstantDisplayService constDispService;
//
//    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

//    @Autowired
//    ManualService manualService;

    @Autowired
    CmsFileService fileService;
	
    @Autowired
	private JcaConstantService jcaConstantService;

	@Override
	public List<BranchDto> searchBranchByCondition(BranchDto branchDto) {
		return branchRepository.findBranchListByCondition(branchDto);
	}

    @Override
    @Transactional
    public void addOrEditBranch(BranchDto branchDto) throws Exception {
        String usernameLogin = "";
        if (UserProfileUtils.getUserNameLogin() != null) {
            usernameLogin = UserProfileUtils.getUserNameLogin();
        } else {
            usernameLogin = "admin";
        }
        Long branchId = branchDto.getId();
        Branch updateBranch = new Branch();
        // edit branch else add new branch
        if (null != branchId) {
            updateBranch = branchRepository.findOne(branchId);
            if (null == updateBranch) {
                throw new BusinessException("Not found Branch with id=" + branchId);
            }
            updateBranch.setUpdateBy(usernameLogin);
            updateBranch.setUpdateDate(new Date());
        } else {
            updateBranch.setCreateBy(usernameLogin);
            updateBranch.setCreateDate(new Date());
        }
        updateBranch.setAddress(branchDto.getAddress().trim());
        updateBranch.setCity(branchDto.getCity().trim());
        updateBranch.setCode(branchDto.getCode().toUpperCase().trim());
        updateBranch.setDistrict(branchDto.getDistrict().trim());
        updateBranch.setFax(branchDto.getFax().trim());
        // upload images
        String iconModel = branchDto.getIcon();
        if (StringUtils.isNotEmpty(iconModel)) {
            String iconNew = fileService.moveFileFromTempToFolderUploadMain(iconModel, "icon/");
            updateBranch.setIcon(iconNew.trim());
        }
        updateBranch.setLatitude(branchDto.getLatitude().trim());
        updateBranch.setLongtitude(branchDto.getLongtitude().trim());
        updateBranch.setName(branchDto.getName().trim());
        updateBranch.setNote(branchDto.getNote().trim());
        updateBranch.setPhone(branchDto.getPhone().trim());
        updateBranch.setIs_primary(branchDto.getIsPrimary() != null ? branchDto.getIsPrimary() : false);
        updateBranch.setType(branchDto.getType().trim());
        updateBranch.setWorkingHours(branchDto.getWorkingHours());
        updateBranch.setEmail(branchDto.getEmail()!= null ? branchDto.getEmail() : "");
        
        // Set enabled
        boolean activeFlag = branchDto.getActiveFlag();
        updateBranch.setActiveFlag(activeFlag ? "1" : "0");

        try {
            branchRepository.save(updateBranch);
            branchDto.setId(updateBranch.getId());
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    @Override
    @Transactional
    public void deleteBranch(Long branchId) throws Exception {
        Branch deleteBranch = new Branch();
        if (null != branchId) {
            deleteBranch = branchRepository.findOne(branchId);
        }
        deleteBranch.setDeleteDate(new Date());
        String usernameLogin = UserProfileUtils.getUserNameLogin();
        deleteBranch.setDeleteBy(usernameLogin);
        try {
            branchRepository.save(deleteBranch);
        } catch (Exception ex) {
            throw new Exception(ex);
        }

    }

	@Override
	public BranchDto getBranchDto(Long branchId) {
		BranchDto branchDto = new BranchDto();
		if (null == branchId) {
			return branchDto;
		}
		return branchRepository.findBranchDtoById(branchId);
	}

	private enum DBType {
		SQLSERVER, MYSQL, ORACLE;
	}

	public List<BranchDto> findBranchLimitByCondition(int offset, int sizeOfPage, BranchSearchDto branchSearchDto) {
		List<BranchDto> list = new ArrayList<BranchDto>();

		// DatabaseTypeEnum.ORACLE.toString()

		DBType dataType = DBType.valueOf(systemConfig.getConfig(SystemConfig.DBTYPE));
		switch (dataType) {
		case SQLSERVER:
			list = branchRepository.findBranchLimitByConditionSQLServer(offset, sizeOfPage, branchSearchDto);
			break;
		case MYSQL:
			list = branchRepository.findBranchLimitByConditionMYSQL(offset, sizeOfPage, branchSearchDto);
			break;
		case ORACLE:
			list = branchRepository.findBranchLimitByConditionSQLServer(offset, sizeOfPage, branchSearchDto);
			break;
		default:
			break;
		}
		return list;
	}

	@Override
	public PageWrapper<BranchDto> search(int page, BranchSearchDto branchSearchDto) {
		int sizeOfPage = branchSearchDto.getPageSize() != null ? branchSearchDto.getPageSize()
				: systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
		PageWrapper<BranchDto> pageWrapper = new PageWrapper<BranchDto>(page, sizeOfPage);
		// set SearchParm
		setSearchParm(branchSearchDto);

		int count = branchRepository.countBranchByCondition(branchSearchDto);

		List<BranchDto> result = new ArrayList<BranchDto>();
		if (count > 0) {
			int currentPage = pageWrapper.getCurrentPage();
			int startIndex = (currentPage - 1) * sizeOfPage;

//            result = manualService.findBranchLimitByCondition(startIndex, sizeOfPage, branchSearchDto);
			result = findBranchLimitByCondition(startIndex, sizeOfPage, branchSearchDto);
		}

		pageWrapper.setDataAndCount(result, count);
		return pageWrapper;
	}

	@Override
	public BranchDto findByCode(String code) {
		return branchRepository.findByCode(code);
	}

	private void setSearchParm(BranchSearchDto branchSearchDto) {
		if (null == branchSearchDto.getFieldValues()) {
			branchSearchDto.setFieldValues(new ArrayList<String>());
		}

		if (branchSearchDto.getFieldValues().isEmpty()) {
			branchSearchDto.setCode(branchSearchDto.getFieldSearch() != null ? branchSearchDto.getFieldSearch().trim()
					: branchSearchDto.getFieldSearch());
			branchSearchDto.setName(branchSearchDto.getFieldSearch() != null ? branchSearchDto.getFieldSearch().trim()
					: branchSearchDto.getFieldSearch());
			branchSearchDto
					.setAddress(branchSearchDto.getFieldSearch() != null ? branchSearchDto.getFieldSearch().trim()
							: branchSearchDto.getFieldSearch());
			branchSearchDto.setPhone(branchSearchDto.getFieldSearch() != null ? branchSearchDto.getFieldSearch().trim()
					: branchSearchDto.getFieldSearch());
		} else {
			for (String field : branchSearchDto.getFieldValues()) {
				if (StringUtils.equals(field, BranchSearchEnum.CODE.name())) {
					branchSearchDto.setCode(branchSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, BranchSearchEnum.NAME.name())) {
					branchSearchDto.setName(branchSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, BranchSearchEnum.ADDRESS.name())) {
					branchSearchDto.setAddress(branchSearchDto.getFieldSearch().trim());
					continue;
				}
				if (StringUtils.equals(field, BranchSearchEnum.PHONE.name())) {
					branchSearchDto.setPhone(branchSearchDto.getFieldSearch().trim());
					continue;
				}
			}
		}
	}

    @Override
    public void initScreenTypeList(ModelAndView mav) {
        // Init career
    	
    	
		// ${constantDisplay.cat} => ${constantDisplay.kind}
		// #{${constantDisplay.code}} => #{${constantDisplay.code}}
		// constDispService.findByType("M10");
		// => List<JcaConstantDto> statusList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(), "EN");

		// type => groupCode
		// cat	=> kind
		// code => code
		
		// catOfficialName => name
		
		// ConstantDisplay motive = constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString());
		// JcaConstantDto motive = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(), emailModel.getMotive().toString(), "EN").get(0);

		// List<ConstantDisplay> listBannerPage = constDispService.findByType(ConstDispType.B01);
    	// List<JcaConstantDto> listBannerPage = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");

//        List<ConstantDisplay> typeList = constDispService.findByType(ConstDispType.M09);

    	List<JcaConstantDto> typeList = jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M09.toString(), "EN");

        mav.addObject("typeList", typeList);
    }

//    /**
//     * findBranchListByTypeAndCity
//     * 
//     * @param locationType
//     * @param cityName
//     * @param keyword
//     * @return List<BranchDto>
//     * @author hand
//     */
//    @Override
//    public List<BranchDto> findBranchListByTypeAndCity(String locationType, String cityName, String keyword) {
//        List<BranchDto> resultList = new ArrayList<BranchDto>();
//
//        try {
//            resultList = branchRepository.findBranchListByTypeAndCity(locationType, cityName, keyword);
//        } catch (Exception e) {
//            logger.error(e + ":" + e.getMessage());
//        }
//        return resultList;
//    }

}