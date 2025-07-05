package vn.com.unit.process.workflow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.ep2p.admin.enumdef.ConstantDisplayType;
import vn.com.unit.process.workflow.dto.AppButtonForStepDeployDto;
import vn.com.unit.process.workflow.dto.AppStepDeployDto;
import vn.com.unit.process.workflow.repository.AppStepDeployRepository;
import vn.com.unit.process.workflow.service.AppStepDeployService;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.entity.JpmStepDeploy;
import vn.com.unit.workflow.enumdef.StepKind;
import vn.com.unit.workflow.service.JpmStepDeployService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppStepDeployServiceImpl implements AppStepDeployService {

    @Autowired
    private AppStepDeployRepository appStepDeployRepository;

    @Autowired
    private JpmStepDeployService jpmStepDeployService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public AppStepDeployDto getById(Long id) {
        JpmStepDeployDto stepDeployDto = jpmStepDeployService.getJpmStepDeployDtoById(id);
        return this.convertJpmStepDtoToAppStepDto(stepDeployDto);
    }

    @Override
    public List<AppStepDeployDto> getJpmStepDeployDtoDetailByProcessId(Long processId, String lang) {
        List<AppStepDeployDto> listJpmStepDeployDto = appStepDeployRepository.findJpmStepDtoDetailByProcessId(processId, lang,
                ConstantDisplayType.J_PRP_STATUS_001.toString());
        // calculate countButton
        String stepCode = null;
        List<AppStepDeployDto> listStepAndButtonByCode = new ArrayList<>();
        Map<String, List<AppStepDeployDto>> mapStep = new HashMap<>();
        for (AppStepDeployDto item : listJpmStepDeployDto) {
            String iCode = item.getCode();
            if (!iCode.equals(stepCode) && stepCode != null) {
                mapStep.put(stepCode, listStepAndButtonByCode);
                listStepAndButtonByCode = new ArrayList<>();
            } else {
                if (stepCode != null) {
                    item.setHidden(true); // set hidden
                }
            }

            listStepAndButtonByCode.add(item);
            stepCode = iCode;
        }
        mapStep.put(stepCode, listStepAndButtonByCode); // add final step

        for (AppStepDeployDto item : listJpmStepDeployDto) {
            String iCode = item.getCode();
            List<AppStepDeployDto> listButton = mapStep.get(iCode);
            int countButton = listButton.size();

            item.setCountButton(countButton);
        }
        return listJpmStepDeployDto;
    }

    @Override
    public List<JpmStepDeploy> getJpmStepDeployByProcessId(Long processId) {
        return appStepDeployRepository.findJpmStepByProcessId(processId);
    }

    @Override
    public JpmStepDeploy getMinJpmStepDeployByProcessId(Long processDeployId) {
        return appStepDeployRepository.findMinJpmStepDeployByProcessId(processDeployId);
    }

    @Override
    public JpmStepDeploy findJpmStepDeployByProcessIdAndStepCode(Long processDeployId, String stepcode) throws Exception {
        return appStepDeployRepository.findJpmStepDeployByProcessIdAndStepCode(processDeployId, stepcode);
    }

    @Override
    public List<AppStepDeployDto> getJpmStepDeployDtoByProcessId(Long processId) throws Exception {
        List<AppStepDeployDto> listJpmStepDeployDto = appStepDeployRepository.findJpmStepDtoByProcessId(processId);
        // calculate countButton;
        String stepCode = null;
        List<AppStepDeployDto> listStepAndButtonByCode = new ArrayList<>();
        Map<String, List<AppStepDeployDto>> mapStep = new HashMap<String, List<AppStepDeployDto>>();
        for (AppStepDeployDto item : listJpmStepDeployDto) {
            String iCode = item.getCode();
            if (!iCode.equals(stepCode) && stepCode != null) {
                mapStep.put(stepCode, listStepAndButtonByCode);
                listStepAndButtonByCode = new ArrayList<>();
            } else {
                if (stepCode != null) {
                    item.setHidden(true); // set hidden;
                }
            }

            listStepAndButtonByCode.add(item);
            stepCode = iCode;
        }
        mapStep.put(stepCode, listStepAndButtonByCode); // add final step

        for (AppStepDeployDto item : listJpmStepDeployDto) {
            String iCode = item.getCode();
            List<AppStepDeployDto> listButton = mapStep.get(iCode);
            int countButton = listButton.size();

            item.setCountButton(countButton);
        }
        return listJpmStepDeployDto;
    }

    @Override
    public JpmStepDeploy getJpmStepDeployByProcessIdAndStatusCode(Long processDeployId, String statusCode) throws Exception {
        return appStepDeployRepository.findJpmStepDeployByProcessIdAndStatusCode(processDeployId, statusCode);
    }


    @Override
    public boolean isSurvey(Long processDeployId, String stepCode) throws NullPointerException, SQLException {
        String stepKind = appStepDeployRepository.findStepKindByIdAndCode(processDeployId, stepCode);
        if (StringUtils.isNoneBlank(stepKind) && StepKind.PARALLEL_SURVEY.toString().equals(stepKind)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<String> getStatusByStatusCommonAndProcess(String statusCommon, Long processDeployId) {

        return appStepDeployRepository.findStatusByProcessAndStatusCommon(processDeployId, statusCommon);
    }

    @Override
    public List<Select2Dto> findStepsByProcessId(Long newProcessDeployId) {
        return appStepDeployRepository.findStepsByProcessId(newProcessDeployId);
    }

    @Override
    public String findCodeById(Long Id) throws SQLException {
        return appStepDeployRepository.findCodeById(Id);
    }

    @Override
    public List<String> getStepCodeByStatusCommonAndProcess(String statusCommon, Long processDeployId) {
        return appStepDeployRepository.findStepCodeByProcessAndStatusCommon(processDeployId, statusCommon);
    }

    private AppStepDeployDto convertJpmStepDtoToAppStepDto(JpmStepDeployDto stepDto) {
        if (null == stepDto)
            return null;
        AppStepDeployDto appStepDto = objectMapper.convertValue(stepDto, AppStepDeployDto.class);
        appStepDto.setId(stepDto.getStepDeployId());
        appStepDto.setListJpmStepLangDeploy(stepDto.getStepLangs());
        appStepDto.setListJpmButton(this.convertJpmButtonForStepDtosToAppButtonForStepDtos(stepDto.getButtonForStepDtos()));
        appStepDto.setName(stepDto.getStepName());
        appStepDto.setCode(stepDto.getStepCode());
        appStepDto.setStatusId(stepDto.getStatusDeployId());
        return appStepDto;
    }

    private List<AppButtonForStepDeployDto> convertJpmButtonForStepDtosToAppButtonForStepDtos(List<JpmButtonForStepDeployDto> buttonForStepDtos) {
        if (CommonCollectionUtil.isEmpty(buttonForStepDtos))
            return null;
        return buttonForStepDtos.stream().map(a -> {
            AppButtonForStepDeployDto appButtonForStepDto = objectMapper.convertValue(a, AppButtonForStepDeployDto.class);
            appButtonForStepDto.setIsAuthenticate(a.isOptionAuthenticate());
            appButtonForStepDto.setIsExportPdf(a.isOptionExportPdf());
            appButtonForStepDto.setIsSave(a.isOptionSaveForm());
            appButtonForStepDto.setIsSaveEform(a.isOptionSaveEform());
            appButtonForStepDto.setIsSign(a.isOptionSigned());
            appButtonForStepDto.setDisplayHistoryApprove(a.isOptionShowHistory());
            appButtonForStepDto.setFunctionId(a.getPermissionDeployId());
            appButtonForStepDto.setFieldSign(a.isOptionFillToEform());
            appButtonForStepDto.setButtonId(a.getButtonDeployId());
            return appButtonForStepDto;
        }).collect(Collectors.toList());
    }
}
