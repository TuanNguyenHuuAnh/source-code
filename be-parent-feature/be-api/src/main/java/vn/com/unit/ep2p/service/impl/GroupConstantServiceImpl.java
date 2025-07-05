/*******************************************************************************
 * Class        ：GroupConstantService
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：tantm
 * Change log   ：2020/12/23：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

//import java.util.ArrayList;
//import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.MultiValueMap;

//import vn.com.unit.common.utils.CommonCollectionUtil;
//import vn.com.unit.common.utils.CommonStringUtil;
//import vn.com.unit.core.dto.JcaGroupConstantDto;
//import vn.com.unit.core.dto.JcaGroupConstantSearchDto;
//import vn.com.unit.core.entity.JcaGroupConstant;
//import vn.com.unit.core.enumdef.param.JcaGroupConstantSearchEnum;
//import vn.com.unit.core.res.ObjectDataRes;
//import vn.com.unit.core.service.JcaGroupConstantService;
//import vn.com.unit.dts.exception.DetailException;
//import vn.com.unit.ep2p.api.req.dto.GroupConstantAddReq;
//import vn.com.unit.ep2p.api.req.dto.GroupConstantUpdateReq;
//import vn.com.unit.ep2p.core.constant.AppApiConstant;
//import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
//import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
//import vn.com.unit.ep2p.core.service.MasterCommonService;
//import vn.com.unit.ep2p.res.dto.GroupConstantInfoRes;
import vn.com.unit.ep2p.service.GroupConstantService;

/**
 * GroupConstantService
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class GroupConstantServiceImpl extends AbstractCommonService implements GroupConstantService {

//    @Autowired
//    private JcaGroupConstantService jcaGroupConstantService;
//    
//    @Autowired
//    private MasterCommonService masterCommonService;
//
//    @Override
//    public ObjectDataRes<JcaGroupConstantDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {
//
//        ObjectDataRes<JcaGroupConstantDto> resObj = null;
//        try {
//            /** init pageable */
//            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaGroupConstant.class,
//                    JcaGroupConstantService.TABLE_ALIAS_JCA_GROUP_CONSTANT);
//            /** init param search repository */
//            JcaGroupConstantSearchDto reqSearch = this.buildJcaGroupConstantSearchDto(commonSearch);
//
//            int totalData = this.countGroupConstantDtoByCondition(reqSearch);
//            List<JcaGroupConstantDto> datas = new ArrayList<>();
//            if (totalData > 0) {
//                datas = this.getGroupConstantDtoByCondition(reqSearch, pageableAfterBuild);
//            }
//            resObj = new ObjectDataRes<>(totalData, datas);
//        } catch (Exception e) {
//            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023101_APPAPI_GROUP_CONSTANT_LIST_ERROR);
//        }
//        return resObj;
//
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public JcaGroupConstantDto save(JcaGroupConstantDto objectDto) throws DetailException {
//
//        jcaGroupConstantService.saveJcaGroupConstantDto(objectDto);
//        return objectDto;
//
//    }
//
//    @Override
//    public void delete(Long id) throws DetailException {
//
//        JcaGroupConstant jcaGroupConstant = jcaGroupConstantService.getJcaGroupConstantById(id);
//        if (null != jcaGroupConstant) {
//            try {
//                // hardcode
//                jcaGroupConstant.setDeletedId(1L);
//                jcaGroupConstant.setDeletedDate(commonService.getSystemDate());
//                jcaGroupConstantService.saveJcaGroupConstant(jcaGroupConstant);
//            } catch (Exception e) {
//                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023105_APPAPI_GROUP_CONSTANT_DELETE_ERROR);
//            }
//        } else {
//            throw new DetailException(AppApiExceptionCodeConstant.E4023104_APPAPI_GROUP_CONSTANT_NOT_FOUND);
//        }
//
//    }
//
//    @Override
//    public JcaGroupConstantDto detail(Long id) throws DetailException {
//        JcaGroupConstantDto jcaGroupConstantDto = null;
//        try {
//            jcaGroupConstantDto = jcaGroupConstantService.getJcaGroupConstantDtoById(id);
//
//            if (null == jcaGroupConstantDto) {
//                throw new DetailException(AppApiExceptionCodeConstant.E4023104_APPAPI_GROUP_CONSTANT_NOT_FOUND);
//            }
//        } catch (Exception e) {
//            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023106_APPAPI_GROUP_CONSTANT_INFO_ERROR);
//        }
//        return jcaGroupConstantDto;
//
//    }
//
//    @Override
//    public int countGroupConstantDtoByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto) {
//        return jcaGroupConstantService.countJcaGroupConstantByCondition(jcaGroupConstantSearchDto);
//    }
//
//    @Override
//    public List<JcaGroupConstantDto> getGroupConstantDtoByCondition(JcaGroupConstantSearchDto jcaGroupConstantSearchDto,
//            Pageable pageable) {
//        return jcaGroupConstantService.getListJcaGroupConstantDtoHaveConstantByCondition(jcaGroupConstantSearchDto, pageable);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void update(GroupConstantUpdateReq reqGroupConstantUpdateDto) throws Exception {
//
//        Long id = reqGroupConstantUpdateDto.getId();
//
//        JcaGroupConstantDto jcaGroupConstant = jcaGroupConstantService.getJcaGroupConstantDtoById(id);
//        if (null != jcaGroupConstant) {
//            try {
//                jcaGroupConstant = objectMapper.convertValue(reqGroupConstantUpdateDto, JcaGroupConstantDto.class);
//                this.save(jcaGroupConstant);
//            } catch (Exception e) {
//                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021303_APPAPI_BUSINESS_DELETE_ERROR);
//            }
//        }
//
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public GroupConstantInfoRes create(GroupConstantAddReq reqGroupConstantAddDto) throws Exception {
//
//        JcaGroupConstantDto jcaGroupConstantDto = new JcaGroupConstantDto();
//        try {
//            jcaGroupConstantDto = objectMapper.convertValue(reqGroupConstantAddDto, JcaGroupConstantDto.class);
//
//            boolean checkCodeExists = jcaGroupConstantService.checkGroupCodeExists(jcaGroupConstantDto.getCode(),
//                    jcaGroupConstantDto.getCompanyId());
//
//            if (checkCodeExists) {
//                throw new DetailException(AppApiExceptionCodeConstant.E101600_CODE_EXISTS_ERROR);
//            }
//
//            this.save(jcaGroupConstantDto);
//        } catch (Exception e) {
//            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023102_APPAPI_GROUP_CONSTANT_ADD_ERROR);
//        }
//        return objectMapper.convertValue(jcaGroupConstantDto, GroupConstantInfoRes.class);
//
//    }
//
//    @Override
//    public GroupConstantInfoRes getGroupConstantInfoResById(Long id) throws Exception {
//        JcaGroupConstantDto jcaGroupConstantDto = this.detail(id);
//        return objectMapper.convertValue(jcaGroupConstantDto, GroupConstantInfoRes.class);
//    }
//
//    private JcaGroupConstantSearchDto buildJcaGroupConstantSearchDto(MultiValueMap<String, String> commonSearch) {
//        JcaGroupConstantSearchDto reqSearch = new JcaGroupConstantSearchDto();
//
//        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
//        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
//        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;
//        
//        reqSearch.setCompanyId(companyId);
//        
//        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
//            for (String enumValue : enumsValues) {
//                switch (JcaGroupConstantSearchEnum.valueOf(enumValue)) {
//                case CODE:
//                    reqSearch.setCode(keySearch);
//                    break;
//                case TEXT:
//                    reqSearch.setText(keySearch);
//                    break;
//                default:
//                    reqSearch.setCode(keySearch);
//                    reqSearch.setText(keySearch);
//                    break;
//                }
//            }
//        }else {
//            reqSearch.setCode(keySearch);
//            reqSearch.setText(keySearch);
//        }
//        
//        return reqSearch;
//    }
//
//    @Override
//    public List<EnumsParamSearchRes> getListEnumSearch() {
//        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaGroupConstantSearchEnum.values()); 
//    }
}
