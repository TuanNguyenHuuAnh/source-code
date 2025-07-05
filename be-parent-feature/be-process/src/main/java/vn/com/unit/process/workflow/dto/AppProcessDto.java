/*******************************************************************************
 * Class        AppProcessDto
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.workflow.dto.JpmProcessLangDto;

/**
 * AppProcessDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class AppProcessDto {

    private Long id;

    @Size(min = 1, max = 255)
    private String code;

    @Size(min = 1, max = 500)
    private String name;

    private boolean isActive;

    private boolean isDeployed;

    @NotNull
    private Long companyId;

    private String description;

    private Long majorVersion;

    private Long minorVersion;

    @NotNull
    private Long businessId;

    private Date effectiveDate;

    private Date expiredDate;

    /** file bpmn */
    private MultipartFile fileProcess;
    private String filePathBpmn;
    private String fileNameBpmn;

    private Long jRepositoryId;

    private String processDefinitionid;

    private AppParamDto[] listJpmParamDto;

    private AppStatusDto[] listJpmStatusDto;

    private AppButtonDto[] listJpmButtonDto;

    private AppFunctionDto[] listJpmFunctionDto;

    private List<AppStepDto> listJpmStepDto;

    private List<JpmProcessLangDto> processLangs;

    private boolean isShowWorkflow;

    /** Only view */
    private String companyName;
    private boolean isClone;
    private String processType;

    /** use for deploy */
    private boolean isCloneRole;
    private boolean isCloneSLA;
    private Long processDeployOldId;

    private Long signType;
    private Long signPosition;

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

    public boolean getIsDeployed() {
        return isDeployed;
    }

    public void setIsDeployed(boolean isDeployed) {
        this.isDeployed = isDeployed;
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

    public AppParamDto[] getListJpmParamDto() {
        return listJpmParamDto;
    }

    public void setListJpmParamDto(AppParamDto[] listJpmParamDto) {
        this.listJpmParamDto = listJpmParamDto;
    }

    public AppStatusDto[] getListJpmStatusDto() {
        return listJpmStatusDto;
    }

    public void setListJpmStatusDto(AppStatusDto[] listJpmStatusDto) {
        this.listJpmStatusDto = listJpmStatusDto;
    }

    public AppButtonDto[] getListJpmButtonDto() {
        return listJpmButtonDto;
    }

    public void setListJpmButtonDto(AppButtonDto[] listJpmButtonDto) {
        this.listJpmButtonDto = listJpmButtonDto;
    }

    public AppFunctionDto[] getListJpmFunctionDto() {
        return listJpmFunctionDto;
    }

    public void setListJpmFunctionDto(AppFunctionDto[] listJpmFunctionDto) {
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

    public List<AppStepDto> getListJpmStepDto() {
        return listJpmStepDto;
    }

    public void setListJpmStepDto(List<AppStepDto> listJpmStepDto) {
        this.listJpmStepDto = listJpmStepDto;
    }

    public boolean getIsClone() {
        return isClone;
    }

    public void setIsClone(boolean isClone) {
        this.isClone = isClone;
    }

    public boolean getIsCloneRole() {
        return isCloneRole;
    }

    public void setIsCloneRole(boolean isCloneRole) {
        this.isCloneRole = isCloneRole;
    }

    public boolean getIsCloneSLA() {
        return isCloneSLA;
    }

    public void setIsCloneSLA(boolean isCloneSLA) {
        this.isCloneSLA = isCloneSLA;
    }

    public Long getProcessDeployOldId() {
        return processDeployOldId;
    }

    public void setProcessDeployOldId(Long processDeployOldId) {
        this.processDeployOldId = processDeployOldId;
    }

    public boolean getIsShowWorkflow() {
        return isShowWorkflow;
    }

    public void setIsShowWorkflow(boolean isShowWorkflow) {
        this.isShowWorkflow = isShowWorkflow;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public List<JpmProcessLangDto> getProcessLangs() {
        return processLangs;
    }

    public void setProcessLangs(List<JpmProcessLangDto> processLangs) {
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
