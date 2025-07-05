/*******************************************************************************
 * Class        :CompanyServiceImpl
 * Created date :2019/05/07
 * Lasted date  :2019/05/07
 * Author       :HungHT
 * Change log   :2019/05/07:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaCompanySearchDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaCompany;
import vn.com.unit.core.entity.JcaSystemConfig;
import vn.com.unit.core.enumdef.CompanyUploadFileTypeEnum;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.core.service.impl.JcaCompanyServiceImpl;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.dto.ResultDto;
import vn.com.unit.ep2p.admin.enumdef.CompanySearchEnum;
import vn.com.unit.ep2p.admin.enumdef.ResultStatus;
import vn.com.unit.ep2p.admin.repository.CompanyRepository;
import vn.com.unit.ep2p.admin.repository.SystemSettingRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.MenuService;
import vn.com.unit.ep2p.constant.AppSystemSettingKey;
import vn.com.unit.ep2p.dto.CompanyDto;
import vn.com.unit.ep2p.dto.CompanySearchDto;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;

/**
 * CompanyServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class CompanyServiceImpl extends JcaCompanyServiceImpl implements CompanyService, AbstractCommonService{

    private SystemConfig systemConfig;

    @Autowired
    private CompanyRepository companyRepository;
    
//    @Autowired
//    private JcaCompanyService jcaCompanyService;

    @Autowired
    private MessageSource msg;
    
	// Model mapper
    private ModelMapper modelMapper = new ModelMapper();
    
//	@Autowired
//	private MenuService menuService;
	
	@Autowired
    private CommonService comService;
	
//    @Autowired
//    private JRepositoryService repositoryService;
    
    @Autowired
    private FileStorageService fileStorageService;

    // @Autowired
    // private OrganizationService organizationService;

//    @Autowired
//    private OrgInfoRepository orgInfoRepository;
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private SystemSettingRepository systemSettingRepository;

    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    JcaSystemConfigService jcaSystemConfigService;

    // @Autowired
    // private JpmBusinessService jpmBusinessService;

    // private final static Long ORG_PARENT_ID = 0L;
    // private final static String ORG_TYPE = "C";
    // private final static String ORG_SUB_TYPE1 = "PQHKH";
    
    public static final String COMPANY_IMAGE_FOLDER = "company_image/";

    /**
     * getCompanyList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    public PageWrapper<JcaCompanyDto> getCompanyList(CompanySearchDto searchDto, int pageSize, int page)
            throws DetailException {
        // Init PageWrapper
        PageWrapper<JcaCompanyDto> pageWrapper = new PageWrapper<JcaCompanyDto>();
        int sizeOfPage = systemConfig.settingPageSizeList(pageSize, pageWrapper, page);

        // Set param search
        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaAccount.class,
                TABLE_ALIAS_JCA_COMPANY);

        List<String> values = searchDto.getFieldValues();
        /** init param search repository */
        if (CollectionUtils.isNotEmpty(searchDto.getFieldValues())) {
            searchDto.setStrFieldValues(String.join(",", searchDto.getFieldValues()));
            searchDto.setFieldValues(null);
        } else {
            searchDto.setFieldValues(null);
        }
        
        /** init param search repository */
        //MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaCompanySearchDto reqSearch = this.buildJcaCompanySearchDto(searchDto, values);
        searchDto.setFieldValues(values);
        
        int count = this.countCompanyByCondition(reqSearch);
        List<JcaCompanyDto> result = new ArrayList<>();
        if (count > 0) {
            result = this.getCompanyByCondition(reqSearch, pageableAfterBuild);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }
    
    private JcaCompanySearchDto buildJcaCompanySearchDto(CompanySearchDto commonSearch,  List<String> values) {
        JcaCompanySearchDto reqSearch = new JcaCompanySearchDto();

        String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFieldSearch()) ? commonSearch.getFieldSearch()
                : DtsConstant.EMPTY;
        
        if(values != null) {
            for (String enumValue : values) {
                switch (CompanySearchEnum.valueOf(enumValue)) {
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
                case DESCRIPTION:
                    reqSearch.setDescription(keySearch);
                    break;
                case SYSTEM_CODE:
                    reqSearch.setSystemCode(keySearch);
                    break;
                case SYSTEM_NAME:
                    reqSearch.setSystemName(keySearch);
                    break;

                default:
                    reqSearch.setName(keySearch);
                    reqSearch.setDescription(keySearch);
                    reqSearch.setSystemCode(keySearch);
                    reqSearch.setSystemName(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setName(keySearch);
            reqSearch.setDescription(keySearch);
            reqSearch.setSystemCode(keySearch);
            reqSearch.setSystemName(keySearch);
        }
        
        return reqSearch;
    }
    
    /**
     * setSearchParm
     * @param search
     * @author HungHT
     */
//    private void setSearchParm(CompanySearchDto search) {
//        if (null == search.getFieldValues()) {
//            search.setFieldValues(new ArrayList<String>());
//        }
//
//        if (search.getFieldValues().isEmpty()) {
//            search.setName(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
//            search.setDescription(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
//            search.setSystemCode(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
//            search.setSystemName(search.getFieldSearch() != null ? search.getFieldSearch().trim() : search.getFieldSearch());
//        } else {
//            for (String field : search.getFieldValues()) {
//                if (StringUtils.equals(field, CompanySearchEnum.NAME.name())) {
//                    search.setName(search.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, CompanySearchEnum.DESCRIPTION.name())) {
//                    search.setDescription(search.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, CompanySearchEnum.SYSTEM_CODE.name())) {
//                    search.setSystemCode(search.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, CompanySearchEnum.SYSTEM_NAME.name())) {
//                    search.setSystemName(search.getFieldSearch().trim());
//                    continue;
//                }
//            }
//        }
//    }

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    public CompanyDto findById(Long id) {
//        return companyRepository.findById(id);
    	CompanyDto result = new CompanyDto();
    	JcaCompanyDto jcaCompanyDto = this.getJcaCompanyDtoById(id);
    	if(null != jcaCompanyDto) {
    		result = this.convertJcaCompanyDtoToCompanyDto(jcaCompanyDto);
    	}
    	return result;
    }

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    public void initScreenDetail(ModelAndView mav, CompanyDto objectDto, Locale locale) {
        List<Select2Dto> listStyle = new ArrayList<>();
        Select2Dto obj = new Select2Dto("bg", "static/images/tpl_hdbank.jpg", "HDBank");
        listStyle.add(obj);
        obj = new Select2Dto("bg-vietjet", "static/images/tpl_vietjet.jpg", "Vietjets");
        listStyle.add(obj);
        obj = new Select2Dto("bg-phulong", "static/images/tpl_phulong.jpg", "PhuLong");
        listStyle.add(obj);
        mav.addObject("listStyle", listStyle);
    }

    /**
     * saveCompany
     * 
     * @param objectDto
     * @param locale
     * @return
     * @throws Exception
     * @author HungHT
     */
    public ResultDto saveCompany(CompanyDto objectDto, Locale locale) throws Exception {
        ResultDto result = new ResultDto();
        if(objectDto.getExpiredDate() != null && objectDto.getEffectedDate() !=null) {
	        if(objectDto.getExpiredDate().before(objectDto.getEffectedDate())) {
	        	result.setStatus(ResultStatus.FAIL.toInt());
	            result.setMessage(msg.getMessage("message.error.serial.info.expired", null, null));
	            result.setId(objectDto.getId());
	            return result;
	        }
        }
        if (!objectDto.isScreenConfig() && null != companyRepository.findByCondition(null, objectDto.getSystemCode(), objectDto.getId())) {
            result.setStatus(ResultStatus.FAIL.toInt());
            result.setMessage(msg.getMessage("company.system.code.existed", null, null));
            result.setId(objectDto.getId());
            return result;
        }     
		if (objectDto.getFileLoginBackground() != null) {
			BufferedImage bImg = ImageIO.read(objectDto.getFileLoginBackground().getInputStream());
			Integer width = bImg.getWidth();
			Integer height = bImg.getHeight();

			if (width < 267 || height < 119) {
				result.setStatus(ResultStatus.FAIL.toInt());
				result.setMessage(msg.getMessage("company.image.more.than", null, null));
				result.setId(objectDto.getId());
				return result;
			}
		}
		
		if (objectDto.getFileShortcutIcon() != null) {
			BufferedImage bImg1 = ImageIO.read(objectDto.getFileShortcutIcon().getInputStream());
			Integer width1 = bImg1.getWidth();
			Integer height1 = bImg1.getHeight();

			if (width1 < 32 || height1 < 32) {
				result.setStatus(ResultStatus.FAIL.toInt());
				result.setMessage(msg.getMessage("company.image.more.than", null, null));
				result.setId(objectDto.getId());
				return result;
			}
		}
		
		if (objectDto.getFileLogoLarge() != null) {
			BufferedImage bImg2 = ImageIO.read(objectDto.getFileLogoLarge().getInputStream());
			Integer width2 = bImg2.getWidth();
			Integer height2 = bImg2.getHeight();
	
			if(width2 < 267 || height2 < 119) {
				result.setStatus(ResultStatus.FAIL.toInt());
				result.setMessage(msg.getMessage("company.image.more.than", null, null));
				result.setId(objectDto.getId());
				return result;
			}
		}
		
		if (objectDto.getFileLogoMini() != null) {
			BufferedImage bImg3 = ImageIO.read(objectDto.getFileLogoMini().getInputStream());
			Integer width3 = bImg3.getWidth();
			Integer height3 = bImg3.getHeight();

			if (width3 < 61 || height3 < 51) {
				result.setStatus(ResultStatus.FAIL.toInt());
				result.setMessage(msg.getMessage("company.image.more.than", null, null));
				result.setId(objectDto.getId());
				return result;
			}
		}

        JcaCompanyDto jcaCompanyDto = this.convertCompanyDtoToJcaCompanyDto(objectDto);
        this.saveFiles(objectDto.getFileLoginBackground(), jcaCompanyDto, CompanyUploadFileTypeEnum.LOGIN_BACKGROUND);
        this.saveFiles(objectDto.getFileShortcutIcon(), jcaCompanyDto, CompanyUploadFileTypeEnum.SHORTCUT_ICON);
        this.saveFiles(objectDto.getFileLogoLarge(), jcaCompanyDto, CompanyUploadFileTypeEnum.LOGO_LARGE);
        this.saveFiles(objectDto.getFileLogoMini(), jcaCompanyDto, CompanyUploadFileTypeEnum.LOGO_MINI);
        JcaCompanyDto objectSave = new JcaCompanyDto();
        Long companyId = objectDto.getId();
        boolean buildMenu = false;
        if(null != companyId) {
        	objectSave = this.getJcaCompanyDtoById(companyId);
        } else {
            buildMenu = true;
        }
        CommonObjectUtil.copyPropertiesNonNull(jcaCompanyDto, objectSave);
        this.saveJcaCompanyDto(objectSave);
        companyId = objectSave.getCompanyId();
        if(buildMenu) {
            menuService.buildMenuByCompany(companyId);
        }
        // Return result
        result.setStatus(ResultStatus.SUCCESS.toInt());
        result.setMessage(msg.getMessage(ConstantCore.MSG_SUCCESS_SAVE, null, null));
        result.setId(companyId);
        
        try {
			if (CommonStringUtil.isNotBlank(objectDto.getSystemName())) {
				JcaSystemConfig jcaSystemConfig = systemSettingRepository.findKeyAndCompanyId(AppSystemSettingKey.DISPLAY_SYSTEM_NAME, objectDto.getId());
				jcaSystemConfig.setSettingValue(objectDto.getSystemName());

				jcaSystemConfigService.saveJcaSystemConfig(jcaSystemConfig);
			}
		} catch (Exception e) {
		}
        
        return result;
    }

	/**
     * deleteCompany
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteCompany(Long id) {
//        Date sysDate = comService.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
//        JcaCompany object = companyRepository.findOne(id);
////        object.setDeletedBy(user);
//        object.setDeletedDate(sysDate);
//        companyRepository.save(object);
//        //delete Org
//        orgInfoRepository.deleteOrgByCompanyId(object.getId(), user, sysDate);
        this.deleteJcaCompanyById(id);
        return true;
    }
    
    /**
     * getCompanyListByCompanyId
     * 
     * @param term
     * @param companyId
     * @param companyAdmin
     * @param isPaging
     * @return
     * @author HungHT
     */
    public List<Select2Dto> getCompanyListByCompanyId(String term, Long companyId, boolean companyAdmin, boolean isPaging) {
        return companyRepository.getCompanyListByCompanyId(term, companyId, companyAdmin, isPaging);
    }

    /**
     * getSystemCodeByCompanyId
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public String getSystemCodeByCompanyId(Long companyId) {
        return findById(companyId).getSystemCode();
    }
    
    /**
     * Save files.
     *
     * @param file the file
     * @param jcaCompanyDto the jca company dto
     * @param enumTypeUpload the enum type upload
     * @return the jca company
     * @throws Exception the exception
     */
    private JcaCompanyDto saveFiles(MultipartFile file, JcaCompanyDto jcaCompanyDto, CompanyUploadFileTypeEnum enumTypeUpload) throws Exception {
    	if (null != file) {
            String subFilePath = COMPANY_IMAGE_FOLDER;
            
            String fileName = file.getOriginalFilename();
            String ext = FilenameUtils.getExtension(fileName);
            String fileNameRename = CommonDateUtil.formatDateToString(comService.getSystemDateTime(), CommonDateUtil.YYYYMMDDHHMMSS).concat(CommonConstant.DOT).concat(ext);
            byte[] imageByte = file.getBytes();
            //
            //fileupload
            FileUploadParamDto param = new FileUploadParamDto();
            param.setFileByteArray(imageByte);
            param.setFileName(fileNameRename);
            param.setRename(null);
            
            param.setTypeRule(2);
            param.setDateRule(null);
            param.setSubFilePath(subFilePath);
            param.setCompanyId(1L);
            
           param.setRepositoryId(5L);
            FileUploadResultDto uploadResultDto = fileStorageService.upload(param);
            //hardcode 
            String filePath = uploadResultDto.getFilePath();
            switch (enumTypeUpload) {
            case LOGIN_BACKGROUND:
                jcaCompanyDto.setLoginBackground(filePath);
                jcaCompanyDto.setLoginBackgroundRepoId(uploadResultDto.getRepositoryId());
                break;
            case LOGO_LARGE:
            	jcaCompanyDto.setLogoLarge(filePath);
                jcaCompanyDto.setLogoLargeRepoId(uploadResultDto.getRepositoryId());
                break;
            case LOGO_MINI:
            	jcaCompanyDto.setLogoMini(filePath);
                jcaCompanyDto.setLogoMiniRepoId(uploadResultDto.getRepositoryId());
                break;
            case SHORTCUT_ICON:
            	jcaCompanyDto.setShortcutIcon(filePath);
                jcaCompanyDto.setShortcutIconRepoId(uploadResultDto.getRepositoryId());
                break;
            default:
                break;
            }
        }
        return jcaCompanyDto;
    }

    /**
     * getLimitNumberUsers
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public Long getLimitNumberUsers(Long companyId) {
        Long numberUser = null;
        CompanyDto company = findById(companyId);
        if (null != company) {
            numberUser = company.getLimitNumberUsers();
        }
        return numberUser;
    }

    /**
     * getLimitNumberTransaction
     * 
     * @param companyId
     * @return
     * @author HungHT
     */
    public Long getLimitNumberTransaction(Long companyId) {
        Long numberTransaction = null;
        CompanyDto company = findById(companyId);
        if (null != company) {
            numberTransaction = company.getLimitNumberTransaction();
            
        }
        return numberTransaction;
    }

    /**
     * findBySystemCode
     * 
     * @param systemCode
     * @return
     * @author HungHT
     */
    public CompanyDto findBySystemCode(String systemCode) {
        return companyRepository.findByCondition(null, systemCode, null);
    }

    @Override
    public List<Select2Dto> findByListCompanyId(List<Long> companyIds, boolean companyAdmin) {
        return companyRepository.findByListCompanyId(companyIds, companyAdmin);
    }

	@Override
	public List<Select2Dto> findListCompanyByUserRole(String username) {
		try {
			return companyRepository.findListCompanyByUserRole(username);
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public List<JcaCompany> findCompanyByListId(List<Long> companyIds, boolean companyAdmin) {
		return companyRepository.findListCompanyByListCompanyId(companyIds, companyAdmin);
	}


//	@Override
//	public List<ResAllCompanyDetailDto> getAllCompany(Integer limitCompany) {
//		return companyRepository.getAllCompany(limitCompany);
//	}
//	
    /**
     * findAll
     * 
     * @return
     * @author HungHT
     */
    public Iterable<JcaCompany> findAll() {
        return companyRepository.findAll();
    }

	@Override
	public String getLanguageById(Long companyId) throws SQLException {
		return companyRepository.findLanguageById(companyId);
	}

	@Override
	public vn.com.unit.common.service.JCommonService getCommonService() {
		return comService;
	}
	
	private CompanyDto convertJcaCompanyDtoToCompanyDto(JcaCompanyDto jcaCompanyDto) {
		CompanyDto result = new CompanyDto();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    	if(null != jcaCompanyDto) {
    		result = modelMapper.map(jcaCompanyDto, CompanyDto.class);
    		result.setId(jcaCompanyDto.getCompanyId());
    		result.setActived(jcaCompanyDto.getActived());
    	}
		return result;
	}
	
	private JcaCompanyDto convertCompanyDtoToJcaCompanyDto(CompanyDto companyDto) {
		JcaCompanyDto result = new JcaCompanyDto();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    	if(null != companyDto) {
    		result = modelMapper.map(companyDto, JcaCompanyDto.class);
    		result.setCompanyId(companyDto.getId());
    		result.setActived(companyDto.isActived()? true : false);
    	}
		return result;
	}
}