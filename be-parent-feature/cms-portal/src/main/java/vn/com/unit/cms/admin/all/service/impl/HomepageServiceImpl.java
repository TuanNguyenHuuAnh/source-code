package vn.com.unit.cms.admin.all.service.impl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.CmsCommonConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.EmailCommonDto;
import vn.com.unit.cms.admin.all.dto.HomepageSearchDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingDto;
import vn.com.unit.cms.admin.all.dto.HomepageSettingEditDto;
//import vn.com.unit.cms.admin.all.entity.Banner;
//import vn.com.unit.cms.admin.all.entity.BannerLanguage;
import vn.com.unit.cms.admin.all.entity.HomepageSetting;
import vn.com.unit.cms.admin.all.enumdef.JobProcessEnum;
import vn.com.unit.cms.admin.all.enumdef.StepActionEnum;
//import vn.com.unit.jcanary.constant.RoleConstant;
import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.cms.admin.all.repository.BannerLanguageRepository;
//import vn.com.unit.cms.admin.all.repository.BannerRepository;
import vn.com.unit.cms.admin.all.repository.HomepageRepository;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
//import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.HomepageService;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;
import vn.com.unit.cms.core.module.banner.enumdef.StepStatusEnum;
import vn.com.unit.cms.core.module.banner.repository.BannerLanguageRepository;
import vn.com.unit.cms.core.module.banner.repository.BannerRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.service.EmailService;
import vn.com.unit.core.dto.JcaConstantDto;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.service.AccountService;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
//import vn.com.unit.jcanary.utils.Utils;
import vn.com.unit.ep2p.core.utils.Utility;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class HomepageServiceImpl extends
        DocumentWorkflowCommonServiceImpl<HomepageSettingEditDto, HomepageSettingEditDto> implements HomepageService {

    @Autowired
    private HomepageRepository homepageRepository;

    @Autowired
    private SystemConfig systemConfig;

//    @Autowired
//	private ConstantDisplayService constantDisplayService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BannerLanguageRepository bannerLanguageRepository;

//    @Autowired
//    private ProcessRepository processRepository;
//    
//    @Autowired
//    private JBPMService jBPMService;
//    
//    @Autowired
//    private ConstantDisplayService constDispService;
//    
//    @Autowired
//    ProcessService processService;
//    
//    @Autowired
//    JProcessService jprocessService;
//    
//    @Autowired
//    HistoryApproveService historyApproveService;

    @Autowired
    AccountService accountService;

    @Autowired
    EmailService emailService;

    @Autowired
    private CmsFileService fileService;

//    @Autowired
//    private EmailUtil emailUtil;

    @Autowired
    MessageSource msg;

    @Autowired
    private JcaConstantService jcaConstantService;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(HomepageServiceImpl.class);

    @Override
    public PageWrapper<HomepageSettingDto> search(int page, HomepageSearchDto homepageSearchDto, Locale locale) {

        // setSearchCriteria(homepageSearchDto);

        // set status name
        homepageSearchDto.setStatusName(msg.getMessage(StepStatusEnum.DRAFT.getStatusName(), null, locale));
        int pageSize = homepageSearchDto.getPageSize() != null ? homepageSearchDto.getPageSize()
                : systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);

        if (homepageSearchDto.getSelectedStatus() != null) {
            if (!homepageSearchDto.getSelectedStatus().isEmpty()) {
                homepageSearchDto.setStatus(null);
            }
        }
        int count = homepageRepository.countBySearchCondition(homepageSearchDto);
        if ((count % pageSize == 0 && page > count / pageSize)
                || (count % pageSize > 0 && page - 1 > count / pageSize)) {
            page = 1;
        }

        PageWrapper<HomepageSettingDto> pageWrapper = new PageWrapper<>(page, pageSize);
        List<HomepageSettingDto> homepageDtoList = new ArrayList<>();

        if (count > 0) {
            int offsetSQL = Utility.calculateOffsetSQL(page, pageSize);
            homepageDtoList = homepageRepository.findBySearchCondition(homepageSearchDto, pageSize, offsetSQL);
        }

        String lang = homepageSearchDto.getLang();

        for (HomepageSettingDto homepageSetting : homepageDtoList) {

            if (homepageSetting.getBannerTopList() == null) {
                homepageSetting.setBannerTopList(new ArrayList<>());
                homepageSetting.setBannerTopList(generateBannerLanguage(homepageSetting.getBannerTopId(), lang));
            }

            if (homepageSetting.getBannerTopMobileList() == null) {
                homepageSetting.setBannerTopMobileList(new ArrayList<>());
                homepageSetting
                        .setBannerTopMobileList(generateBannerLanguage(homepageSetting.getBannerTopMobileId(), lang));
            }

        }

        pageWrapper.setDataAndCount(homepageDtoList, count);

        return pageWrapper;
    }

    private List<BannerLanguage> generateBannerLanguage(String idList, String lang) {
        List<BannerLanguage> result = new ArrayList<BannerLanguage>();
        if (idList == null) {
            return result;
        }
        String[] lst = idList.split(",");
        for (String id : lst) {
        	BannerLanguageDto bannerLang = bannerLanguageRepository.findByBannerIdAndLangueCode(Long.valueOf(id));
        	BannerLanguage rs = new BannerLanguage();
        	try {
				NullAwareBeanUtils.copyPropertiesWONull(result, rs);
			} catch (Exception e) {
				logger.error("Exception ", e);
			}
            if (bannerLang != null) {
                result.add(rs);
            }
        }
        return result;

    }

    @Override
    public List<SelectItem> getStatusList() {
        List<SelectItem> selectedStatus = new ArrayList<>();

        // ${constantDisplay.cat} => ${constantDisplay.kind}
        // #{${constantDisplay.code}} => #{${constantDisplay.code}}
        // constDispService.findByType("M10");
        // => List<JcaConstantDto> statusList =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(),
        // "EN");

        // type => groupCode
        // cat => kind
        // code => code

        // catOfficialName => name

        // ConstantDisplay motive =
        // constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString());
        // JcaConstantDto motive =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString(), "EN").get(0);

        // List<ConstantDisplay> listBannerPage =
        // constDispService.findByType(ConstDispType.B01);
        // List<JcaConstantDto> listBannerPage =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(),
        // "EN");

//    	List<ConstantDisplay> constants = constantDisplayService.findByType(ConstDispType.M06);

        List<JcaConstantDto> constants = jcaConstantService
                .getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M06.toString(), "EN");

        if (constants != null) {
            for (JcaConstantDto item : constants) {
                SelectItem status = new SelectItem();
                status.setName(item.getCode());
                status.setValue(item.getKind());
                selectedStatus.add(status);
            }
        }

        return selectedStatus;
    }

    @Override
    public HomepageSettingEditDto getHomepageEdit(Long id, Locale locale, String businessCode) {
        HomepageSettingEditDto resultDto = new HomepageSettingEditDto();

        if (id == null) {
            resultDto.setStatus(StepStatusEnum.DRAFT.getStepNo());
            resultDto.setBusinessCode(businessCode);
            resultDto.setCreateBy(UserProfileUtils.getUserNameLogin());
        } else {
            // check id in database
            HomepageSetting findId = homepageRepository.findByIdEntity(id);
            if (null == findId) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
            HomepageSetting homepage = homepageRepository.findOne(id);
            if (homepage != null) {
                resultDto.setId(homepage.getId());
                resultDto.setEffectiveDateFrom(homepage.getEffectiveDateFrom());
                resultDto.setEffectiveDateTo(homepage.getEffectiveDateTo());
                resultDto.setDescription(homepage.getDescription());
                resultDto.setSpeedRoll(homepage.getSpeedRoll());
                resultDto.setBannerTop(generateBannerIdList(findBannerById(homepage.getBannerTopId())));
                resultDto.setBannerTopList(findBannerById(homepage.getBannerTopId()));
                resultDto.setStatus(homepage.getStatus());
                resultDto.setBannerTopMobile(generateBannerIdList(findBannerById(homepage.getBannerTopMobileId())));
                resultDto.setBannerTopMobileList(findBannerById(homepage.getBannerTopMobileId()));
                resultDto.setBannerPage(homepage.getBannerPage());
                resultDto.setStatusCode(homepage.getStatusCode());
                resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_HOMEPAGESETTIMG);
                resultDto.setCreateBy(homepage.getCreateBy());
                resultDto.setBusinessCode(businessCode);
                resultDto.setProcessId(homepage.getProcessId());
                resultDto.setUpdateDate(homepage.getUpdateDate());
                resultDto.setReferenceId(homepage.getId());
                resultDto.setComment(homepage.getNote());
            }
        }

