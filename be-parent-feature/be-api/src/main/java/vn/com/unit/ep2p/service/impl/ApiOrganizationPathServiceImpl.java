
package vn.com.unit.ep2p.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaOrganizationPathDto;
import vn.com.unit.core.entity.JcaOrganizationPath;
import vn.com.unit.core.service.JcaOrganizationPathService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.core.constant.AppApiExceptionCodeConstant;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.dto.req.OrganizationPathInfoReq;
import vn.com.unit.ep2p.dto.res.OrganizationPathInfoRes;
import vn.com.unit.ep2p.service.OrganizationPathService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiOrganizationPathServiceImpl extends AbstractCommonService implements OrganizationPathService {

    @Autowired
    private JcaOrganizationPathService jcaOrganizationPathService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaOrganizationPathDto create(OrganizationPathInfoReq organizationPathInfoReq) throws DetailException {
        JcaOrganizationPathDto jcaOrganizationPathDto = new JcaOrganizationPathDto();
        try {
            jcaOrganizationPathDto.setAncestorId(organizationPathInfoReq.getAncestorId());
            jcaOrganizationPathDto.setDescendantId(organizationPathInfoReq.getDescendantId());
            jcaOrganizationPathDto.setDepth(organizationPathInfoReq.getDepth());
            this.save(jcaOrganizationPathDto);
        }
        catch (Exception e) {
            handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021809_APPAPI_ORGANIZATION_PATH_SAVE_ERROR);
        }
        return jcaOrganizationPathDto;
    }

    @Override
    public JcaOrganizationPathDto save(JcaOrganizationPathDto jcaOrganizationPathDto) {
        JcaOrganizationPath jcaOrganizationPath = jcaOrganizationPathService.saveJcaOrganizationPathDto(jcaOrganizationPathDto);
        return objectMapper.convertValue(jcaOrganizationPath, JcaOrganizationPathDto.class) ;
    }

    @Override
    public JcaOrganizationPathDto getDetailDtoByDescendantId(Long descendantId) {
        return jcaOrganizationPathService.getJcaOrganizationPathDtoByDescendantId(descendantId);
    }

    @Override
    public JcaOrganizationPathDto getDetailDtoById(Long id) {
        return jcaOrganizationPathService.getJcaOrganizationPathDtoById(id);
    }

    @Override
    public OrganizationPathInfoRes getOrganizationPathInfoResById(Long id) {
        JcaOrganizationPathDto jcaOrganizationPathDto = this.getDetailDtoById(id);
        return objectMapper.convertValue(jcaOrganizationPathDto, OrganizationPathInfoRes.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(OrganizationPathInfoReq organizationPathInfoReq) throws DetailException {
        JcaOrganizationPathDto jcaOrganizationPathDto = jcaOrganizationPathService.getJcaOrganizationPathDtoByDescendantId(organizationPathInfoReq.getDescendantId());
        if(null != jcaOrganizationPathDto) {
            try {
                jcaOrganizationPathDto.setAncestorId(organizationPathInfoReq.getAncestorId());
                this.save(jcaOrganizationPathDto);
            }
            catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021809_APPAPI_ORGANIZATION_PATH_SAVE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021808_APPAPI_ORGANIZATION_PATH_NOT_FOUND, true);
        }
    }

    @Override
    public OrganizationPathInfoRes getOrganizationPathInfoResByDescendantId(Long descendantId) {
        JcaOrganizationPathDto jcaOrganizationPathDto = this.getDetailDtoByDescendantId(descendantId);
        return objectMapper.convertValue(jcaOrganizationPathDto, OrganizationPathInfoRes.class);
    }

    @Override
    public void delete(OrganizationPathInfoReq organizationPathInfoReq) throws DetailException {
        JcaOrganizationPathDto jcaOrganizationPathDto = jcaOrganizationPathService.getJcaOrganizationPathDtoByDescendantId(organizationPathInfoReq.getDescendantId());
        if(null != jcaOrganizationPathDto) {
            try {
//                jcaOrganizationPathDto.setDeletedId(1L);
//                jcaOrganizationPathDto.setDeletedDate(commonService.getSystemDate());
                this.save(jcaOrganizationPathDto);
            }
            catch (Exception e) {
                handlerCastException.castException(e, AppApiExceptionCodeConstant.E4021809_APPAPI_ORGANIZATION_PATH_SAVE_ERROR);
            }
        }else {
            throw new DetailException(AppApiExceptionCodeConstant.E4021808_APPAPI_ORGANIZATION_PATH_NOT_FOUND, true);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrganizationPathByDescendantId(Long descendantId) throws DetailException {
        jcaOrganizationPathService.deleteOrganizationPathByDescendantId(descendantId);
    }

}
