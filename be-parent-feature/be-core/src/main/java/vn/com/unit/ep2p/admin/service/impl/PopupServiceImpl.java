/*******************************************************************************
 * Class        TemplateServiceImpl
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.repository.PopupRepository;
import vn.com.unit.ep2p.admin.repository.TemplateRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.PopupService;
import vn.com.unit.ep2p.dto.Popup;
import vn.com.unit.ep2p.dto.PopupDto;
import vn.com.unit.ep2p.dto.PopupSearchDto;
import vn.com.unit.ep2p.dto.PopupSearchReqDto;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.enumdef.TemplateSearchEnum;

/**
 * TemplateServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PopupServiceImpl implements PopupService, AbstractCommonService{

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private PopupRepository popupRepository;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private CommonService comService;
    
    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Select2Dto> getTemplate(String term, String fileFormat){
        return templateRepository.getTemplate(term, fileFormat);
    }

    @Override
    public PageWrapper<PopupDto> doSearch(int page, PopupSearchDto searchDto, int pageSize, Locale locale) throws DetailException {
        // Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<PopupDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        if (null == searchDto)
            searchDto = new PopupSearchDto();
        /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        PopupSearchReqDto reqSearch = this.buildJcaEmailTemplateSearchDto(commonSearch);
        int count = popupRepository.countPopupByCondition(reqSearch);
        List<PopupDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = popupRepository.findAllPopupListByCondition(startIndex, sizeOfPage, reqSearch,locale.getLanguage());
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

    private PopupSearchReqDto buildJcaEmailTemplateSearchDto(MultiValueMap<String, String> commonSearch) {
        PopupSearchReqDto reqSearch = new PopupSearchReqDto();
        
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        String keySearch = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldSearch")) ? commonSearch.getFirst("fieldSearch")
                : DtsConstant.EMPTY;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldValues"))
                ? java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst("fieldValues"), ","))
                : null;
                
        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (TemplateSearchEnum.valueOf(enumValue)) {
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
//                case TEMPLATE_NAME:
//                    reqSearch.setTemplateName(keySearch);
//                    break;
//                    
                case CREATE:
                    reqSearch.setCreate(keySearch);
                    break;

                default:
//                    reqSearch.setCode(keySearch);
                    reqSearch.setName(keySearch);
                    reqSearch.setCreate(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setCode(keySearch);
            reqSearch.setName(keySearch);
            reqSearch.setCreate(keySearch);
        }
        
        return  reqSearch;
    }

    @Override
    public JcaEmailTemplate getTemplateById(Long templateId){
        return templateRepository.findOne(templateId);
    }
    

    @Override
    public void deleteTemplate(Long id){
        if(null != id) popupRepository.delete(id);
    }

	/**
	 * getParams
	 * @param content
	 * @return
	 * @author trieuvd
	 */
	public List<String> getParams(String content) {
        List<String> paramList = new ArrayList<String>();
        if(StringUtils.isNotBlank(content)) {
            Matcher matcher = ConstantCore.PARAM_PATTERN.matcher(content);
            while (matcher.find()) {
                String param = matcher.group(1);
                paramList.add(param);
            } 
        }
        return paramList;
    }


    @Override
    public TemplateEmailDto getTemplateEmailDtoById(Long id) {
        TemplateEmailDto result = new TemplateEmailDto();
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateDtoById(id);
        if(null != jcaEmailTemplateDto) {
            result = objectMapper.convertValue(jcaEmailTemplateDto, TemplateEmailDto.class);
            result.setTemplateId(jcaEmailTemplateDto.getId());
            result.setTemplateName(jcaEmailTemplateDto.getName());
            result.setSubject(jcaEmailTemplateDto.getTemplateSubject());
            result.setTemplateContent(jcaEmailTemplateService.unescapeTemplateHtml(jcaEmailTemplateDto.getTemplateContent()));
        }
        return result;
    }

    @Override
    public void InitScreenEdit(ModelAndView mav, PopupDto objectDto, Locale locale) {
     // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
    }

    @Override
    public PopupDto savePopup(PopupDto objectDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	    Long id = objectDto.getId();
        Popup popup = modelMapper.map(objectDto, Popup.class);
	    if(id != null) {
            popup.setUpdatedId(UserProfileUtils.getAccountId());
            popup.setUpdatedDate(new Date());
        } else {
            popup.setCreatedId(UserProfileUtils.getAccountId());
            popup.setCreatedDate(new Date());
        }
        popup.setTemplateContent(unescapeTemplateHtml(objectDto.getTemplateContent()));
        popupRepository.save(popup);
	    return  objectDto;
    }
    public String unescapeTemplateHtml(String templateHtmlEscape) {
        if(CommonStringUtil.isNotBlank(templateHtmlEscape)) {
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_QUOT, CommonConstant.CHAR_DOUBLE_QUOTATION_MARK+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_AMP, CommonConstant.CHAR_AMPERSAND+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_LT, CommonConstant.CHAR_IS_LESS_THAN+ CommonConstant.EMPTY);
            templateHtmlEscape = templateHtmlEscape.replaceAll(CommonConstant.ESCAPE_GT, CommonConstant.CHAR_IS_MORE_THAN+ CommonConstant.EMPTY);
            return templateHtmlEscape;
        }else {
            return templateHtmlEscape;
        }
    }
    @Override
    public PopupDto getPopupDtoById(Long id) {
	    Popup popup = popupRepository.findOne(id);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PopupDto popupDto = modelMapper.map(popup, PopupDto.class);
        popupDto.setTemplateContent(unescapeTemplateHtml(popupDto.getTemplateContent()));
        return popupDto;
    }

    @Override
    public JCommonService getCommonService() {
        return null;
    }

    @Override
    public List<Select2Dto> getListPopup() {
	    List<Popup> lst = (List<Popup>) popupRepository.findAll();
	    if(lst != null && !lst.isEmpty()) return lst.stream().map(x -> new Select2Dto(x.getCode(), x.getName(), x.getName())).collect(Collectors.toList());
        return new ArrayList<>();
    }
}
