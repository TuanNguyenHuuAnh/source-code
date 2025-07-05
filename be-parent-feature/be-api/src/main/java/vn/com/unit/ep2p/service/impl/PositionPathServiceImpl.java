
package vn.com.unit.ep2p.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaPositionPathDto;
import vn.com.unit.core.entity.JcaPositionPath;
import vn.com.unit.core.service.JcaPositionPathService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.PositionPathInfoReq;
import vn.com.unit.ep2p.dto.res.PositionPathInfoRes;
import vn.com.unit.ep2p.service.PositionPathService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PositionPathServiceImpl extends AbstractCommonService implements PositionPathService {

    @Autowired
    private JcaPositionPathService jcaPositionPathService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PositionPathInfoRes create(JcaPositionPathDto jcaPositionPathDto) throws DetailException {
        try {
            this.save(jcaPositionPathDto);
        }
        catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023301_APPAPI_POSITION_PATH_ADD_ERROR);
        }
        return this.getPositionPathInfoResById(jcaPositionPathDto.getId());
    }

    @Override
    public JcaPositionPathDto save(JcaPositionPathDto jcaPositionPathDto) {
        @SuppressWarnings("unused")
		JcaPositionPath jcaPositionPath = jcaPositionPathService.saveJcaPositionPathDto(jcaPositionPathDto);
        return jcaPositionPathDto;
    }

    @Override
    public JcaPositionPathDto getDetailDtoByDescendantId(Long descendantId) {
        return jcaPositionPathService.getJcaPositionPathDtoByDescendantId(descendantId);
    }

    @Override
    public JcaPositionPathDto getDetailDtoById(Long id) {
        return jcaPositionPathService.getJcaPositionPathDtoById(id);
    }

    @Override
    public PositionPathInfoRes getPositionPathInfoResById(Long id) {
        JcaPositionPathDto jcaPositionPathDto = this.getDetailDtoById(id);
        return objectMapper.convertValue(jcaPositionPathDto, PositionPathInfoRes.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(JcaPositionPathDto jcaPositionPathDto) throws DetailException {
        if( null != jcaPositionPathDto.getDescendantId() && null != jcaPositionPathDto.getAncestorId()) {
            JcaPositionPathDto jcaPositionPathDtoExist = jcaPositionPathService.getJcaPositionPathDtoByDescendantId(jcaPositionPathDto.getDescendantId());
            if( null != jcaPositionPathDtoExist) {
                try {
                    this.save(jcaPositionPathDto);
                }
                catch (Exception e) {
                    handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023302_APPAPI_POSITION_PATH_UPDATE_INFO_ERROR);
                }
            }else {
                throw new DetailException(AppApiExceptionCodeConstant.E4023303_APPAPI_POSITION_PATH_NOT_FOUND_ERROR);
            }
        }
    }

    @Override
    public JcaPositionPathDto getJcaPositionPathDtoByDescendantId(Long descendantId) {
        return this.getDetailDtoByDescendantId(descendantId);
    }

    @Override
    public void delete(PositionPathInfoReq positionPathInfoReq) throws DetailException {
        JcaPositionPathDto jcaPositionPathDto = jcaPositionPathService.getJcaPositionPathDtoByDescendantId(positionPathInfoReq.getDescendantId());
        if(null != jcaPositionPathDto) {
            try {
                jcaPositionPathDto.setDeletedId(1L);
                jcaPositionPathDto.setDeletedDate(commonService.getSystemDate());
                this.save(jcaPositionPathDto);
            }
            catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4023304_APPAPI_POSITION_PATH_DELETE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4023303_APPAPI_POSITION_PATH_NOT_FOUND_ERROR, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePositionPathByDescendantId(Long descendantId) {
        jcaPositionPathService.deletePositionPathByDescendantId(descendantId);
    }

}
