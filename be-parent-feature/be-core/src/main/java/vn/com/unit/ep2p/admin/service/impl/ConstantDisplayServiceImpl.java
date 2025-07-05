/*******************************************************************************
 * Class        ConstantDisplayServiceImpl
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.common.utils.CommonUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.dto.JcaGroupConstantDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.entity.JcaGroupConstant;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.core.service.impl.JcaConstantServiceImpl;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.service.AbstractCommonService;
import vn.com.unit.ep2p.admin.service.ConstantDisplayService;

/**
 * ConstantDisplayServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Service
@Primary
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ConstantDisplayServiceImpl extends JcaConstantServiceImpl implements ConstantDisplayService , AbstractCommonService {

    private static final Logger logger = LoggerFactory.getLogger(ConstantDisplayServiceImpl.class);

//    public void copyProperties(JcaConstantDto data, ConstantDisplayLanguage entity, String mLanguageCode) {
//        entity.setmLanguageCode(mLanguageCode);
//        if(StringUtils.equals(mLanguageCode, "EN")) {
//            entity.setCatAbbrName(data.getName());
//            entity.setCatOfficialName(data.getName());
//        }
//        if(StringUtils.equals(mLanguageCode, "VI")) {
//            entity.setCatAbbrName(data.getName());
//            entity.setCatOfficialName(data.getName());
//        }
//    }
//    @Resource(name = "freemarkerConfig")
//    private FreeMarkerConfigurer freemarkerConfig;
    
    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    JRepositoryService jrepositoryService;
    
    @Autowired
    private LanguageService languageService;

	/** The com service. */
	@Autowired
	private JCommonService comService;
	
    @Autowired
    private ObjectMapper objectMapper;
    
    // Model mapper
    ModelMapper modelMapper = new ModelMapper();
    
    public List<String> checkLanguage(JcaConstantDto data) {
        List<String> mLanguageCode = new ArrayList<>();
        if(StringUtils.isNotBlank(data.getName()) || StringUtils.isNotBlank(data.getName())) {
            mLanguageCode.add("EN");
        }
        
        if(StringUtils.isNotBlank(data.getName()) || StringUtils.isNotBlank(data.getName())) {
            mLanguageCode.add("VI");
        }
        return mLanguageCode;
    }



    @Override
    public List<Select2Dto> findType() {
    	String lang = UserProfileUtils.getLanguage();
        return this.getAllType(lang);
    }

    @Override
    public List<JcaConstantDto> findConstantDisplayByTypeAndLang(String type, Locale locale) {
        String lang = locale.getLanguage();
        return this.getListJcaConstantDtoByKind(type, lang);
    }
    @Override
    @Transactional
	public PageWrapper<JcaConstantDto> doSearch(int page, JcaConstantSearchDto searchDto, int pageSize)
			throws DetailException {
    	// Get listPageSize, sizeOfPage
        List<Integer> listPageSize = systemConfig.getListPage(pageSize);
        int sizeOfPage = systemConfig.getSizeOfPage(listPageSize, pageSize);
        PageWrapper<JcaConstantDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        if (null == searchDto) {
            searchDto = new JcaConstantSearchDto();
        }
        pageWrapper.setListPageSize(listPageSize);      
        pageWrapper.setSizeOfPage(sizeOfPage);
        
        /** init pageable */
        Pageable pageableAfterBuild = this.buildPageable(PageRequest.of(page - 1, sizeOfPage), JcaConstant.class, TABLE_ALIAS_JCA_CONSTANT);

          /** init param search repository */
        MultiValueMap<String, String> commonSearch = CommonUtil.convert(searchDto, objectMapper);
        JcaConstantSearchDto reqSearch = this.buildJcaConstantSearchDto(commonSearch);

        int count = countJcaConstantByCondition(reqSearch);
        List<JcaConstantDto> result = new ArrayList<>();
        if (count > 0) {
//            int currentPage = pageWrapper.getCurrentPage();
//            int startIndex = (currentPage - 1) * sizeOfPage;
//            result = emailRepository.findAllEmailListByCondition(startIndex, sizeOfPage, searchDto);
            result = getListJcaConstantDtoByCondition(reqSearch, pageableAfterBuild);
        }
        
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
	}
    @Override
	public void submit(@Valid JcaGroupConstantDto dto) {
		// TODO Auto-generated method stub
		try {
			List<Language> listLang =  languageService.findAllActive();
			for(Language lang : listLang) {
				
				dto.setLangId(lang.getId());
				dto.setLangCode(lang.getCode());
				if(lang.getCode().equals(dto.getLangCodeEn())) {
					JcaGroupConstant jcaGroupConstant = objectMapper.convertValue(dto, JcaGroupConstant.class);
					
					jcaGroupConstant.setName(dto.getNameEn());
					saveJcaGroupConstantDto(jcaGroupConstant);
					if(CollectionUtils.isNotEmpty(dto.getConstants())) {
						for(JcaConstantDto constDto : dto.getConstants()) {
							constDto.setGroupCode(dto.getCode());
							constDto.setGroupId(dto.getConstantDisplayOrder());
							constDto.setLangCode(lang.getCode());
							constDto.setLangId(lang.getId());
							constDto.setName(constDto.getNameEn());
							saveJcaConstantDto(constDto);
						}
					}
				}else if(lang.getCode().equals(dto.getLangCodeVi())) {
					JcaGroupConstant jcaGroupConstant = objectMapper.convertValue(dto, JcaGroupConstant.class);
					jcaGroupConstant.setName(dto.getNameVi());
					saveJcaGroupConstantDto(jcaGroupConstant);
					if(CollectionUtils.isNotEmpty(dto.getConstants())) {
						for(JcaConstantDto constDto : dto.getConstants()) {
							constDto.setGroupCode(dto.getCode());
							constDto.setGroupId(dto.getConstantDisplayOrder());
							constDto.setLangCode(lang.getCode());
							constDto.setLangId(lang.getId());
							constDto.setName(constDto.getNameVi());
							saveJcaConstantDto(constDto);
						}
					}
				}
				
			}
			
		} catch(Exception e) {
			logger.error("##saveConstantDisplay##", e);
            throw e;
		}
	}



	@Override
	public JCommonService getCommonService() {
		// TODO Auto-generated method stub
		 return comService;
	}
	private JcaConstantSearchDto buildJcaConstantSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaConstantSearchDto reqSearch = new JcaConstantSearchDto();
		/*
		 * String keySearch =
		 * CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldSearch")) ?
		 * commonSearch.getFirst("fieldSearch") : DtsConstant.EMPTY; List<String>
		 * enumsValues =
		 * CommonStringUtil.isNotBlank(commonSearch.getFirst("fieldValues")) ?
		 * java.util.Arrays.asList(CommonStringUtil.split(commonSearch.getFirst(
		 * "fieldValues"), ",")) : null;
		 */
        String groupCode = CommonStringUtil.isNotBlank(commonSearch.getFirst("groupCode")) ? commonSearch.getFirst("groupCode") : null;
        String kind = CommonStringUtil.isNotBlank(commonSearch.getFirst("kind")) ? commonSearch.getFirst("kind") : null;
        String code = CommonStringUtil.isNotBlank(commonSearch.getFirst("code")) ? commonSearch.getFirst("code") : null;
        String name = CommonStringUtil.isNotBlank(commonSearch.getFirst("name")) ? commonSearch.getFirst("name") : null;
        
        reqSearch.setGroupCode(groupCode);
        reqSearch.setKind(kind);
        reqSearch.setCode(code);
        reqSearch.setName(name);
        
        return  reqSearch;
    }
}
