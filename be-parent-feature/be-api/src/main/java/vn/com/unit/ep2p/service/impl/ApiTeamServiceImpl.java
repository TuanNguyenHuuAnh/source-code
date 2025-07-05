/*******************************************************************************
 * Class        TeamServiceImpl
 * Created date 2020/12/08
 * Lasted date  2020/12/08
 * Author       MinhNV
 * Change log   2020/12/08 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.utils.CommonCollectionUtil;
//import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaCompanyDto;
import vn.com.unit.core.dto.JcaTeamDto;
import vn.com.unit.core.dto.JcaTeamSearchDto;
import vn.com.unit.core.entity.JcaTeam;
import vn.com.unit.core.enumdef.param.JcaTeamSearchEnum;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaCompanyService;
import vn.com.unit.core.service.JcaTeamService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
//import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.res.dto.EnumsParamSearchRes;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.core.service.MasterCommonService;
import vn.com.unit.ep2p.dto.req.TeamAddReq;
import vn.com.unit.ep2p.dto.req.TeamUpdateReq;
import vn.com.unit.ep2p.dto.res.TeamInfoRes;
import vn.com.unit.ep2p.service.TeamService;

/**
 * TeamServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiTeamServiceImpl extends AbstractCommonService implements TeamService {

    @Autowired
    @Qualifier("jcaTeamServiceImpl")
    private JcaTeamService jcaTeamService;
    
    @Autowired
    private JcaCompanyService jcaCompanyService;
    
    @Autowired
    private MasterCommonService masterCommonService;

    private TeamInfoRes mapperJcaItemDtoToItemInfoRes(JcaTeamDto jcaTeamDto){
        return objectMapper.convertValue(jcaTeamDto, TeamInfoRes.class);
    }
    
    @Override
    public ObjectDataRes<JcaTeamDto>     search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
        ObjectDataRes<JcaTeamDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaTeam.class,JcaTeamService.TABLE_ALIAS_JCA_TEAM);
            /** init param search repository */
            JcaTeamSearchDto reqSearch = this.buildJcaTeamDtoSearchDto(commonSearch);
            
            int totalData = this.countJcaTeamDtoByCondition(reqSearch);
            List<JcaTeamDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getJcaTeamDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021501_APPAPI_TEAM_LIST_ERROR);
        }
        return resObj;
    }
    
    private JcaTeamSearchDto buildJcaTeamDtoSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaTeamSearchDto reqSearch = new JcaTeamSearchDto();
        
        String keySearch = null != commonSearch.getFirst("keySearch") ? commonSearch.getFirst("keySearch") : AppApiConstant.EMPTY;
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean actived = null != commonSearch.getFirst("actived") ? Boolean.valueOf(commonSearch.getFirst("actived")) : null;
        List<String> enumsValues = CommonStringUtil.isNotBlank(commonSearch.getFirst("multipleSeachEnums")) ? commonSearch.get("multipleSeachEnums") : null;

        
        reqSearch.setActived(actived);
        reqSearch.setCompanyId(companyId);
        
        if(CommonCollectionUtil.isNotEmpty(enumsValues)) {
            for (String enumValue : enumsValues) {
                switch (JcaTeamSearchEnum.valueOf(enumValue)) {
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
        
        return  reqSearch;
    }
    
    private void setCode(String code, JcaTeamDto jcaTeamDto) throws DetailException{
        int count = jcaTeamService.countJcaTeamDtoByCode(code);
        if(count > 0) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021507_APPAPI_TEAM_CODE_DUPLICATED, true);
        }else {
            jcaTeamDto.setCode(code);
        }
    }
    
    private void setName(String name, JcaTeamDto jcaTeamDto, Long teamId) throws DetailException{
        int count = jcaTeamService.countJcaTeamDtoByName(name, teamId);
        if(count > 0) {
            throw new DetailException(AppApiExceptionCodeConstant.E4021508_APPAPI_TEAM_NAME_DUPLICATED, true);
        }else {
            jcaTeamDto.setName(name);
        }
    }
    
    private void setCompanyId(Long companyId, JcaTeamDto jcaTeamDto) throws DetailException{
        JcaCompanyDto jcaCompanyDto =  jcaCompanyService.getJcaCompanyDtoById(companyId);
        if(null != jcaCompanyDto) {
            jcaTeamDto.setCompanyId(companyId);
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021203_APPAPI_COMPANY_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TeamInfoRes create(TeamAddReq reqTeamAddDto) throws Exception {
        JcaTeamDto jcaTeamDto = new JcaTeamDto();
        try {
            if (CommonStringUtil.isNotBlank(reqTeamAddDto.getCode())) {
                setCode(reqTeamAddDto.getCode(), jcaTeamDto);
            }
            if (CommonStringUtil.isNotBlank(reqTeamAddDto.getName())) {
                setName(reqTeamAddDto.getName(), jcaTeamDto, null);
            }
            if (CommonStringUtil.isNotBlank(reqTeamAddDto.getNameAbv())) {
                jcaTeamDto.setNameAbv(reqTeamAddDto.getNameAbv());
            }
            if (CommonStringUtil.isNotBlank(reqTeamAddDto.getDescription())) {
                jcaTeamDto.setDescription(reqTeamAddDto.getDescription());
            }
            
            
        
            if (null != reqTeamAddDto.getCompanyId()) {
                setCompanyId(reqTeamAddDto.getCompanyId(), jcaTeamDto);
            }
            if (null != reqTeamAddDto.getActived()) {
                jcaTeamDto.setActived(reqTeamAddDto.getActived());
            }else {
                jcaTeamDto.setActived(false);
            }
            
          
            this.save(jcaTeamDto);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021502_APPAPI_TEAM_ADD_ERROR);
        }
        return this.mapperJcaItemDtoToItemInfoRes(jcaTeamDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TeamUpdateReq reqTeamUpdateDto) throws DetailException {
        JcaTeamDto jcaTeamDto = jcaTeamService.getJcaTeamDtoById(reqTeamUpdateDto.getTeamId());
        if(null != jcaTeamDto) {
            try {
                this.convertUpdateDataToJcaTeamDto(reqTeamUpdateDto, jcaTeamDto);
                this.save(jcaTeamDto);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021503_APPAPI_TEAM_UPDATE_INFO_ERROR);
            } 
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021506_APPAPI_TEAM_NOT_FOUND);
        }
        
    }
    private void convertUpdateDataToJcaTeamDto(TeamUpdateReq reqTeamUpdateDto, JcaTeamDto jcaTeamDto) throws DetailException {
        
        if (DtsStringUtil.isNotBlank(reqTeamUpdateDto.getName())) {
            setName(reqTeamUpdateDto.getName(), jcaTeamDto, reqTeamUpdateDto.getTeamId());
        }
        if (DtsStringUtil.isNotBlank(reqTeamUpdateDto.getNameAbv())) {
            jcaTeamDto.setNameAbv(reqTeamUpdateDto.getNameAbv());
        }
        if (DtsStringUtil.isNotBlank(reqTeamUpdateDto.getDescription())) {
            jcaTeamDto.setDescription(reqTeamUpdateDto.getDescription());
        }
        if (null != reqTeamUpdateDto.getActived()) {
            jcaTeamDto.setActived(reqTeamUpdateDto.getActived());
        }else {
            jcaTeamDto.setActived(false);
        }
        if(null != reqTeamUpdateDto.getCompanyId()) {
            setCompanyId(reqTeamUpdateDto.getCompanyId(), jcaTeamDto);
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException {
        JcaTeamDto jcaTeamDto = jcaTeamService.getJcaTeamDtoById(id);
        if (null != jcaTeamDto) {
            try {
                jcaTeamService.deleteJcaTeamById(id);
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021504_APPAPI_TEAM_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021506_APPAPI_TEAM_NOT_FOUND);
        }
    }

    @Override
    public JcaTeamDto save(JcaTeamDto objectDto) throws DetailException{
        JcaTeam jcaTeam = jcaTeamService.saveJcaTeamDto(objectDto);
        objectDto.setTeamId(jcaTeam.getId());
        return objectDto;
    }

    @Override
    public JcaTeamDto detail(Long id) throws DetailException {
        JcaTeamDto jcaTeamDto = jcaTeamService.getJcaTeamDtoById(id);
        if (null != jcaTeamDto) {
            try {
                return jcaTeamDto;
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021505_APPAPI_TEAM_INFO_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021506_APPAPI_TEAM_NOT_FOUND);
        }
        return jcaTeamDto;
    }

    @Override
    public int countJcaTeamDtoByCondition(JcaTeamSearchDto jcaTeamSearchDto) {
        return jcaTeamService.countJcaTeamDtoByCondition(jcaTeamSearchDto);
    }

    @Override
    public List<JcaTeamDto> getJcaTeamDtoByCondition(JcaTeamSearchDto jcaTeamSearchDto, Pageable pageable) {
        return jcaTeamService.getJcaTeamDtoByCondition(jcaTeamSearchDto, pageable);
    }

    @Override
    public TeamInfoRes getTeamInfoById(Long id) throws Exception {
        JcaTeamDto jcaTeamDto = this.detail(id);
        return objectMapper.convertValue(jcaTeamDto, TeamInfoRes.class);
    }

    @Override
    public List<EnumsParamSearchRes> getListEnumSearch() {
        return  masterCommonService.getEnumsParamSearchResForEnumClass(JcaTeamSearchEnum.values());
    }

}
