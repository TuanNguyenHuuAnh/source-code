/*******************************************************************************
* Class        JpmProcessService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.workflow.dto.JpmProcessDto;
import vn.com.unit.workflow.dto.JpmProcessImportExportDto;
import vn.com.unit.workflow.dto.JpmProcessSearchDto;
import vn.com.unit.workflow.entity.JpmProcess;

/**
 * JpmProcessService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmProcessService {

    static final String TABLE_ALIAS_JPM_PROCESS = "pro";

    /**
     * get JpmProcessDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmProcessDto}
     * @author KhuongTH
     */
    JpmProcessDto getJpmProcessDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmProcess with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmProcess
     *            type {@link JpmProcess}
     * @return {@link JpmProcess}
     * @author KhuongTH
     */
    JpmProcess saveJpmProcess(JpmProcess jpmProcess);

    /**
     * save JpmProcessDto
     * 
     * @param jpmProcessDto
     *            type {@link JpmProcessDto}
     * @return {@link JpmProcess}
     * @author KhuongTH
     */
    JpmProcess saveJpmProcessDto(JpmProcessDto jpmProcessDto);

    /**
     * <p>
     * count process by search condition
     * </p>
     * 
     * @param searchDto
     *            type {@link JpmProcessSearchDto}
     * @return int
     * @author KhuongTH
     */
    int countBySearchCondition(JpmProcessSearchDto searchDto);

    /**
     * <p>
     * get list process dto by condition
     * </p>
     * 
     * @param searchDto
     *            type {@link JpmProcessSearchDto}
     * @param pageIndex
     *            type int
     * @param pageSize
     *            type int
     * @param isPaging
     *            type boolean
     * @return {@link List<JpmProcessDto>}
     * @author KhuongTH
     */
    List<JpmProcessDto> getProcessDtosByCondition(JpmProcessSearchDto searchDto, Pageable pageable);

    /**
     * <p>
     * Import process.
     * </p>
     *
     * @param processImportDto
     *            type {@link JpmProcessImportExportDto}
     * @return {@link Long} : id of process
     * @throws DetailException
     *             the detail exception
     * @author KhuongTH
     */
    Long importProcess(JpmProcessImportExportDto processImportDto) throws DetailException;

    /**
     * <p>
     * Gets the process dto by code and company id.
     * </p>
     *
     * @param processCode
     *            type {@link String}
     * @param companyId
     *            type {@link Long}
     * @return the process dto by code and company id
     * @author KhuongTH
     */
    JpmProcessDto getProcessDtoByCodeAndCompanyId(String processCode, Long companyId);

    /**
     * <p>
     * Gets the jpm process dto by process id.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @return the jpm process dto by process id
     * @author sonnd
     */
    JpmProcessDto getJpmProcessDtoByProcessId(Long processId);

    /**
     * <p>
     * Update process info.
     * </p>
     *
     * @param fileStream
     *            type {@link InputStream}
     * @param processDto
     *            type {@link JpmProcessDto}
     * @return {@link byte[]}
     * @author KhuongTH
     */
    byte[] updateProcessInfo(InputStream fileStream, JpmProcessDto processDto);

    /**
     * <p>
     * Update version for process.
     * </p>
     *
     * @param processId
     *            type {@link Long}
     * @param isMajor
     *            type {@link boolean}
     * @return true, if successful
     * @author KhuongTH
     */
    boolean updateVersionForProcess(Long processId, boolean isMajor);
    
    
    public JpmProcessDto getJpmProcessDtoByBusinessId(Long businessId, Long companyId);

}