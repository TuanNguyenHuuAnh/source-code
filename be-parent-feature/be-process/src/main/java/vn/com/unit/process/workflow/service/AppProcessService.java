/*******************************************************************************
 * Class        JpmProcessService
 * Created date 2019/06/10
 * Lasted date  2019/06/10
 * Author       KhoaNA
 * Change log   2019/06/10 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.common.exception.AppException;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.process.workflow.dto.AppProcessDto;
import vn.com.unit.process.workflow.dto.AppProcessSearchDto;
import vn.com.unit.process.workflow.dto.AppStatusDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.entity.JpmProcess;

/**
 * JpmProcessService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppProcessService {

    /**
     * Get AppProcessDto by searchDto.
     * 
     * @param page
     *            type int
     * @param pageSize
     *            type int
     * @param searchDto
     *            type AppProcessSearchDto
     * @return PageWrapper<AppProcessDto>
     * @author KhoaNA
     * @throws DetailException
     */
    PageWrapper<AppProcessDto> search(int page, int pageSize, AppProcessSearchDto searchDto) throws DetailException;

    /**
     * Get AppProcessDto by id.
     * 
     * @param id
     *            type Long
     * @return AppProcessDto
     * @author KhoaNA
     */
    AppProcessDto getAppProcessDtoById(Long id);

    /**
     * Get AppProcessDto by code, companyId and deletedBy is null
     * 
     * @param code
     *            type String
     * @param companyId
     *            type Long
     * @return AppProcessDto
     * @author KhoaNA
     */
    AppProcessDto getJpmProcessByCodeAndCompanyId(String code, Long companyId);

    /**
     * Save JpmProcess from AppProcessDto. MajorVersion + 1 if isMajor is true. Else MinorVersion + 1 if isMajor is true. JpmProcessHistory
     * is saved.
     *
     * @param objectDto
     *            type AppProcessDto
     * @param isMajor
     *            type boolean
     * @return JpmProcess
     * @author KhoaNA
     */
    public JpmProcess saveJpmProcessDtoWithAutoVersion(AppProcessDto objectDto, boolean isMajor);

    /**
     * Delete JpmProcess by id.
     *
     * @param id
     *            type Long
     * @return boolean
     * @author KhoaNA
     */
    public boolean deletedById(Long id);

    /**
     * Delete JpmProcess by id.
     *
     * @param businessId
     *            type Long
     * @return List<AppProcessDto>
     * @author KhuongTH
     */
    public List<AppProcessDto> findJpmProcessDtoByBusinessId(Long businessId);

    /**
     * getByFormId
     *
     * @param businessId
     *            type Long
     * @return JpmProcess
     * @author TaiTT
     */
    public AppProcessDto getByFormId(Long businessId, Long processId);

    /**
     * Save JpmProcess from AppProcessDto. And deploy JpmProcess
     *
     * @param objectDto
     *            type {@link AppProcessDto}
     * @param isMajor
     *            type {@link boolean}
     * @return JpmProcessDeploy
     * @throws AppException
     *             the app exception
     * @author KhuongTH
     * @throws Exception
     */
    public Long saveAndDeployJpmProcessDtoWithAutoVersion(AppProcessDto objectDto, boolean isMajor) throws Exception;

    /**
     * getSelect2DtoListBusinessId
     *
     * @param businessId
     *            type Long
     * @return List<Select2Dto>
     * @author KhuongTH
     */
    public List<Select2Dto> getSelect2DtoListBusinessId(Long businessId);

    /**
     * getJpmProcessByNameAndCompanyId
     * 
     * @Param name type String
     * @param businessId
     *            type Long
     * @return AppProcessDto
     * @author KhuongTH
     */
    AppProcessDto getJpmProcessByNameAndCompanyId(String name, Long companyId);

    /**
     * <p>
     * Clone jpm process.
     * </p>
     *
     * @param appProcessDto
     *            type {@link AppProcessDto}
     * @return {@link Long}
     * @throws Exception
     *             the exception
     * @author KhuongTH
     */
    public Long cloneJpmProcess(AppProcessDto appProcessDto) throws Exception;

    /**
     * getStatusCodeByTaskId
     * 
     * @param taskIdStr
     *            type String
     * @return String
     * @author KhoaNA
     */
    public String getStatusCodeByTaskId(String taskIdStr);

    /**
     * getStatusListByBpmnFile
     * 
     * @param fileStream
     * @return Map<String, AppStatusDto>
     * @author KhuongTH
     */
    public Map<String, AppStatusDto> getStatusListByBpmnFile(InputStream fileStream);

    /**
     * importJpmProcess.
     *
     * @param processImportDto
     *            type {@link JpmProcessImportExportDto}
     * @return boolean
     * @throws Exception
     *             the exception
     * @author KhuongTH
     */
    public Long importJpmProcess(JpmProcessImportExportDto processImportDto) throws Exception;
}