//        Long processId = resultDto.getProcessId();
//        if (processId == null) {
//            // First step
//            JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(businessCode, locale.toString());
//            processId = processDto.getProcessId();
//
//        }
//        // List button of step
//        List<JProcessStepDto> stepButtonList = jprocessService.findStepButtonListHasRole(processId,
//                resultDto.getStatus(), locale.toString());
//        resultDto.setStepBtnList(stepButtonList);
//        
//        String statusName = jprocessService.getStatusName(resultDto.getProcessId(), resultDto.getStatus(), locale);
//        resultDto.setStatusName(statusName);

        return resultDto;
    }

    private List<String> generateBannerIdList(List<Banner> findBannerById) {
        List<String> resultList = new ArrayList<>();
        for (Banner banner : findBannerById) {
            resultList.add(String.valueOf(banner.getId()));
        }
        return resultList;
    }

    @Override
    public List<SelectItem> generateBannerSelectionList(List<Banner> findBannerById) {
        List<SelectItem> resultList = new ArrayList<>();
        for (Banner banner : findBannerById) {
            SelectItem item = new SelectItem();
//            item.setName(banner.getName());
            item.setValue(String.valueOf(banner.getId()));
            resultList.add(item);
        }
        return resultList;
    }

    private List<Banner> findBannerById(String bannerTopId) {
        List<Banner> BannerList = new ArrayList<>();
        if (bannerTopId != null) {
            if (StringUtils.isNotBlank(bannerTopId)) {
                String[] idList = bannerTopId.split(ConstantCore.COMMA);
                for (String id : idList) {
                    BannerList.add(bannerService.findBannerById(Long.valueOf(id)));
                }
            }
        }
        return BannerList;
    }

    @Override
    public List<Banner> findBannerByTypeAndDevice(String bannerType, boolean isMobile) {
        List<Banner> bannerList = bannerRepository.findByTypeAndDevice(bannerType, isMobile);
        if (bannerList != null)
            return bannerList;
        return new ArrayList<Banner>();
    }

    @Override
    @Transactional
    public boolean doEdit(HomepageSettingEditDto editDto, Locale locale, HttpServletRequest request) {

        // user name login
//        UserProfile userProfile = UserProfileUtils.getUserProfile();

        // do create
        createOrUpdate(editDto, UserProfileUtils.getUserNameLogin(), locale, request);

        // if action process
        if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
            // update history approve
            updateHistoryApprove(editDto);

            // send mail
            sendMail(editDto, request);
        }

        CmsUtils.moveTempSubFolderToUpload(
                Paths.get(AdminConstant.HOMEPAGER_EDITOR_FOLDER, editDto.getRequestToken()).toString());

        return true;
    }

    private void createOrUpdate(HomepageSettingEditDto editDto, String usernameLogin, Locale locale,
            HttpServletRequest request) {
        HomepageSetting entity = new HomepageSetting();
        // create new
        if (editDto.getId() != null) {
            // check id in database
            HomepageSetting findId = homepageRepository.findByIdEntity(editDto.getId());
            if (null == findId) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
            // edit
            entity = homepageRepository.findOne(editDto.getId());

            if (null == entity) {
                throw new BusinessException(
                        msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_WITH_ID, null, locale) + editDto.getId());
            }

            entity.setUpdateDate(new Date());
            entity.setUpdateBy(usernameLogin);
        } else {
            entity.setCreateDate(new Date());
            entity.setCreateBy(usernameLogin);
        }
        try {
            entity.setSpeedRoll(editDto.getSpeedRoll());
            entity.setEffectiveDateFrom(editDto.getEffectiveDateFrom());
            entity.setEffectiveDateTo(editDto.getEffectiveDateTo());
            entity.setDescription(editDto.getDescription());
            entity.setBannerTopId(generateBannerString(editDto.getBannerTop()));
            entity.setBannerTopMobileId(generateBannerString(editDto.getBannerTopMobile()));
            entity.setNote(editDto.getComment());

            // handle value banner page
            if (editDto.getBannerPage() != null && editDto.getBannerPage() != "") {
                entity.setBannerPage(editDto.getBannerPage().split(",")[0]);
            }
            // Add process role
            entity.setOwnerBranchId(UserProfileUtils.getBranchId());
            entity.setOwnerId(UserProfileUtils.getAccountId());
            entity.setOwnerSectionId(UserProfileUtils.getDepartmentId());
            entity.setStatus(editDto.getStatus());

            // if action process
//            if (!StringUtils.equals(editDto.getButtonId().toString(), StepActionEnum.SAVE.getCode())) {
//                if (editDto.getProcessId() == null) {
//                    // First step
//                    JProcessStepDto processDto = jprocessService.findFirstStepOfProcess(editDto.getBusinessCode(),
//                            locale.toString());
//                    // set process id
//                    editDto.setProcessId(processDto.getProcessId());
//                }
//
//                // current step
//                JProcessStepDto currentActionStep = jprocessService.findCurrentProcessStep(editDto.getProcessId(),
//                        editDto.getStatus(), editDto.getButtonId());
//                Integer status = jprocessService.getNextStepNo(currentActionStep, null);
//                
//                editDto.setOldStatus(editDto.getStatus());
//                // set status
//                editDto.setStatus(status);
//                editDto.setCurrItem(currentActionStep.getItems());
//            }

            entity.setProcessId(editDto.getProcessId());
            entity.setStatus(editDto.getStatus());

            homepageRepository.save(entity);
            if (entity.getId() != null) {
                editDto.setId(entity.getId());
            }
        } catch (Exception e) {
            logger.error("###createOrEditHomepageSetting###: " + e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }
    }

    private void updateHistoryApprove(HomepageSettingEditDto editDto) {
        try {
            // insert comment
            HistoryApproveDto historyApproveDto = new HistoryApproveDto();
            historyApproveDto.setApprover(UserProfileUtils.getFullName());
            historyApproveDto.setComment(editDto.getComment());
            historyApproveDto.setProcessId(editDto.getProcessId());
            historyApproveDto.setProcessStep(editDto.getStatus().longValue());
            historyApproveDto.setReferenceId(editDto.getId());
            historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_HOMEPAGESETTIMG);
            historyApproveDto.setActionId(editDto.getButtonId().toString());
            historyApproveDto.setOldStep(editDto.getOldStatus());
            historyApproveDto.setAccountId(UserProfileUtils.getAccountId());
//	            historyApproveService.addHistoryApprove(historyApproveDto);
        } catch (Exception e) {
            logger.error("updateHistoryApprove: " + e.getMessage());
        }

    }

    public void sendMail(HomepageSettingEditDto editDto, HttpServletRequest request) {
        // locale default
        String defaultlocale = systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT);
        Locale locale = new Locale(defaultlocale);

        EmailCommonDto emailCommon = new EmailCommonDto();
        emailCommon.setActionName(msg.getMessage("email.template.hompage.setting", null, locale));
        emailCommon.setButtonAction(editDto.getButtonAction());
        emailCommon.setButtonId(editDto.getButtonId().toString());
        emailCommon.setComment(editDto.getComment());

        // Nội dung
        LinkedHashMap<String, String> content = new LinkedHashMap<>();
        JcaConstantDto constantDisplay = getBannerPageOfEditById(editDto.getId());
        String tenTrang = constantDisplay.getCode();

        try {
            tenTrang = msg.getMessage(constantDisplay.getCode(), null, locale);
        } catch (Exception e) {
            logger.error("sendMail- tenTrang:" + e.getMessage());
        }

        content.put("Tên trang", tenTrang);

        emailCommon.setContent(content);

        emailCommon.setCurrItem(editDto.getCurrItem());

        emailCommon.setId(editDto.getId());
        emailCommon.setOldStatus(editDto.getOldStatus());
        emailCommon.setProcessId(editDto.getProcessId());
        emailCommon.setReferenceType(editDto.getReferenceType());
        emailCommon.setStatus(editDto.getStatus());

        // Subject của email
        emailCommon.setSubject(msg.getMessage("subject.email.template.hompage.setting", null, locale));

        emailCommon.setUrl(CmsUtils.getBaseUrl(request) + "/homepage-setting/edit?id=" + editDto.getId());

