/*******************************************************************************
 * Class        TemplateServiceImpl
 * Created date 2018/08/16
 * Lasted date  2018/08/16
 * Author       phatvt
 * Change log   2018/08/1601-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
//import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaEmailTemplateSearchDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.dts.constant.DtsConstant;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.repository.TemplateRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.TemplateService;
import vn.com.unit.ep2p.dto.TemplateEmailDto;
import vn.com.unit.ep2p.dto.TemplateSearchDto;
import vn.com.unit.ep2p.enumdef.TemplateSearchEnum;

/**
 * TemplateServiceImpl
 * 
 * @version 01-00
 * @since 01-00AccountServiceImpl
 * @author phatvt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TemplateServiceImpl implements TemplateService, AbstractCommonService{

    @Autowired
    private TemplateRepository templateRepository;
    
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
    public void saveTemplate(JcaEmailTemplate template){
        //template.setActived(1);
        templateRepository.save(template);
    }
    
    @Override
    public List<Select2Dto> getTemplate(String term, String fileFormat){
        return templateRepository.getTemplate(term, fileFormat);
    }

    @Override
    public PageWrapper<JcaEmailTemplateDto> doSearch(int page, TemplateSearchDto searchDto, int pageSize,Locale locale) throws DetailException {
        // Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaEmailTemplateDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        if (null == searchDto)
            searchDto = new TemplateSearchDto();
        // set SearchParm
        //setSearchParm(searchDto);

        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(
                PageRequest.of(page - 1, sizeOfPage, Sort.by("ID").descending()), JcaEmailTemplate.class,
                JcaEmailTemplateService.TABLE_ALIAS_JCA_EMAIL_TEMPLATE);

          /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaEmailTemplateSearchDto reqSearch = this.buildJcaEmailTemplateSearchDto(commonSearch);
        
        int count = jcaEmailTemplateService.countJcaEmailTemplateDtoByCondition(reqSearch);
        List<JcaEmailTemplateDto> result = new ArrayList<>();
        if (count > 0) {
//            int currentPage = pageWrapper.getCurrentPage();
//            int startIndex = (currentPage - 1) * sizeOfPage;
//            result = templateRepository.findAllTemplateListByCondition(startIndex, sizeOfPage, searchDto,locale.getLanguage());
            result = jcaEmailTemplateService.getJcaEmailTemplateDtoByCondition(reqSearch, pageableAfterBuild);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }
    
    private JcaEmailTemplateSearchDto buildJcaEmailTemplateSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaEmailTemplateSearchDto reqSearch = new JcaEmailTemplateSearchDto();
        
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
                    reqSearch.setTemplateName(keySearch);
                    break;
//                case TEMPLATE_NAME:
//                    reqSearch.setTemplateName(keySearch);
//                    break;
//                    
                case CREATE:
                    reqSearch.setCreatedBy(keySearch);
                    break;

                default:
//                    reqSearch.setCode(keySearch);
                    reqSearch.setTemplateName(keySearch);
                    reqSearch.setCreatedBy(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setCode(keySearch);
            reqSearch.setTemplateName(keySearch);
            reqSearch.setCreatedBy(keySearch);
        }
        
        return  reqSearch;
    }
    
//    private void setSearchParm(TemplateSearchDto searchDto) {
//        if (null == searchDto.getFieldValues()) {
//            searchDto.setFieldValues(new ArrayList<String>());
//        }
//
//        if (searchDto.getFieldValues().isEmpty()) {
//            searchDto.setTemplateName(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim()
//                    : searchDto.getFieldSearch());
//            searchDto.setCreatedBy(searchDto.getFieldSearch() != null ? searchDto.getFieldSearch().trim()
//                    : searchDto.getFieldSearch());
//        } else {
//            for (String field : searchDto.getFieldValues()) {
//                if (StringUtils.equals(field, TemplateSearchEnum.NAME.name())) {
//                    searchDto.setTemplateName(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, TemplateSearchEnum.CREATE.name())) {
//                    searchDto.setCreatedBy(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//                if (StringUtils.equals(field, TemplateSearchEnum.FILENAME.name())) {
//                    searchDto.setFileName(searchDto.getFieldSearch().trim());
//                    continue;
//                }
//            }
//        }
//        // Add company_id
//        searchDto.setCompanyId(searchDto.getCompanyId());
////        searchDto.setCompanyAdmin(UserProfileUtils.isCompanyAdmin());
////        searchDto.setCompanyIdList(UserProfileUtils.getCompanyIdList());
//    }
    
    @Override
    public JcaEmailTemplate getTemplateById(Long templateId){
        return templateRepository.findOne(templateId);
    }
    
    @Override
    public List<TemplateEmailDto> getTemplateDtoById(Long templateId){
        List<TemplateEmailDto> result = new ArrayList<>();
//        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateDtoById(templateId);
//        List<JcaEmailTemplateLangDto> emailTemplateLangDtos = jcaEmailTemplateLangService.getJcaEmailTemplateLangDtoEmailTemplateId(templateId);
//        for (JcaEmailTemplateLangDto jcaEmailTemplateLangDto : emailTemplateLangDtos) {
//            TemplateEmailDto emailDto = new TemplateEmailDto();
//            emailDto.setTemplateId(jcaEmailTemplateDto.getId());
//            emailDto.setTemplateLangId(jcaEmailTemplateLangDto.getId());
//            emailDto.setCompanyId(jcaEmailTemplateDto.getCompanyId());
//            emailDto.setCode(jcaEmailTemplateDto.getCode());
//            emailDto.setFileName(jcaEmailTemplateDto.getTemplateName());
//            emailDto.setTemplateName(jcaEmailTemplateDto.getTemplateName());
//            emailDto.setLangCode(jcaEmailTemplateLangDto.getLangCode());
//            emailDto.setMobileNotification(jcaEmailTemplateLangDto.getNotification());
//            emailDto.setSubject(jcaEmailTemplateLangDto.getTitle());
//            emailDto.setTemplateContent(jcaEmailTemplateDto.getTemplateContent());
//            //emailDto.setActived(actived);
//            result.add(emailDto);
//        }
        return result;
    }
    
    @Override
    public void deleteTemplate(Long templateId){
//    	JcaEmailTemplate template = templateRepository.findOne(templateId);
//        if(null!=template) {
//            //template.setDeletedBy(UserProfileUtils.getUserNameLogin());
//            //template.setActived(0);
//            template.setDeletedDate(comService.getSystemDateTime());
//            templateRepository.save(template);
//        }
//        /*templateRepository.deleteTemplate(templateId, UserProfileUtils.getUserNameLogin());*/
        jcaEmailTemplateService.deleteJcaEmailTemplate(templateId);
    }

    @Override
    public List<Select2Dto> getTemplateByCompanyId(String term, String fileFormat, Long companyId) {
        return templateRepository.getTemplateByCompanyId(term, fileFormat, companyId, true);
    }

	@Override
	public List<Select2Dto> getTemplateswithoutWorkflow(List<String> names) throws Exception {
		return templateRepository.findTemplateswithoutWorkflow(names);
	}

    @Override
    public JcaEmailTemplate getByCodeAndCompanyId(String code, Long companyId) {
        if(StringUtils.isNotBlank(code)) {
            return templateRepository.getByCodeAndCompanyId(code, companyId);
        }
        return null;
        
    }

	@Override
	public String getNameById(Long templateId) throws Exception {
		return templateRepository.findNameById(templateId);
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
    public List<String> getListParam(JcaEmailTemplate template) {
        LinkedHashSet<String> hashParam = new LinkedHashSet<String>();
        if(null != template) {
            hashParam.addAll(getParams(template.getTemplateSubject()));
            //hashParam.addAll(getParams(template.getMobileNotification()));
            hashParam.addAll(getParams(template.getTemplateContent()));
        }
        List<String> listParam = new ArrayList<String>(hashParam);
        return listParam;
    }
    
    @Override
    public TemplateEmailDto getMapLangAndSubjectNotificationById(Long templateId) {
//        Map<String, String> resMapContent = new HashMap<String, String>();
//        Map<String, String> resMapSubject = new HashMap<String, String>();
        TemplateEmailDto templateEmailDto = new TemplateEmailDto();
//        List<TemplateEmailDto> templateDtoLst = templateRepository.findTemplateDtoById(templateId);
//        
//        for (TemplateEmailDto templateDto: templateDtoLst){
//            resMapSubject.put(templateDto.getLangCode(),templateDto.getSubject());
//            resMapContent.put(templateDto.getLangCode(),templateDto.getMobileNotification());
//        }
//        
//        templateEmailDto.setTemplateEmailLst(templateDtoLst);
//        templateEmailDto.setActived(templateDtoLst.get(0).getActived());
//        templateEmailDto.setCode(templateDtoLst.get(0).getCode());
//        templateEmailDto.setCompanyId(templateDtoLst.get(0).getCompanyId());
//        templateEmailDto.setCompanyName(templateDtoLst.get(0).getCompanyName());
//        templateEmailDto.setDisplayFlag(templateDtoLst.get(0).getDisplayFlag());
//        templateEmailDto.setFileFormat(templateDtoLst.get(0).getFileFormat());
//        templateEmailDto.setFileName(templateDtoLst.get(0).getFileName());
//        templateEmailDto.setFileSize(templateDtoLst.get(0).getFileSize());
//        templateEmailDto.setTemplateId(templateDtoLst.get(0).getTemplateId());
//        templateEmailDto.setTemplateName(templateDtoLst.get(0).getTemplateName());
//        templateEmailDto.setUrl(templateDtoLst.get(0).getUrl());
//        templateEmailDto.setTemplateContent(templateDtoLst.get(0).getTemplateContent());
//        
//        /** set maping */
//        templateEmailDto.setContentNotificationMap(resMapContent);
//        templateEmailDto.setSubjectNotificationMap(resMapSubject);
        
        return templateEmailDto;
    }

    @Override
    public vn.com.unit.common.service.JCommonService getCommonService() {
        return comService;
    }

    @Override
    public TemplateEmailDto saveTemplateDto(TemplateEmailDto templateEmailDto) {
        
        JcaEmailTemplateDto jcaEmailTemplateDto = new JcaEmailTemplateDto();
        
        jcaEmailTemplateDto.setId(templateEmailDto.getTemplateId());
        jcaEmailTemplateDto.setName(templateEmailDto.getTemplateName());
        jcaEmailTemplateDto.setCode(templateEmailDto.getCode());
        jcaEmailTemplateDto.setTemplateContent(templateEmailDto.getTemplateContent());
        jcaEmailTemplateDto.setTemplateSubject(templateEmailDto.getSubject());
        jcaEmailTemplateDto.setCompanyId(templateEmailDto.getCompanyId());
//        
//        List<JcaEmailTemplateLangDto> jcaEmailTemplateLangDtos = new ArrayList<>();
//        List<TemplateEmailDto> templateDtoLst = templateEmailDto.getTemplateEmailLst();
//        for (TemplateEmailDto templateDto: templateDtoLst){
//            
//            JcaEmailTemplateLangDto emailTemplateLangDto = new JcaEmailTemplateLangDto();
//            emailTemplateLangDto.setLangCode(templateDto.getLangCode());
//            emailTemplateLangDto.setTitle(templateDto.getSubject());
//            emailTemplateLangDto.setNotification(templateDto.getMobileNotification());
//            
//            jcaEmailTemplateLangDtos.add(emailTemplateLangDto);
//        }
//        jcaEmailTemplateDto.setJcaEmailTemplateLangDtos(jcaEmailTemplateLangDtos);
        
        templateEmailDto.setTemplateId(jcaEmailTemplateService.saveJcaEmailTemplateDto(jcaEmailTemplateDto).getId());
        
        return templateEmailDto;
    }

    @Override
    public TemplateEmailDto getTemplateEmailDtoById(Long id) {
        TemplateEmailDto result = new TemplateEmailDto();
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateDtoById(id);
//        List<JcaEmailTemplateLangDto> emailTemplateLangDtos = jcaEmailTemplateLangService.getJcaEmailTemplateLangDtoEmailTemplateId(templateId);
//        for (JcaEmailTemplateLangDto jcaEmailTemplateLangDto : emailTemplateLangDtos) {
//            TemplateEmailDto emailDto = new TemplateEmailDto();
//            emailDto.setTemplateId(jcaEmailTemplateDto.getId());
//            emailDto.setTemplateLangId(jcaEmailTemplateLangDto.getId());
//            emailDto.setCompanyId(jcaEmailTemplateDto.getCompanyId());
//            emailDto.setCode(jcaEmailTemplateDto.getCode());
//            emailDto.setFileName(jcaEmailTemplateDto.getTemplateName());
//            emailDto.setTemplateName(jcaEmailTemplateDto.getTemplateName());
//            emailDto.setLangCode(jcaEmailTemplateLangDto.getLangCode());
//            emailDto.setMobileNotification(jcaEmailTemplateLangDto.getNotification());
//            emailDto.setSubject(jcaEmailTemplateLangDto.getTitle());
//            emailDto.setTemplateContent(jcaEmailTemplateDto.getTemplateContent());
//            //emailDto.setActived(actived);
//            result.add(emailDto);
//        }
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
    public void InitScreenEdit(ModelAndView mav, TemplateEmailDto objectDto, Locale locale) {
     // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject("companyList", companyList);
//        String listParam = systemConfig.getConfig(AppSystemSettingKey.LIST_PARAM_TEMPLATE);
//        mav.addObject("listParam", listParam);
        // add parameter list
//        List<Select2Dto> listParamSelect2Dto = getParamList();
//        mav.addObject("listParamSelect2Dto", listParamSelect2Dto);
    }
    
}
