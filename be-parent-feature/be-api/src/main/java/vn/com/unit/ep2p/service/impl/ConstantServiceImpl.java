/*******************************************************************************
 * Class        ：ConstantService
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaConstantDto;
import vn.com.unit.core.dto.JcaConstantSearchDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.entity.Language;
import vn.com.unit.core.enumdef.param.JcaConstantSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaConstantService;
import vn.com.unit.core.service.LanguageService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.ConstantLanguageInfoReq;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.core.utils.NullAwareBeanUtils;
import vn.com.unit.ep2p.dto.req.ConstantAddReq;
import vn.com.unit.ep2p.dto.res.ConstantInfoRes;
import vn.com.unit.ep2p.service.ConstantService;

/**
 * ConstantService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ConstantServiceImpl extends AbstractCommonService implements ConstantService {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JcaConstantService jcaConstantService;
    
    @Autowired
    private MasterCommonService masterCommonService;
    
    @Autowired
    private LanguageService languageService;

    @Override
    public ObjectDataRes<JcaConstantDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {

        ObjectDataRes<JcaConstantDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaConstant.class, JcaConstantService.TABLE_ALIAS_JCA_CONSTANT);
            /** init param search repository */
            JcaConstantSearchDto reqSearch = this.buildJcaConstantSearchDto(commonSearch);

            int totalData = this.countJcaConstantByCondition(reqSearch);
            List<JcaConstantDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getConstantDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023201_APPAPI_CONSTANT_LIST_ERROR);
        }
        return resObj;

    }

    @Override
    public int countJcaConstantByCondition(JcaConstantSearchDto jcaConstantSearchDto) {
        return jcaConstantService.countJcaConstantByCondition(jcaConstantSearchDto);
    }

    @Override
    public List<JcaConstantDto> getConstantDtoByCondition(JcaConstantSearchDto jcaConstantSearchDto, Pageable pageable) {
        return jcaConstantService.getListJcaConstantDtoByCondition(jcaConstantSearchDto, pageable);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaConstantDto save(JcaConstantDto objectDto) throws DetailException {
    	jcaConstantService.saveJcaConstantDto(objectDto);
        return objectDto;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ConstantAddReq reqConstantAddDto) throws Exception {
       this.create(reqConstantAddDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<JcaConstantDto> create(ConstantAddReq reqConstantAddDto) throws Exception {
        List<JcaConstantDto> jcaCons = new ArrayList<>();
    	try {
        	JcaConstantDto jcaConstantDto = objectMapper.convertValue(reqConstantAddDto, JcaConstantDto.class);

            boolean checkCodeExists = jcaConstantService.checkCodeExistsByGroupCodeAndKind(jcaConstantDto.getCode(),
                    jcaConstantDto.getGroupCode(), jcaConstantDto.getKind(), null);

            if (checkCodeExists) {
                throw new DetailException(AppApiExceptionCodeConstant.E101600_CODE_EXISTS_ERROR);
            }
            
            List<ConstantLanguageInfoReq> constantLang = reqConstantAddDto.getLanguages();
            List<Language> languages =  languageService.findAllActive();
            jcaCons = constantLang.stream().map(con -> {
            	Long langId = languages.stream().filter(lang ->
            	CommonStringUtil.upperCase(con.getLangCode()).equals(CommonStringUtil.upperCase(lang.getCode())))
            			.findFirst().get().getId();
            	jcaConstantDto.setLangCode(con.getLangCode());
            	jcaConstantDto.setLangId(langId);
            	jcaConstantDto.setName(con.getName());
            	
            	jcaConstantService.saveJcaConstantDto(jcaConstantDto);

            	return jcaConstantDto;
            }).collect(Collectors.toList());
            

        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023202_APPAPI_CONSTANT_ADD_ERROR);
        }
        return jcaCons;

    }

    @Override
    public ConstantInfoRes getConstantInfoResById(Long id) throws Exception {
        JcaConstantDto jcaConstantDto = this.detail(id);
        return objectMapper.convertValue(jcaConstantDto, ConstantInfoRes.class);
    }

    private JcaConstantSearchDto buildJcaConstantSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaConstantSearchDto reqSearch = new JcaConstantSearchDto();

        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        String groupCode = null != commonSearch.getFirst("groupCode") ? commonSearch.getFirst("groupCode") : AppApiConstant.EMPTY;
        String kind = null != commonSearch.getFirst("kind") ? commonSearch.getFirst("kind") : AppApiConstant.EMPTY;
        String langCode = null != commonSearch.getFirst("langCode") ? commonSearch.getFirst("langCode") : AppApiConstant.EMPTY;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
        reqSearch.setGroupCode(groupCode);
        reqSearch.setKind(kind);
        reqSearch.setLangCode(langCode);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaConstantSearchEnum.valueOf(enumValue)) {
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
        }else {
            reqSearch.setCode(keySearch);
            reqSearch.setName(keySearch);
        }
        return reqSearch;
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaConstantSearchEnum.values()); 
    }

	/* (non-Javadoc)
	 * @see vn.com.unit.core.service.BaseRestService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) throws DetailException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see vn.com.unit.core.service.BaseRestService#detail(java.lang.Long)
	 */
	@Override
	public JcaConstantDto detail(Long id) throws DetailException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public ConstantInfoRes getConstantInfoResByCodeAndGroupCodeAndKind(String groupCode, String kind, String code,
            String langCode) {

        JcaConstantDto dto = jcaConstantService.getJcaConstantDtoByCodeAndGroupCodeAndKind(code, groupCode, kind,
                langCode);
        ConstantInfoRes res = null;

        if (dto != null) {
            res = new ConstantInfoRes();
            try {
                NullAwareBeanUtils.copyPropertiesWONull(dto, res);
            } catch (Exception e) {
                logger.error("Exception ", e);
            }
        }

        return res;
    }
}
