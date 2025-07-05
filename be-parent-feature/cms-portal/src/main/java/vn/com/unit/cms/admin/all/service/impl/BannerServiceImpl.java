/*******************************************************************************
 * Class        ：BannerServiceImpl
 * Created date ：2017/02/16
 * Lasted date  ：2017/02/16
 * Author       ：TaiTM
 * Change log   ：2017/02/16：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.cms.admin.all.constant.AdminConstant;
import vn.com.unit.cms.admin.all.constant.ConstDispType;
import vn.com.unit.cms.admin.all.constant.ConstantHistoryApprove;
import vn.com.unit.cms.admin.all.dto.BannerEditDto;
import vn.com.unit.cms.admin.all.jcanary.service.CmsCommonService;
import vn.com.unit.cms.admin.all.service.BannerLanguageService;
import vn.com.unit.cms.admin.all.service.BannerService;
import vn.com.unit.cms.admin.all.service.CmsFileService;
import vn.com.unit.cms.admin.all.service.NewsCategoryService;
import vn.com.unit.cms.admin.all.util.CmsLanguageUtils;
import vn.com.unit.cms.admin.all.util.CommonSearchFilterUtils;
import vn.com.unit.cms.core.constant.CmsPrefixCodeConstant;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageDto;
import vn.com.unit.cms.core.module.banner.dto.BannerLanguageSearchDto;
import vn.com.unit.cms.core.module.banner.dto.BannerSearchDto;
import vn.com.unit.cms.core.module.banner.entity.Banner;
import vn.com.unit.cms.core.module.banner.entity.BannerLanguage;
import vn.com.unit.cms.core.module.banner.repository.BannerRepository;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.SystemException;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.JcaDatatableConfigService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.dto.CommonSearchFilterDto;
import vn.com.unit.ep2p.core.ers.service.Select2DataService;
import vn.com.unit.ep2p.core.exception.BusinessException;

/**
 * BannerServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Service(value = "bannerServiceImpl")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private BannerLanguageService bannerLanguageService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private JcaConstantService jcaConstantService;

    @Autowired
    private CmsFileService fileService;

    @Autowired
    private CmsCommonService cmsCommonService;

    @Autowired
    private MessageSource msg;

    @Autowired
    private CommonSearchFilterUtils commonSearchFilterUtils;

    @Autowired
    private Select2DataService select2DataService;

    @Autowired
    private JcaDatatableConfigService jcaDatatableConfigService;

    private static final int YOUTUBE_TYPE = 2;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);
    
    @Override
    public List<BannerLanguageSearchDto> getListByCondition(BannerSearchDto searchDto, Pageable pageable) {
        return bannerRepository.findByBannerSearchDto(searchDto, pageable).getContent();
    }

    @Override
    public int countListByCondition(BannerSearchDto searchDto) {
        return bannerRepository.countByBannerSearchDto(searchDto);
    }

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    @Override
    public CmsCommonService getCmsCommonService() {
        return cmsCommonService;
    }

    @Override
    public JcaDatatableConfigService getJcaDatatableConfigService() {
        return jcaDatatableConfigService;
    }

    /**
     * find Banner by id
     *
     * @param bid
     * @return
     * @author TaiTM
     */
    @Override
    public Banner findBannerById(Long id) {
        return bannerRepository.findOne(id);
    }

    /**
     * delete Banner by entity
     *
     * @param banner
     * @author TaiTM
     */
    @Override
    @Transactional
    public void deleteBanner(Banner banner) {
        // user name login
        String userName = UserProfileUtils.getUserNameLogin();
        // delete 
        bannerLanguageService.deleteByBannerId(banner.getId(), new Date(), userName);

        banner.setDeleteDate(new Date());
        banner.setDeleteBy(userName);
        bannerRepository.save(banner);
    }

    /**
     * get BannerEdit
     *
     * @param bannerId
     * @return BannerEditDto
     * @author TaiTM
     */
    @Override
    public BannerEditDto getBannerEdit(Long bannerId, Locale locale) {
        BannerEditDto resultDto = new BannerEditDto();
        if (bannerId == null) {
            return resultDto;
        }

        // set banner
        Banner banner = findBannerById(bannerId);
        // dữ liệu ko tồn tại hoặc đã bị xóa
        if (banner == null || banner.getDeleteDate() != null) {
            throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
        }
        if (banner != null) {
            resultDto.setId(banner.getId());
            resultDto.setCode(banner.getCode());
            resultDto.setNote(banner.getNote());
            resultDto.setBannerType(banner.getBannerType());
            resultDto.setBannerDevice(banner.getBannerDevice());
            resultDto.setCreateBy(banner.getCreateBy());
            resultDto.setReferenceType(ConstantHistoryApprove.APPROVE_BANNER);
            resultDto.setReferenceId(banner.getId());
            resultDto.setUpdateDate(banner.getUpdateDate());
            resultDto.setEnabled(banner.isEnabled());
            resultDto.setDocId(banner.getDocId());
            resultDto.setChannel(banner.getChannel());
			if (banner.getChannel() != null) {
				String[] lstChannel = banner.getChannel().split(",");
				List<String> channelsList = Arrays.asList(lstChannel);
				resultDto.setChannelList(channelsList);
			}

            if (!StringUtils.isEmpty(banner.getBannerType())) {
                List<JcaConstantDto> listBannerType = jcaConstantService.getListJcaConstantDtoByGroupCodeAndKind(
                        ConstDispType.M10.toString(), banner.getBannerType(), "EN");
                resultDto.setBannerTypeName(listBannerType.get(0).getCode());
            }
        }

        List<BannerLanguageDto> bannerLanguageDtos = getBannerLanguageDto(bannerId);
        resultDto.setBannerLanguageList(bannerLanguageDtos);

        return resultDto;
    }

    /**
     * get BannerLanguageDto
     *
     * @param bannerId
     * @return BannerLanguageDto List
     * @author TaiTM
     */
    private List<BannerLanguageDto> getBannerLanguageDto(Long bannerId) {
        // resultLst
        List<BannerLanguageDto> resultLst = new ArrayList<BannerLanguageDto>();

        // bannerLanguages
        List<BannerLanguage> bannerLanguages = bannerLanguageService.findBannerLanguageByBannerId(bannerId);

        // languageList
        List<Language> languageList = languageService.findAllActive();

        // loop language
        for (Language language : languageList) {
            // loop BannerLanguage
            for (BannerLanguage entity : bannerLanguages) {
                // bannerLanguageId is languageId
                if (StringUtils.equals(entity.getLanguageCode(), language.getCode())) {
                    BannerLanguageDto dto = new BannerLanguageDto();
                    dto.setId(entity.getId());
                    dto.setBannerId(entity.getBannerId());
                    dto.setLanguageCode(entity.getLanguageCode());
                    dto.setBannerTextLeft(entity.getBannerTextLeft());
                    dto.setBannerLink(entity.getBannerLink());
                    dto.setBannerImg(entity.getBannerImg());
                    dto.setBannerPhysicalImg(entity.getBannerPhysicalImg());
                    dto.setBannerVideo(entity.getBannerVideo());
                    dto.setBannerPhysicalVideo(entity.getBannerPhysicalVideo());
                    dto.setTitle(entity.getTitle());
                    dto.setDescription(entity.getDescription());
                    dto.setBannerYoutubeVideo(entity.getBannerYoutubeVideo());
                    dto.setBannerVideoType(entity.getBannerVideoType());
                    resultLst.add(dto);
                    break;
                }
            }
        }

        return resultLst;
    }

    /**
     * addOrEdit
     *
     * @param bannerEdit
     * @author TaiTM
     * @throws Exception
     */
    @Override
    @Transactional
    public boolean addOrEdit(BannerEditDto bannerEdit, String requestToken, HttpServletRequest request, Locale locale)
            throws Exception {

        // user login
        String usernameLogin = UserProfileUtils.getUserNameLogin();

        // save or update banner
        createOrEditBanner(bannerEdit, usernameLogin, request, locale);

        // save or update BannerLanguage
        createOrEditBannerLanguage(bannerEdit, usernameLogin);

        moveEditorTempFile(requestToken);

        return true;
    }

    private void moveEditorTempFile(String requestToken) {
        CmsUtils.moveTempSubFolderToUpload(Paths.get(AdminConstant.BANNER_EDITOR_FOLDER, requestToken).toString());
    }

    /**
     * create or update banner
     *
     * @param bannerEdit
     * @param userProfile
     * @author TaiTM
     * @throws Exception
     */
    private void createOrEditBanner(BannerEditDto bannerEdit, String usernameLogin, HttpServletRequest request,
            Locale locale) throws Exception {
        Banner b = new Banner();
        // edit banner else add new banner
        if (null != bannerEdit.getId()) {
            b = this.findBannerById(bannerEdit.getId());

            if (b == null || b.getDeleteBy() != null) {
                throw new BusinessException(msg.getMessage(ConstantCore.MSG_NOT_FOUND_ENTITY_ID, null, locale));
            }
            b.setEnabled(bannerEdit.isEnabled());
            b.setUpdateDate(new Date());
            b.setUpdateBy(usernameLogin);
        } else {
            b.setCreateDate(new Date());
            b.setCreateBy(usernameLogin);

            // code generation
            b.setCode(CommonUtil.getNextBannerCode(CmsPrefixCodeConstant.PREFIX_CODE_BANNER,
                    cmsCommonService.getMaxCodeYYMM("M_BANNER", CmsPrefixCodeConstant.PREFIX_CODE_BANNER)));
        }

        try {
            b.setBannerType(bannerEdit.getBannerType());
            b.setBannerDevice(bannerEdit.getBannerDevice());
            b.setEnabled(bannerEdit.isEnabled());
			if (bannerEdit.getChannelList() != null) {
				b.setChannel(String.join(",", bannerEdit.getChannelList()));
			}

            if (bannerEdit.getDocId() != null) {
                b.setDocId(bannerEdit.getDocId());
            }

            b.setNote("");

            bannerRepository.save(b);
            bannerEdit.setId(b.getId());
            bannerEdit.setCode(b.getCode());

        } catch (Exception e) {
            logger.error("createOrEditBanner: " + e.getMessage());
            throw new SystemException(msg.getMessage(ConstantCore.MSG_ERROR_CREATE_UPDATE, null, locale));
        }

    }

    /**
     * create or update BannerLanguage
     *
     * @param bannerEdit
     * @param userName
     * @author TaiTM
     * @throws Exception 
     */
    private void createOrEditBannerLanguage(BannerEditDto bannerEdit, String userName) throws Exception {
        try {
            List<BannerLanguageDto> lstBl = bannerEdit.getBannerLanguageList();

            if (lstBl != null) {

                for (BannerLanguageDto rs : lstBl) {

                    BannerLanguage bl = new BannerLanguage();

                    if (rs.getId() != null) {
                        bl = bannerLanguageService.findById(rs.getId());

                        if (bl == null) {
                            throw new BusinessException("Not found Banner Language with id=" + rs.getId());
                        }

                        bl.setUpdateDate(new Date());
                        bl.setUpdateBy(userName);
                    } else {
                        bl.setCreateDate(new Date());
                        bl.setCreateBy(userName);
                    }

                    bl.setBannerId(bannerEdit.getId());
                    bl.setLanguageCode(rs.getLanguageCode());
                    bl.setBannerTextLeft(rs.getBannerTextLeft());
                    bl.setBannerLink(rs.getBannerLink());
                    bl.setTitle(rs.getTitle());
                    bl.setDescription(rs.getDescription());

                    if (rs.getBannerVideoType() != null || StringUtils.isNotBlank(rs.getBannerVideo())) {
                        if (rs.getBannerVideoType() != null && rs.getBannerVideoType() == YOUTUBE_TYPE) {
                            bl.setBannerVideoType(rs.getBannerVideoType());
                            bl.setBannerYoutubeVideo(rs.getBannerYoutubeVideo());

                            bl.setBannerPhysicalVideo(null);
                            bl.setBannerVideo(null);
                        } else {
                            bl.setBannerVideoType(1);
                            bl.setBannerYoutubeVideo("");

                            // physical file template
                            String physicalVideoTmpName = rs.getBannerPhysicalVideo();
                            // upload video
                            if (StringUtils.isNotEmpty(physicalVideoTmpName)) {
                                String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalVideoTmpName,
                                        AdminConstant.BANNER_FOLDER);
                                bl.setBannerPhysicalVideo(newPhiscalName);
                                bl.setBannerVideo(rs.getBannerVideo());
                            } else {
                                bl.setBannerPhysicalVideo(null);
                                bl.setBannerVideo(null);
                            }
                        }
                    }

                    // physical file template
                    String physicalImgTmpName = rs.getBannerPhysicalImg();
                    // upload images
                    if (StringUtils.isNotEmpty(physicalImgTmpName)) {
                        String newPhiscalName = CmsUtils.moveTempToUploadFolder(physicalImgTmpName,
                                AdminConstant.BANNER_FOLDER);
                        bl.setBannerPhysicalImg(newPhiscalName);
                        bl.setBannerImg(rs.getBannerImg());
                    } else {
                        bl.setBannerPhysicalImg(null);
                        bl.setBannerImg(null);
                    }

                    bannerLanguageService.saveBannerLanguage(bl);
                }
            }
        } catch (Exception e) {
            logger.error("createOrEditBannerLanguage: " + e.getMessage());
            throw new Exception("####createOrEditBannerLanguage####", e);
        }
    }

    /**
     * initLanguageList
     *
     * @param mav
     * @author TaiTM
     */
    @Override
    public void initLanguageList(ModelAndView mav) {
        CmsLanguageUtils.initLanguageList(mav);
    }

    /**
     * find Banner by code
     *
     * @param code
     * @return Banner
     * @author TaiTM
     */
    @Override
    public Banner findByCode(String code) {
        return bannerRepository.findByCode(code);
    }

    /**
     * find Banner by id
     *
     * @param id
     * @return Banner
     * @author TaiTM
     */