//        emailUtil.sendMail(emailCommon, request, locale);
    }

    private String generateBannerString(List<String> bannerList) {
        if (bannerList != null) {
            if (!bannerList.isEmpty()) {
                StringBuilder resultBuilder = new StringBuilder();
                for (String item : bannerList) {
                    resultBuilder.append(item).append(ConstantCore.COMMA);
                }
                return resultBuilder.deleteCharAt(resultBuilder.lastIndexOf(ConstantCore.COMMA)).toString();
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(Long homepageId) {
        String userNameLogin = UserProfileUtils.getUserNameLogin();
        HomepageSetting homepage = new HomepageSetting();

        if (null != homepageId) {
            homepage = homepageRepository.findOne(homepageId);
        }
        // delete homePageSetting
        homepage.setDeleteDate(new Date());
        homepage.setDeleteBy(userNameLogin);

        try {
            homepageRepository.save(homepage);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
    }

    @Override
    public List<JcaConstantDto> listBannerPage() {

        // ${constantDisplay.cat} => ${constantDisplay.kind}
        // #{${constantDisplay.code}} => #{${constantDisplay.code}}
        // constDispService.findByType("M10");
        // => List<JcaConstantDto> statusList =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(),
        // "EN");

        // type => groupCode
        // cat => kind
        // code => code

        // catOfficialName => name

        // ConstantDisplay motive =
        // constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString());
        // JcaConstantDto motive =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString(), "EN").get(0);

        // List<ConstantDisplay> listBannerPage =
        // constDispService.findByType(ConstDispType.B01);
        // List<JcaConstantDto> listBannerPage =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(),
        // "EN");

        List<JcaConstantDto> listBannerPage = jcaConstantService
                .getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(), "EN");

        return listBannerPage;
    }

    @Override
    @Transactional
    public void approver(HomepageSettingEditDto homepageSettingEditDto) {
        String userNameLogin = UserProfileUtils.getUserNameLogin();
        Long homePageId = homepageSettingEditDto.getId();

        // update data m_homepage_setting table
        HomepageSetting updateHomePageSetting = new HomepageSetting();
        if (null != homePageId) {
            updateHomePageSetting = homepageRepository.findOne(homePageId);
            if (null == updateHomePageSetting) {
                throw new BusinessException("Not found home page setting by id= " + homePageId);
            }
            updateHomePageSetting.setUpdateBy(userNameLogin);
            updateHomePageSetting.setUpdateDate(new Date());
        }

        /* --- Start jBPM process -------------------------------- */
//        Process process = processRepository.findOne(updateHomePageSetting.getProcessId());
//        if (process != null) {
//            if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))) {
//                Hashtable<String, Object> params = new Hashtable<String, Object>();
//                if (homepageSettingEditDto.isAction()) {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_APPROVE);
//                } else {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_REJECT);
//                }
//
//                List<String> listCheck = new ArrayList<String>();
//                listCheck.add(CommonConstant.STATUS_SUBMITTED);
//
//                jBPMService.updateJBPMStatus(process.getDeploymentId(), updateHomePageSetting.getProcessInstanceId(),
//                		CmsRoleConstant.ROLE_MANAGER, CmsRoleConstant.ROLE_MANAGER, params, CommonConstant.PARAM_STATUS,
//                        listCheck);
//            }else{
//                throw new SystemException("authority exception");
//            }
//        }

        /* --- End jBPM process ---------------------------------- */
        String statusCode = "";
        if (homepageSettingEditDto.isAction()) {
            statusCode = JobProcessEnum.APPROVAL.toString();
        } else {
            statusCode = JobProcessEnum.REJECT.toString();
        }
        updateHomePageSetting.setStatusCode(statusCode);
        try {
            homepageRepository.save(updateHomePageSetting);
        } catch (Exception ex) {
            throw new SystemException(ex);
        }
        // Update history approve

        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
        historyApproveDto.setComment(homepageSettingEditDto.getComment());
        historyApproveDto.setProcessId(updateHomePageSetting.getProcessId());
        historyApproveDto.setReferenceId(updateHomePageSetting.getId());
        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_HOMEPAGESETTIMG);
        historyApproveDto.setStatusCode(statusCode);
        historyApproveDto.setApprover(userNameLogin);

//        historyApproveService.addHistoryApprove(historyApproveDto, JobProcessEnum.BUSINESS_CODE_AH1.toString());
    }

    @Override
    public List<JcaConstantDto> listBannerPageOfEdit() {

        // ${constantDisplay.cat} => ${constantDisplay.kind}
        // #{${constantDisplay.code}} => #{${constantDisplay.code}}
        // constDispService.findByType("M10");
        // => List<JcaConstantDto> statusList =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(),
        // "EN");

        // type => groupCode
        // cat => kind
        // code => code

        // catOfficialName => name

        // ConstantDisplay motive =
        // constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString());
        // JcaConstantDto motive =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString(), "EN").get(0);

        // List<ConstantDisplay> listBannerPage =
        // constDispService.findByType(ConstDispType.B01);
        // List<JcaConstantDto> listBannerPage =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(),
        // "EN");

        List<JcaConstantDto> listBannerPage = homepageRepository.getConstantDisplayAll(ConstDispType.B01.toString());
        List<JcaConstantDto> listBannerPageAdded = homepageRepository.getConstantDisplay(ConstDispType.B01.toString());

        if (!listBannerPageAdded.isEmpty()) {
            for (int i = 0; i < listBannerPage.size(); i++) {
                for (int j = 0; j < listBannerPageAdded.size(); j++) {
//                    if (listBannerPage.get(i).getCat().equals(listBannerPageAdded.get(j).getCat())){
                    // listBannerPage.remove(listBannerPage.get(i));
//                    }
                }
            }
        }
        return listBannerPage;
    }

    @Override
    public JcaConstantDto getBannerPageOfEditById(Long homePageId) {

        // ${constantDisplay.cat} => ${constantDisplay.kind}
        // #{${constantDisplay.code}} => #{${constantDisplay.code}}
        // constDispService.findByType("M10");
        // => List<JcaConstantDto> statusList =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.M10.toString(),
        // "EN");

        // type => groupCode
        // cat => kind
        // code => code

        // catOfficialName => name

        // ConstantDisplay motive =
        // constantDisplayService.findByTypeAndCat(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString());
        // JcaConstantDto motive =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(ConstDispType.MOTIVE.toString(),
        // emailModel.getMotive().toString(), "EN").get(0);

        // List<ConstantDisplay> listBannerPage =
        // constDispService.findByType(ConstDispType.B01);
        // List<JcaConstantDto> listBannerPage =
        // jcaConstantService.getListJcaConstantDtoByGroupCodeAndLang(ConstDispType.B01.toString(),
        // "EN");

        return homepageRepository.getConstantDisplayById(ConstDispType.B01.toString(), homePageId);
    }

    @Override
    public HomepageSettingDto getHomepageEditByBannerPage(String bannerPage) {
        return homepageRepository.findByBannerPage(bannerPage);
    }

    @Override
    public List<Banner> findBannerByTypeAndDeviceLanguage(String bannerType, boolean isMobile, String language,
            Integer status) {
        List<Banner> bannerList = bannerRepository.findByTypeAndDeviceLanguage(bannerType, isMobile, language, status);
        if (bannerList != null)
            return bannerList;
        return new ArrayList<Banner>();
    }

    @Override
    public List<HomepageSettingDto> getAllHomepage() {
        return homepageRepository.getAllHomepage();
    }

    @Override
    public boolean requestEditorDownload(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        boolean retVal = false;
        if (fileUrl != null) {
            if (CmsUtils.fileExistedInMain(fileUrl)) {
                fileService.download(fileUrl, request, response);
                retVal = true;
            } else if (CmsUtils.fileExistedInTemp(fileUrl)) {
                fileService.downloadTemp(fileUrl, request, response);
                retVal = true;
            }
        }
        return retVal;
    }

    @Override
    public Long countByBannerId(Long bannerId) {
        return homepageRepository.countByBannerId(bannerId);
    }

    @Override
    public HomepageSettingEditDto getEdit(Long id, String customerAlias, Locale locale) {
        return getHomepageEdit(id, locale, CmsCommonConstant.BUSINESS_HOMEPAGESETTIMG);
    }
}