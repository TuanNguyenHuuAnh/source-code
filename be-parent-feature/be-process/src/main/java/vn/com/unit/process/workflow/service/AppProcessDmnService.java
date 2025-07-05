/*******************************************************************************
 * Class        ：AppProcessDmnService
 * Created date ：2021/03/15
 * Lasted date  ：2021/03/15
 * Author       ：KhuongTH
 * Change log   ：2021/03/15：01-00 KhuongTH create a new
******************************************************************************/
package vn.com.unit.process.workflow.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.process.workflow.dto.AppProcessDmnDto;

/**
 * <p>
 * AppProcessDmnService
 * </p>
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppProcessDmnService {

    List<AppProcessDmnDto> getAppProcessDmnDtosByProcessId(Long processId);

    boolean uploadDmnFiles(Long processId, List<MultipartFile> dmnFiles) throws IOException;

    boolean delete(Long id);
    
    void deployDmn(Long processId, Long processDeployId, String parentDeploymentId) throws Exception;
}
