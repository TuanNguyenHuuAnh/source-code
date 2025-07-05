package vn.com.unit.process.workflow.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.process.workflow.dto.AppProcessDmnDto;
import vn.com.unit.process.workflow.service.AppProcessDmnService;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.dto.FileUploadParamDto;
import vn.com.unit.storage.dto.FileUploadResultDto;
import vn.com.unit.storage.service.FileStorageService;
import vn.com.unit.workflow.core.WorkflowRepositoryService;
import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;
import vn.com.unit.workflow.dto.JpmProcessDmnDto;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.service.JpmProcessDmnDeployService;
import vn.com.unit.workflow.service.JpmProcessDmnService;
import vn.com.unit.workflow.service.JpmProcessService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AppProcessDmnServiceImpl implements AppProcessDmnService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private JpmProcessDmnService processDmnService;

    @Autowired
    private JpmProcessService jpmProcessService;

    @Autowired
    private SystemConfig systemConfig;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private WorkflowRepositoryService workflowRepositoryService;
    
    @Autowired
    private JpmProcessDmnDeployService jpmProcessDmnDeployService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<AppProcessDmnDto> getAppProcessDmnDtosByProcessId(Long processId) {
        List<JpmProcessDmnDto> processDmnDtos = processDmnService.getJpmProcessDmnDtosByProcessId(processId);
        return processDmnDtos.stream().map(this::convertJpmProcessDmnDtoToAppProcessDmnDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean uploadDmnFiles(Long processId, List<MultipartFile> dmnFiles) throws IOException {
        boolean res = false;
        if (CommonCollectionUtil.isNotEmpty(dmnFiles) && Objects.nonNull(processId)) {
            JpmProcessDto processDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
            Date sysDate = CommonDateUtil.getSystemDateTime();
            String sysDateStr = CommonDateUtil.formatDateToString(sysDate, CommonDateUtil.YYYYMMDDHHMMSS);
            Long companyId = processDto.getCompanyId();

            String repoIdStr = systemConfig.getConfig(SystemConfig.REPO_UPLOADED_MAIN, processDto.getCompanyId());
            Long repoId = Long.parseLong(repoIdStr);
            for (MultipartFile dmnFile : dmnFiles) {
                byte[] contentFile = dmnFile.getBytes();
                String fileNameDmn = dmnFile.getOriginalFilename();

                // upload file
                FileUploadParamDto param = new FileUploadParamDto();
                param.setFileByteArray(contentFile);
                param.setFileName(fileNameDmn);
                param.setTypeRule(2);
                param.setSubFilePath("/dmn");// TODO hardcode
                param.setCompanyId(companyId);

                // rename file append sysDate
                String rename = CommonFileUtil.getBaseName(fileNameDmn).concat(CommonConstant.UNDERSCORE).concat(sysDateStr);
                param.setRename(rename);
                param.setRepositoryId(repoId);
                FileUploadResultDto uploadResultDto;
                try {
                    uploadResultDto = fileStorageService.upload(param);

                    String filePath = uploadResultDto.getFilePath();

                    JpmProcessDmnDto processDmnDto = new JpmProcessDmnDto();
                    processDmnDto.setDmnFileName(fileNameDmn);
                    processDmnDto.setDmnFilePath(filePath);
                    processDmnDto.setDmnRepoId(repoId);
                    processDmnDto.setProcessId(processId);

                    processDmnService.saveJpmProcessDmnDto(processDmnDto);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            res = true;
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return processDmnService.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployDmn(Long processId, Long processDeployId, String parentDeploymentId) throws Exception {
        List<JpmProcessDmnDto> processDmnDtos = processDmnService.getJpmProcessDmnDtosByProcessId(processId);

        if (CommonCollectionUtil.isNotEmpty(processDmnDtos)) {
            JpmProcessDto processDto = jpmProcessService.getJpmProcessDtoByProcessId(processId);
            Long companyId = processDto.getCompanyId();
            String companyCode = companyService.getSystemCodeByCompanyId(companyId);
            String processCategory = processDto.getBusinessCode();
            for (JpmProcessDmnDto processDmnDto : processDmnDtos) {
                String filePathDmn = processDmnDto.getDmnFilePath();
                FileDownloadParam fileDownloadParam = new FileDownloadParam();
                fileDownloadParam.setFilePath(filePathDmn);
                fileDownloadParam.setRepositoryId(processDmnDto.getDmnRepoId());

                FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
                byte[] dmnContent = fileDownloadResult.getFileByteArray();
                String deploymentId = workflowRepositoryService.deployDmn(dmnContent, processCategory, companyCode, filePathDmn,
                        parentDeploymentId);
                
                JpmProcessDmnDeployDto processDmnDeployDto = objectMapper.convertValue(processDmnDto, JpmProcessDmnDeployDto.class);
                processDmnDeployDto.setDeploymentId(deploymentId);
                processDmnDeployDto.setProcessDeployId(processDeployId);
                processDmnDeployDto.setId(null);
                
                jpmProcessDmnDeployService.saveJpmProcessDmnDeployDto(processDmnDeployDto);
            }
        }
    }

    private AppProcessDmnDto convertJpmProcessDmnDtoToAppProcessDmnDto(JpmProcessDmnDto jpmProcessDmnDto) {
        AppProcessDmnDto res = objectMapper.convertValue(jpmProcessDmnDto, AppProcessDmnDto.class);
        return res;
    }
}