//    @Override
//    public Banner findById(Long id) {
//        return bannerRepository.findOne(id);
//    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.hdb.admin.service.BannerService#requestEditorDownload(java.
     * lang.String, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
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
    public List<BannerLanguageDto> findBannerLanguageByTypeAndDeviceLanguage(String bannerType, boolean isMobile,
            String languageCode, Integer status) {
        return bannerRepository.findBannerLanguageByTypeAndDeviceLanguage(bannerType, isMobile, languageCode, status);
    }

    @Override
    public int countDependencies(Long bannerId, List<Long> lstStatus) {
        return bannerRepository.countDependencies(bannerId, lstStatus);
    }

    @Override
    public List<Map<String, String>> listDependencies(Long bannerId, List<Long> lstStatus) {
        return bannerRepository.listDependencies(bannerId, lstStatus);
    }

    public String getMaxCode(String tableName, String prefix) {
        return bannerRepository.getMaxCode(tableName, prefix);
    }

//    @Override
//    public List<CommonSearchFilterDto> initListSearchFilter(BannerSearchDto searchDto, Locale locale) {
//        List<CommonSearchFilterDto> list = new ArrayList<>();
//        CommonSearchFilterDto code = commonSearchFilterUtils.createInputCommonSearchFilterDto("code",
//                msg.getMessage("banner.code", null, locale), searchDto.getCode());
//        new CommonSearchFilterDto();
//        list.add(code);
//
//        CommonSearchFilterDto title = commonSearchFilterUtils.createInputCommonSearchFilterDto("title",
//                msg.getMessage("banner.name", null, locale), searchDto.getTitle());
//        list.add(title);
//
//        List<Select2Dto> listDataType = select2DataService.getConstantData("M_BANNER", "TYPE", locale.toString());
//        CommonSearchFilterDto bannerType = commonSearchFilterUtils.createSelectCommonSearchFilterDto("bannerType",
//                msg.getMessage("banner.type", null, locale), searchDto.getBannerType(), listDataType);
//        list.add(bannerType);
//
//        List<Select2Dto> listDataDevice = select2DataService.getConstantData("M_BANNER", "DEVICE", locale.toString());
//        CommonSearchFilterDto bannerDevice = commonSearchFilterUtils.createSelectCommonSearchFilterDto("bannerDevice",
//                msg.getMessage("banner.device", null, locale), searchDto.getBannerDevice(), listDataDevice);
//        list.add(bannerDevice);
//
//        return list;
//    }
    @Override
    public List<CommonSearchFilterDto> initListSearchFilter(BannerSearchDto searchDto, Locale locale) {
    	
      List<CommonSearchFilterDto> list = BannerService.super.initListSearchFilter(searchDto, locale);
      List<Select2Dto> listDataType = select2DataService.getConstantData("M_BANNER", "TYPE", locale.toString());
      List<Select2Dto> listDataDevice = select2DataService.getConstantData("M_BANNER", "DEVICE", locale.toString());
      List<CommonSearchFilterDto>  rs = new ArrayList<>();
      if (CollectionUtils.isNotEmpty(list)) {
          for (CommonSearchFilterDto filter : list) {
        	  if ("bannerTypeName".equals(filter.getField())) {
        		  
        	      CommonSearchFilterDto bannerType = commonSearchFilterUtils.createSelectCommonSearchFilterDto("bannerTypeName",
        	    		  msg.getMessage("banner.type", null, locale), searchDto.getBannerType(), listDataType);
        	      rs.add(bannerType);
        	      
              } else if ("bannerDeviceName".equals(filter.getField())) {
                CommonSearchFilterDto bannerDevice = commonSearchFilterUtils.createSelectCommonSearchFilterDto("bannerDeviceName",
                        msg.getMessage("banner.device", null, locale), searchDto.getBannerDevice(), listDataDevice);
                rs.add(bannerDevice);
              }
              else if(!"bannerPhysicalUrl".equals(filter.getField()) && filter.getField() != null)
              {
            	  rs.add(filter);
              }
          }
      }
      return rs;
    }
    @Override
    public BannerEditDto getEditDtoById(Long id, Locale locale) {
        return getBannerEdit(id, locale);
    }

    @Override
    public void saveOrUpdate(BannerEditDto editDto, Locale locale) throws Exception {
        addOrEdit(editDto, editDto.getRequestToken(), null, locale);
    }

    @Override
    public void deleteDataById(Long id) throws Exception {
        Banner banner = findBannerById(id);
        deleteBanner(banner);
    }

    @Override
    public List<BannerLanguageSearchDto> getListForSort(BannerSearchDto searchDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateSortAll(BannerSearchDto searchDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getEnumColumnForExport() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String getTemplateNameForExport(Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommonSearchFilterUtils getCommonSearchFilterUtils() {
        return commonSearchFilterUtils;
    }
}
