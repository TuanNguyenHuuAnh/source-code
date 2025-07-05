/*******************************************************************************
 * Class        PositionServiceImpl
 * Created date 2020/12/01
 * Lasted date  2020/12/01
 * Author       MinhNV
 * Change log   2020/12/01 01-00 MinhNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.tree.TreeBuilder;
import vn.com.unit.common.tree.TreeObject;
import vn.com.unit.core.dto.JcaPositionDto;
import vn.com.unit.core.dto.JcaPositionSearchDto;
import vn.com.unit.core.entity.JcaPosition;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.core.service.JcaPositionService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.dts.utils.DtsStringUtil;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.PositionAddReq;
import vn.com.unit.ep2p.dto.req.PositionUpdateReq;
import vn.com.unit.ep2p.dto.res.PositionInfoRes;
import vn.com.unit.ep2p.service.PositionPathService;
import vn.com.unit.ep2p.service.PositionService;

/**
 * PositionServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author MinhNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiPositionServiceImpl extends AbstractCommonService implements PositionService {

    @Autowired
    private JcaPositionService jcaPositionService;
    
    @Autowired
    private PositionPathService positionPathService;

    private PositionInfoRes mapperJcaItemDtoToItemInfoRes(JcaPositionDto jcaPositionDto){
        return objectMapper.convertValue(jcaPositionDto, PositionInfoRes.class);
    }

    @Override
    public ObjectDataRes<JcaPositionDto> search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException {
        ObjectDataRes<JcaPositionDto> resObj = null;
        try {
            /** init pageable */
            Pageable pageableAfterBuild = this.buildPageable(pageable, JcaPosition.class,JcaPositionService.TABLE_ALIAS_JCA_POSITION);
            /** init param search repository */
            JcaPositionSearchDto reqSearch = this.buildPositionSearchSearchDto(commonSearch);
            
            int totalData = this.countJcaPositionDtoByCondition(reqSearch);
            List<JcaPositionDto> datas = new ArrayList<>();
            if (totalData > 0) {
                datas = this.getJcaPositionDtoByCondition(reqSearch,pageableAfterBuild);
            }
            resObj = new ObjectDataRes<>(totalData, datas);
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402901_APPAPI_POSITION_LIST_ERROR);
        }
        return resObj;
    }
    
    private JcaPositionSearchDto buildPositionSearchSearchDto(MultiValueMap<String, String> commonSearch) {
        JcaPositionSearchDto reqSearch = new JcaPositionSearchDto();
        
        Long companyId = null != commonSearch.getFirst("companyId") ? Long.valueOf(commonSearch.getFirst("companyId")) : null;
        Boolean actived = null != commonSearch.getFirst("active") ? Boolean.valueOf(commonSearch.getFirst("active")) : null;
        
        reqSearch.setActived(actived);
        reqSearch.setCompanyId(companyId);
        return  reqSearch;
    }
    
    private void setPositionParentId(Long positionParentId, JcaPositionDto jcaPositionDto) throws DetailException {
        if(positionParentId  == CommonConstant.NUMBER_ZERO_L ){
            jcaPositionDto.setPositionParentId(positionParentId);
        }else {
            JcaPositionDto jcaPositionDtoParent = jcaPositionService.getJcaPositionDtoById(positionParentId);
            if( null != jcaPositionDtoParent) {
               jcaPositionDto.setPositionParentId(positionParentId);
            }else {
               throw new DetailException(AppApiExceptionCodeConstant.E402907_APPAPI_POSITION_PARENT_NOT_FOUND);
            } 
        }
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PositionInfoRes create(PositionAddReq reqPositionAddDto) throws DetailException{
        JcaPositionDto jcaPositionDto = new JcaPositionDto();
        try {
            if (null != reqPositionAddDto.getCode()) {
                jcaPositionDto.setCode(reqPositionAddDto.getCode());
            }
            if (null != reqPositionAddDto.getName()) {
                jcaPositionDto.setName(reqPositionAddDto.getName());
            }
            if (null != reqPositionAddDto.getNameAbv()) {
                jcaPositionDto.setNameAbv(reqPositionAddDto.getNameAbv());
            }
            if (null != reqPositionAddDto.getDescription()) {
                jcaPositionDto.setDescription(reqPositionAddDto.getDescription());
            }
            if (null != reqPositionAddDto.getCompanyId()) {
                jcaPositionDto.setCompanyId(reqPositionAddDto.getCompanyId());
            }
            if (null != reqPositionAddDto.getPositionParentId() && reqPositionAddDto.getPositionParentId() != CommonConstant.NUMBER_ZERO_L) {
                setPositionParentId(reqPositionAddDto.getPositionParentId(), jcaPositionDto);
            }
            if(reqPositionAddDto.getPositionParentId() == CommonConstant.NUMBER_ZERO_L) {
                jcaPositionDto.setPositionParentId(reqPositionAddDto.getPositionParentId());
            }
            if (null != reqPositionAddDto.getPositionOrder()) {
                jcaPositionDto.setPositionOrder(reqPositionAddDto.getPositionOrder());
            }
            
            jcaPositionDto.setActived(reqPositionAddDto.getActived());
            this.save(jcaPositionDto);
            
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402902_APPAPI_POSITION_ADD_ERROR);
        }
        return this.mapperJcaItemDtoToItemInfoRes(jcaPositionDto);
    }
    

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PositionUpdateReq reqPositionUpdateDto) throws DetailException{
        JcaPositionDto jcaPositionDto = jcaPositionService.getJcaPositionDtoById(reqPositionUpdateDto.getPositionId());
        if (null != jcaPositionDto) {
            updatePosition(reqPositionUpdateDto, jcaPositionDto);
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E402906_APPAPI_POSITION_NOT_FOUND);
        }
    }
    
    private void updatePosition(PositionUpdateReq reqPositionUpdateDto, JcaPositionDto jcaPositionDto) throws DetailException {
        try {
            if (null != reqPositionUpdateDto.getPositionId()) {
                jcaPositionDto.setPositionId(reqPositionUpdateDto.getPositionId());
            }
            if (DtsStringUtil.isNotBlank(reqPositionUpdateDto.getName())) {
                jcaPositionDto.setName(reqPositionUpdateDto.getName());
            }
            if (DtsStringUtil.isNotBlank(reqPositionUpdateDto.getNameAbv())) {
                jcaPositionDto.setNameAbv(reqPositionUpdateDto.getNameAbv());
            }
            if (DtsStringUtil.isNotBlank(reqPositionUpdateDto.getDescription())) {
                jcaPositionDto.setDescription(reqPositionUpdateDto.getDescription());
            }
            
            if (null != reqPositionUpdateDto.getPositionParentId()) {
                setPositionParentId(reqPositionUpdateDto.getPositionParentId(), jcaPositionDto);
            }
            if(reqPositionUpdateDto.getPositionParentId() == CommonConstant.NUMBER_ZERO_L) {
                jcaPositionDto.setPositionParentId(reqPositionUpdateDto.getPositionParentId());
            }
            if (null != reqPositionUpdateDto.getPositionOrder()) {
                jcaPositionDto.setPositionOrder(reqPositionUpdateDto.getPositionOrder());
            }
            jcaPositionDto.setActived(reqPositionUpdateDto.getActived());
            this.save(jcaPositionDto);
         
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402903_APPAPI_POSITION_UPDATE_INFO_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws DetailException{
        JcaPositionDto jcaPositionDto = jcaPositionService.getJcaPositionDtoById(id);
        if (null != jcaPositionDto) {
            try {
                jcaPositionService.deleteJcaPositionById(id);
                // delete position path
                Long descendantId = id;
                positionPathService.deletePositionPathByDescendantId(descendantId);
                
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402904_APPAPI_POSITION_DELETE_ERROR);
            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E402906_APPAPI_POSITION_NOT_FOUND);
        }
    }

    @Override
    public PositionInfoRes getPositionInfoById(Long id) throws DetailException{
        JcaPositionDto position = this.detail(id);
        return objectMapper.convertValue(position, PositionInfoRes.class);
    }

    @Override
    public JcaPositionDto save(JcaPositionDto objectDto) throws DetailException {
        JcaPosition jcaPostion = jcaPositionService.saveJcaPositionDto(objectDto);
        objectDto.setPositionId(jcaPostion.getId());
        return objectDto;
    }

    @Override
    public JcaPositionDto detail(Long id) throws DetailException{        
        JcaPositionDto jcaPositionDto = jcaPositionService.getJcaPositionDtoById(id);
        if (null != jcaPositionDto) {
            try {
                return jcaPositionDto;
            } catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E402905_APPAPI_POSITION_INFO_ERROR);

            }
        } else {
            throw new DetailException(AppApiExceptionCodeConstant.E402906_APPAPI_POSITION_NOT_FOUND);
        }
        return jcaPositionDto;
    }

    @Override
    public int countJcaPositionDtoByCondition(JcaPositionSearchDto jcaPositionSearchDto) {
        return jcaPositionService.countJcaPositionDtoByCondition(jcaPositionSearchDto);
    }

    @Override
    public List<JcaPositionDto> getJcaPositionDtoByCondition(JcaPositionSearchDto jcaPositionSearchDto, Pageable pagable) {
        return jcaPositionService.getJcaPositionDtoByCondition(jcaPositionSearchDto, pagable);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<TreeObject<JcaPositionDto>> getListJcaPositionDto() throws DetailException {
        List<JcaPositionDto> datas = new ArrayList<>();
        List<TreeObject<JcaPositionDto>> listTreeObject = new ArrayList<>();
        try {
            datas = jcaPositionService.getJcaPositionDtoToBuildTree();
            TreeBuilder<JcaPositionDto> builder = new TreeBuilder(datas);
            builder.sortWithTree();
            listTreeObject = builder.buildTree();
        } catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E402901_APPAPI_POSITION_LIST_ERROR);
        }
        return listTreeObject;
    }
   
}
