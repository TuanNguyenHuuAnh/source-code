/*******************************************************************************
 * Class        ：WorkflowDiagramService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：KhuongTH
 * Change log   ：2021/01/13：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.core;

import java.io.InputStream;
import java.util.List;

import vn.com.unit.workflow.dto.JpmParamDto;
import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.dto.JpmStepDto;

/**
 * <p>
 * WorkflowDiagramService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface WorkflowDiagramService {

    /**
     * <p>
     * Builds the step dtos by process diagram.
     * </p>
     *
     * @param fileStream
     *            type {@link InputStream}
     * @return {@link List<JpmStepDto>}
     * @author KhuongTH
     */
    List<JpmStepDto> buildStepDtosByProcessDiagram(InputStream fileStream);

    /**
     * <p>
     * Builds the param dtos by process diagram.
     * </p>
     *
     * @param fileStream
     *            type {@link InputStream}
     * @return {@link List<JpmParamDto>}
     * @author KhuongTH
     */
    List<JpmParamDto> buildParamDtosByProcessDiagram(InputStream fileStream);

    /**
     * <p>
     * getStatusCodeByStepCode
     * </p>
     * .
     *
     * @param stepCode
     *            type {@link String}
     * @return {@link String}
     * @author @Override KhuongTH
     */
    String getStatusCodeByStepCode(String stepCode);

    /**
     * <p>
     * Update process info.
     * </p>
     *
     * @param fileStream
     *            type {@link InputStream}
     * @param processKey
     *            type {@link String}
     * @param processCategory
     *            type {@link String}
     * @return {@link byte[]}
     * @author KhuongTH
     */
    byte[] updateProcessInfo(InputStream fileStream, String processKey, String processCategory);

    /**
     * <p>
     * Update candidate for process info.
     * </p>
     *
     * @param contentFile
     *            type {@link byte[]}
     * @param stepDeployDtos
     *            type {@link List<JpmStepDeployDto>}
     * @return {@link byte[]}
     * @author KhuongTH
     */
    byte[] updateCandidateForProcessInfo(byte[] contentFile, List<JpmStepDeployDto> stepDeployDtos);
    
    /**
     * <p>
     * Gets the diagram from process file.
     * </p>
     *
     * @param contentFile
     *            type {@link byte[]}
     * @return the diagram from process file; encode base64
     * @author KhuongTH
     */
    String getDiagramFromProcessFile(byte[] contentFile);

}
