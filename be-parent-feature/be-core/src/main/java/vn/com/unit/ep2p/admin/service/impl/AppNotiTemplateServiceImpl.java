/*******************************************************************************
 * Class        ：AppNotiTemplateServiceImpl
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonSearchUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaNotiTemplateDto;
import vn.com.unit.core.dto.JcaNotiTemplateSearchDto;
import vn.com.unit.core.dto.LanguageDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaNotiTemplateService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.service.impl.JcaNotiTemplateServiceImpl;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.repository.AppNotiTemplateRepository;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.AppNotiTemplateService;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.dto.NotiTemplateSearchDto;
import vn.com.unit.ep2p.enumdef.NotiTemplateSearchEnum;

/**
 * <p>
 * AppNotiTemplateServiceImpl
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppNotiTemplateServiceImpl extends JcaNotiTemplateServiceImpl implements AppNotiTemplateService, AbstractCommonService {

    @Autowired
    private AppNotiTemplateRepository appNotiTemplateRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private JcaNotiTemplateService jcaNotiTemplateService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private LanguageService languageService;
    
    private static final String LANGUAGE_LIST = "languageList";
    private static final String COMPANY_LIST = "companyList";

    @Override
    public List<Select2Dto> getSelect2DtoList(String keySearch, Long companyId, boolean isPaging) {
        return appNotiTemplateRepository.getSelect2DtoList(keySearch, companyId, isPaging);
    }

    @Override
    public PageWrapper<JcaNotiTemplateDto> getJcaNotiTemplateDtoList(NotiTemplateSearchDto searchDto, int pageSize, int page)
            throws DetailException {
        // Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaNotiTemplateDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        
        JcaNotiTemplateSearchDto reqSearch = this.buildJcaNotiTemplateSearchDto(searchDto);
        
        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, pageSize), JcaNotiTemplateService.class,
                JcaNotiTemplateService.TABLE_ALIAS_JCA_NOTI_TEMPLATE);
        int count = jcaNotiTemplateService.countJcaNotiTemplateDtoByCondition(reqSearch);
        List<JcaNotiTemplateDto> resultList = new ArrayList<>();
        if (count > 0) {
            resultList = jcaNotiTemplateService.getJcaNotiTemplateDtoListByCondition(reqSearch, pageableAfterBuild);
        }
        pageWrapper.setDataAndCount(resultList, count);
        return pageWrapper;
    }

    private JcaNotiTemplateSearchDto buildJcaNotiTemplateSearchDto(NotiTemplateSearchDto searchDto) {
        JcaNotiTemplateSearchDto reqSearch = new JcaNotiTemplateSearchDto();
        reqSearch.setCompanyId(searchDto.getCompanyId());
        String keySearch = searchDto.getFieldSearch();
        List<String> fieldSearch = searchDto.getFieldValues();
        if(CommonCollectionUtil.isNotEmpty(fieldSearch)) {
            for (String field : fieldSearch) {
                switch (NotiTemplateSearchEnum.valueOf(field)) {
                case CODE:
                    reqSearch.setCode(keySearch);
                    break;
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
                default:
                    reqSearch.setCode(keySearch);
                    reqSearch.setName(keySearch);
                    break;
                }
            }
        } else {
            reqSearch.setCode(keySearch);
            reqSearch.setName(keySearch);
        }
        return reqSearch;
    }

    @Override
    public JCommonService getCommonService() {
        return commonService;
    }

    @Override
    public void initScreenEdit(ModelAndView mav, JcaNotiTemplateDto objectDto, Locale locale) {
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject(COMPANY_LIST, companyList);
        //add languageList
        List<LanguageDto> languageList = languageService.getLanguageDtoList();
        mav.addObject(LANGUAGE_LIST, languageList);
    }

    @Override
    public void initScreenList(ModelAndView mav, NotiTemplateSearchDto searchDto, Locale locale) {
        // set init search
        CommonSearchUtil.setSearchSelect(NotiTemplateSearchEnum.class, mav);
        // Add company list
        List<Select2Dto> companyList = companyService.findByListCompanyId(UserProfileUtils.getCompanyIdList(), UserProfileUtils.isCompanyAdmin());
        mav.addObject(COMPANY_LIST, companyList);
    }

}
