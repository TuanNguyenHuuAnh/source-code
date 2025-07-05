package vn.com.unit.process.workflow.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.ep2p.admin.dto.JcaAuthorityListDto;
import vn.com.unit.ep2p.core.efo.dto.EfoFormAuthorityDto;
import vn.com.unit.ep2p.core.efo.service.EfoFormAuthorityService;
import vn.com.unit.process.workflow.service.AppProccessAuthorityService;
import vn.com.unit.workflow.dto.JpmAuthorityDto;
import vn.com.unit.workflow.service.JpmAuthorityService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppProccessAuthorityServiceImpl implements AppProccessAuthorityService {

    @Autowired
    private JpmAuthorityService jpmAuthorityService;

    @Autowired
    private EfoFormAuthorityService efoFormAuthorityService;

    private static final String AUTHORITY_TYPE_FORM = "1";
    private static final String AUTHORITY_TYPE_PROCESS = "2";

    @Override
    public List<JcaAuthorityDto> getAuthorityDtosByProcessDeployIdAndRoleId(Long processDeployId, Long roleId) {
        List<JpmAuthorityDto> authorityDtos = jpmAuthorityService.getJpmAuthorityDtosByProcessDeployIdAndRoleId(processDeployId, roleId);
        return authorityDtos.stream().map(this::convertJpmAuthorityDtoToJcaAuthorityDto).collect(Collectors.toList());
    }

    @Override
    public List<JcaAuthorityDto> getServiceAuthorityByBusinessIdAndRoleId(Long businessId, Long roleId) {
        List<EfoFormAuthorityDto> authorityDtos = efoFormAuthorityService.getEfoFormAuthorityDtosByBusinessIdAndRoleId(businessId, roleId);
        return authorityDtos.stream().map(this::convertEfoFormAuthorityDtoToJcaAuthorityDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAuthorityProcess(JcaAuthorityListDto jcaAuthorityListDto) {
        List<JcaAuthorityDto> jcaAuthorityDtos = jcaAuthorityListDto.getData();
        if (CommonCollectionUtil.isNotEmpty(jcaAuthorityDtos)) {
            List<JpmAuthorityDto> authorityProcessDtos = jcaAuthorityDtos.stream()
                    .filter(a -> AUTHORITY_TYPE_PROCESS.equals(a.getAuthorityType())).map(this::convertJcaAuthorityDtoToJpmAuthorityDto)
                    .collect(Collectors.toList());
            Long processDeployId = jcaAuthorityListDto.getProcessId();
            Long roleId = jcaAuthorityListDto.getRoleId();
            jpmAuthorityService.saveJpmAuthorityDtosByProcessDeployIdAndRoleId(authorityProcessDtos, processDeployId, roleId);

            Long businessId = jcaAuthorityListDto.getBusinessId();
            List<EfoFormAuthorityDto> formAuthorityDtos = jcaAuthorityDtos.stream()
                    .filter(a -> AUTHORITY_TYPE_FORM.equals(a.getAuthorityType())).map(this::convertJcaAuthorityDtoToEfoFormAuthorityDto)
                    .collect(Collectors.toList());
            efoFormAuthorityService.saveEfoFormAuthorityDtosByRoleId(formAuthorityDtos, businessId, roleId);
        }
    }

    private JcaAuthorityDto convertJpmAuthorityDtoToJcaAuthorityDto(JpmAuthorityDto jpmAuthorityDto) {
        if (Objects.isNull(jpmAuthorityDto))
            return null;
        else {
            JcaAuthorityDto res = new JcaAuthorityDto();
            res.setCanAccessFlag(jpmAuthorityDto.isAccessFlag());
            res.setItemId(jpmAuthorityDto.getPermissionDeployId());
            res.setFunctionName(jpmAuthorityDto.getPermissionName());
            res.setAuthorityType(AUTHORITY_TYPE_PROCESS);
            return res;
        }
    }

    private JpmAuthorityDto convertJcaAuthorityDtoToJpmAuthorityDto(JcaAuthorityDto jcaAuthorityDto) {
        if (Objects.isNull(jcaAuthorityDto))
            return null;
        else {
            JpmAuthorityDto res = new JpmAuthorityDto();
            res.setAccessFlag(jcaAuthorityDto.getCanAccessFlag());
            res.setPermissionDeployId(jcaAuthorityDto.getItemId());
            return res;
        }
    }

    private JcaAuthorityDto convertEfoFormAuthorityDtoToJcaAuthorityDto(EfoFormAuthorityDto efoFormAuthorityDto) {
        if (Objects.isNull(efoFormAuthorityDto))
            return null;
        else {
            JcaAuthorityDto res = new JcaAuthorityDto();
            res.setCanAccessFlag(efoFormAuthorityDto.isAccessFlag());
            res.setItemId(efoFormAuthorityDto.getFormId());
            res.setFunctionName(efoFormAuthorityDto.getFormName());
            res.setAuthorityType(AUTHORITY_TYPE_FORM);
            return res;
        }
    }

    private EfoFormAuthorityDto convertJcaAuthorityDtoToEfoFormAuthorityDto(JcaAuthorityDto jcaAuthorityDto) {
        if (Objects.isNull(jcaAuthorityDto))
            return null;
        else {
            EfoFormAuthorityDto res = new EfoFormAuthorityDto();
            res.setAccessFlag(jcaAuthorityDto.getCanAccessFlag());
            res.setFormId(jcaAuthorityDto.getItemId());
            return res;
        }
    }
}
