/*******************************************************************************
 * Class        ：EmailTemplateServiceImpl
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：SonND
 * Change log   ：2020/12/23：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaEmailTemplateDto;
import vn.com.unit.core.dto.JcaEmailTemplateSearchDto;
import vn.com.unit.core.entity.JcaEmailTemplate;
import vn.com.unit.core.enumdef.param.JcaEmailTemplateSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaEmailTemplateService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.req.dto.EmailTemplateAddInfoReq;
import vn.com.unit.ep2p.core.req.dto.EmailTemplateUpdateInfoReq;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.res.EmailTemplateInfoRes;
import vn.com.unit.ep2p.service.EmailTemplateService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EmailTemplateServiceImpl extends AbstractCommonService implements EmailTemplateService {

    @Autowired
    private JcaEmailTemplateService jcaEmailTemplateService;

    @Autowired
    private MasterCommonService masterCommonService;
    
    @Override
    public ObjectDataRes<JcaEmailTemplateDto>  search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
        ObjectDataRes<JcaEmailTemplateDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaEmailTemplate.class,JcaEmailTemplateService.TABLE_ALIAS_JCA_EMAIL_TEMPLATE);
            /** init param search repository */
            JcaEmailTemplateSearchDto reqSearch = this.buildJcaEmailTemplateSearchDto(commonSearch);
            
            int totalData = this.countJcaEmailDtoByCondition(reqSearch);
            List<JcaEmailTemplateDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getJcaEmailTemplateDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022801_APPAPI_EMAIL_TEMPLATE_LIST_ERROR);
        }
        return resObj;
    }
    

    private JcaEmailTemplateSearchDto buildJcaEmailTemplateSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaEmailTemplateSearchDto reqSearch = new JcaEmailTemplateSearchDto();
        
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
//        Boolean actived = null != commonSearch.getFirst("actived") ? Boolean.valueOf(commonSearch.getFirst("actived")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
        
        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaEmailTemplateSearchEnum.valueOf(enumValue)) {
                case CODE:
                    reqSearch.setCode(keySearch);
                    break;
                case TEMPLATE_NAME:
                    reqSearch.setTemplateName(keySearch);
                    break;
                    
                case CREATED_BY:
                    reqSearch.setCreatedBy(keySearch);
                    break;

                default:
                    reqSearch.setCode(keySearch);
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

    @Override
    public JcaEmailTemplateDto save(JcaEmailTemplateDto jcaEmailTemplateDto) throws DetailException {
        JcaEmailTemplate jcaEmailTemplate = jcaEmailTemplateService.saveJcaEmailTemplateDto(jcaEmailTemplateDto);
        jcaEmailTemplateDto.setId(jcaEmailTemplate.getId());
        return jcaEmailTemplateDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long emailTemplateId) throws DetailException {
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateDtoById(emailTemplateId);
        if(null != jcaEmailTemplateDto) {
            try {
                // hardcode
                jcaEmailTemplateService.deleteJcaEmailTemplateDto(emailTemplateId);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022805_APPAPI_EMAIL_TEMPLATE_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022804_APPAPI_EMAIL_TEMPLATE_NOT_FOUND, true);
        }
    }

    @Override
    public JcaEmailTemplateDto detail(Long emailTemplateId) throws DetailException {
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateDtoById(emailTemplateId);
        if(null != jcaEmailTemplateDto) {
            return jcaEmailTemplateDto;
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022804_APPAPI_EMAIL_TEMPLATE_NOT_FOUND, true);
        }
    }
    
 
    private void setCompanyId(Long companyId, JcaEmailTemplateDto jcaEmailTemplateDto)  throws DetailException {
//        if(null != companyId) {
//            CompanyInfoRes  companyInfoRes = companyService.getCompanyInfoResById(companyId);
//            if(null != companyInfoRes) {
//                jcaEmailTemplateDto.setCompanyId(companyId);
//            }else {
//                throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND, true);
//            }
//
//         }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(EmailTemplateUpdateInfoReq emailTemplateUpdateInfoReq) throws DetailException {
        Long id = emailTemplateUpdateInfoReq.getEmailTemplateId();
        JcaEmailTemplateDto jcaEmailTemplateDto = jcaEmailTemplateService.getJcaEmailTemplateDtoById(id);
        if(null != jcaEmailTemplateDto) {
          try {
              jcaEmailTemplateDto.setName(emailTemplateUpdateInfoReq.getTemplateName());
              jcaEmailTemplateDto.setTemplateContent(emailTemplateUpdateInfoReq.getTemplateContent());
              jcaEmailTemplateDto.setTemplateSubject(emailTemplateUpdateInfoReq.getSubject());
              this.setCompanyId(emailTemplateUpdateInfoReq.getCompanyId(), jcaEmailTemplateDto);
              this.save(jcaEmailTemplateDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022803_APPAPI_EMAIL_TEMPLATE_UPDATE_INFO_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022804_APPAPI_EMAIL_TEMPLATE_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailTemplateInfoRes create(EmailTemplateAddInfoReq emailTemplateAddInfoReq) throws DetailException {
        JcaEmailTemplateDto jcaEmailTemplateDto = new JcaEmailTemplateDto();
        try {
            jcaEmailTemplateDto.setName(emailTemplateAddInfoReq.getTemplateName());
            jcaEmailTemplateDto.setCode(emailTemplateAddInfoReq.getCode());
            jcaEmailTemplateDto.setTemplateContent(emailTemplateAddInfoReq.getTemplateContent());
            jcaEmailTemplateDto.setTemplateSubject(emailTemplateAddInfoReq.getSubject());
            this.setCompanyId(emailTemplateAddInfoReq.getCompanyId(), jcaEmailTemplateDto);
            
            this.save(jcaEmailTemplateDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022802_APPAPI_EMAIL_TEMPLATE_ADD_ERROR);
        }
        return this.mapperJcaEmailTemplateDtoToEmailTemplateInfoRes(jcaEmailTemplateDto);
    }
    
    private EmailTemplateInfoRes mapperJcaEmailTemplateDtoToEmailTemplateInfoRes(JcaEmailTemplateDto jcaEmailTemplateDto){
        return objectMapper.convertValue(jcaEmailTemplateDto, EmailTemplateInfoRes.class);
    }


    @Override
    public int countJcaEmailDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto) {
        return jcaEmailTemplateService.countJcaEmailTemplateDtoByCondition(jcaEmailTemplateSearchDto);
    }

    @Override
    public List<JcaEmailTemplateDto> getJcaEmailTemplateDtoByCondition(JcaEmailTemplateSearchDto jcaEmailTemplateSearchDto, Pageable pageable) {
        return  jcaEmailTemplateService.getJcaEmailTemplateDtoByCondition(jcaEmailTemplateSearchDto, pageable);
    }


    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaEmailTemplateSearchEnum.values()); 
    }

}
