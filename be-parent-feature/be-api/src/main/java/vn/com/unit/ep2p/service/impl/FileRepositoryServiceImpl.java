/*******************************************************************************
 * Class        ：FileRepositoryServiceImpl
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.enumdef.param.JcaRepositorySearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.RepositoryAddReq;
import vn.com.unit.ep2p.dto.req.RepositoryUpdateReq;
import vn.com.unit.ep2p.dto.res.RepositoryInfoRes;
import vn.com.unit.ep2p.service.FileRepositoryService;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.JcaRepositorySearchDto;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * FileRepositoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FileRepositoryServiceImpl extends AbstractCommonService implements FileRepositoryService {

    @Autowired
    private JcaRepositoryService jcaRepositoryService;
    
    @Autowired
    private MasterCommonService masterCommonService;

    @Override
    public ObjectDataRes<JcaRepositoryDto> search(MultiValueMap<String, String> commonSearch, Pageable pageable) throws DetailException {

        ObjectDataRes<JcaRepositoryDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaRepository.class,
                    JcaRepositoryService.TABLE_ALIAS_JCA_REPOSITORY);
            /** init param search repository */
            JcaRepositorySearchDto reqSearch = this.buildJcaRepositorySearchDto(commonSearch);

            int totalData = this.countRepositoryDtoByCondition(reqSearch);
            List<JcaRepositoryDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getRepositoryDtoByCondition(reqSearch, pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022401_APPAPI_REPOSITORY_LIST_ERROR);
        }
        return resObj;

    }

    private JcaRepositorySearchDto buildJcaRepositorySearchDto(MultiValueMap<String, String> commonSearch) {

        JcaRepositorySearchDto reqSearch = new JcaRepositorySearchDto();
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : null;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;

        reqSearch.setCompanyId(companyId);
        reqSearch.setLangCode(UserProfileUtils.getLanguage());
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaRepositorySearchEnum.valueOf(enumValue)) {
                case CODE:
                    reqSearch.setCode(keySearch);
                    break;
                case NAME:
                    reqSearch.setName(keySearch);
                    break;
                    
                case PHYSICAL_PATH:
                    reqSearch.setPhysicalPath(keySearch);
                    break;
                case SUB_FOLDER_PATH:
                    reqSearch.setSubFolderPath(keySearch);
                    break;

                default:
                    reqSearch.setCode(keySearch);
                    reqSearch.setName(keySearch);
                    reqSearch.setPhysicalPath(keySearch);
                    reqSearch.setSubFolderPath(keySearch);
                    break;
                }
            }
        }else {
            reqSearch.setCode(keySearch);
            reqSearch.setName(keySearch);
            reqSearch.setPhysicalPath(keySearch);
            reqSearch.setSubFolderPath(keySearch);
        }
        
        return reqSearch;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRepositoryDto save(JcaRepositoryDto objectDto) throws DetailException {
        JcaRepository jacRepository = jcaRepositoryService.saveJcaRepositoryDto(objectDto);
        objectDto.setId(jacRepository.getId());
        return objectDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {

        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        JcaRepository jcaRepository = jcaRepositoryService.getJcaRepositoryById(id);
        if (null != jcaRepository) {
            try {
                jcaRepository.setDeletedId(userId);
                jcaRepository.setDeletedDate(sysDate);
                jcaRepositoryService.saveJcaRepository(jcaRepository);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022405_APPAPI_REPOSITORY_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022404_APPAPI_REPOSITORY_NOT_FOUND);
        }

    }

    @Override
    public JcaRepositoryDto detail(Long id) throws DetailException {
        JcaRepositoryDto jcaRepositoryDto = null;
        try {
            jcaRepositoryDto = jcaRepositoryService.getJcaRepositoryDtoById(id);
            if (null == jcaRepositoryDto) {
                throw new DetailException(AppApiExceptionCodeConstant.E4022404_APPAPI_REPOSITORY_NOT_FOUND);
            }
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022406_APPAPI_REPOSITORY_INFO_ERROR);
        }
        return jcaRepositoryDto;

    }

    @Override
    public int countRepositoryDtoByCondition(JcaRepositorySearchDto jcaRepositorySearchDto) {
        return jcaRepositoryService.countListJcaRepositoryByCondition(jcaRepositorySearchDto);
    }

    @Override
    public List<JcaRepositoryDto> getRepositoryDtoByCondition(JcaRepositorySearchDto jcaRepositorySearchDto, Pageable pageable) {
        return jcaRepositoryService.getListJcaRepositoryDtoByCondition(jcaRepositorySearchDto, pageable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(RepositoryUpdateReq reqRepositoryUpdateDto) throws Exception {

        Long id = reqRepositoryUpdateDto.getRepositoryId();

        JcaRepositoryDto jcaRepositoryDto = jcaRepositoryService.getJcaRepositoryDtoById(id);
        if (null != jcaRepositoryDto) {
            try {
                jcaRepositoryDto = objectMapper.convertValue(reqRepositoryUpdateDto, JcaRepositoryDto.class);
                this.save(jcaRepositoryDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022403_APPAPI_REPOSITORY_UPDATE_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4022404_APPAPI_REPOSITORY_NOT_FOUND);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RepositoryInfoRes create(RepositoryAddReq reqRepositoryAddDto) throws Exception {
        JcaRepositoryDto objectDto = new JcaRepositoryDto();

        try {
            objectDto = objectMapper.convertValue(reqRepositoryAddDto, JcaRepositoryDto.class);

            boolean checkCodeExists = jcaRepositoryService.checkGroupCodeExists(objectDto.getCode(), objectDto.getCompanyId());

            if (checkCodeExists) {
                throw new DetailException(AppApiExceptionCodeConstant.E101600_CODE_EXISTS_ERROR);
            }

            this.save(objectDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4022402_APPAPI_REPOSITORY_ADD_ERROR);
        }
        return objectMapper.convertValue(objectDto, RepositoryInfoRes.class);
    }

    @Override
    public RepositoryInfoRes getRepositoryInfoResById(Long id) throws Exception {
        JcaRepositoryDto objectDto = this.detail(id);
        return objectMapper.convertValue(objectDto, RepositoryInfoRes.class);
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return masterCommonService.getEnumsParamSearchResForEnumClass(JcaRepositorySearchEnum.values()); 
    }

}
