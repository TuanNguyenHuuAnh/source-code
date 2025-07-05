/*******************************************************************************
 * Class        AppProcessDeployDto
 * Created date 2019/07/04
 * Lasted date  2019/07/04
 * Author       KhuongTH
 * Change log   2019/07/04 01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.workflow.dto.JpmProcessDeployLangDto;

/**
 * AppProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class AppProcessDeployDto {

    private Long id;

    @Size(min = 1, max = 255)
    private String code;

    @Size(min = 1, max = 500)
    private String name;

    private boolean isActive;

    private Long jpmProcessId;

    @NotNull
    private Long companyId;

    private String description;

    private Long majorVersion;

    private Long minorVersion;

    @NotNull
    private Long businessId;

    private Date effectiveDate;

    private Date expiredDate;

    private boolean isShowWorkflow;

    /** view */
    private Date deployedDate;
    private boolean isWorkflow;
    private boolean isSystem;
    private Integer processType;

    /** file bpmn */
    private MultipartFile fileProcess;
    private String filePathBpmn;
    private String fileNameBpmn;

    private Long jRepositoryId;

    private String processDefinitionid;

    private AppParamDeployDto[] listJpmParamDto;

    private AppStatusDeployDto[] listJpmStatusDto;

    private AppButtonDeployDto[] listJpmButtonDto;

    private AppFunctionDeployDto[] listJpmFunctionDto;

    private List<AppStepDeployDto> listJpmStepDto;

    private List<JpmProcessDeployLangDto> processLangs;

    private Long signType;
    private Long signPosition;

    /** Only view */
    private String companyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(Long majorVersion) {
        this.majorVersion = majorVersion;
    }

    public Long getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(Long minorVersion) {
        this.minorVersion = minorVersion;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getFilePathBpmn() {
        return filePathBpmn;
    }

    public void setFilePathBpmn(String filePathBpmn) {
        this.filePathBpmn = filePathBpmn;
    }

    public String getFileNameBpmn() {
        return fileNameBpmn;
    }

    public void setFileNameBpmn(String fileNameBpmn) {
        this.fileNameBpmn = fileNameBpmn;
    }

    public MultipartFile getFileProcess() {
        return fileProcess;
    }

    public void setFileProcess(MultipartFile fileProcess) {
        this.fileProcess = fileProcess;
    }

    public AppParamDeployDto[] getListJpmParamDto() {
        return listJpmParamDto;
    }

    public void setListJpmParamDto(AppParamDeployDto[] listJpmParamDto) {
        this.listJpmParamDto = listJpmParamDto;
    }

    public AppStatusDeployDto[] getListJpmStatusDto() {
        return listJpmStatusDto;
    }

    public void setListJpmStatusDto(AppStatusDeployDto[] listJpmStatusDto) {
        this.listJpmStatusDto = listJpmStatusDto;
    }

    public AppButtonDeployDto[] getListJpmButtonDto() {
        return listJpmButtonDto;
    }

    public void setListJpmButtonDto(AppButtonDeployDto[] listJpmButtonDto) {
        this.listJpmButtonDto = listJpmButtonDto;
    }

    public AppFunctionDeployDto[] getListJpmFunctionDto() {
        return listJpmFunctionDto;
    }

    public void setListJpmFunctionDto(AppFunctionDeployDto[] listJpmFunctionDto) {
        this.listJpmFunctionDto = listJpmFunctionDto;
    }

    public Long getjRepositoryId() {
        return jRepositoryId;
    }

    public void setjRepositoryId(Long jRepositoryId) {
        this.jRepositoryId = jRepositoryId;
    }

    public String getProcessDefinitionid() {
        return processDefinitionid;
    }

    public void setProcessDefinitionid(String processDefinitionid) {
        this.processDefinitionid = processDefinitionid;
    }

    public Long getJpmProcessId() {
        return jpmProcessId;
    }

    public void setJpmProcessId(Long jpmProcessId) {
        this.jpmProcessId = jpmProcessId;
    }

    public List<AppStepDeployDto> getListJpmStepDto() {
        return listJpmStepDto;
    }

    public void setListJpmStepDto(List<AppStepDeployDto> listJpmStepDto) {
        this.listJpmStepDto = listJpmStepDto;
    }

    public Date getDeployedDate() {
        return deployedDate;
    }

    public void setDeployedDate(Date deployedDate) {
        this.deployedDate = deployedDate;
    }

    public boolean getIsWorkflow() {
        return isWorkflow;
    }

    public void setIsWorkflow(boolean isWorkflow) {
        this.isWorkflow = isWorkflow;
    }

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public boolean getIsShowWorkflow() {
        return isShowWorkflow;
    }

    public void setIsShowWorkflow(boolean isShowWorkflow) {
        this.isShowWorkflow = isShowWorkflow;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public List<JpmProcessDeployLangDto> getProcessLangs() {
        return processLangs;
    }

    public void setProcessLangs(List<JpmProcessDeployLangDto> processLangs) {
        this.processLangs = processLangs;
    }

    public Long getSignType() {
        return signType;
    }

    public void setSignType(Long signType) {
        this.signType = signType;
    }

    public Long getSignPosition() {
        return signPosition;
    }

    public void setSignPosition(Long signPosition) {
        this.signPosition = signPosition;
    }

}
