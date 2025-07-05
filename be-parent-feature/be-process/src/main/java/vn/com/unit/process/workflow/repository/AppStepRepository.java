/*******************************************************************************
 * Class        :AppStepRepository
 * Created date :2019/06/17
 * Lasted date  :2019/06/17
 * Author       :KhoaNA
 * Change log   :2019/06/17:01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.process.workflow.dto.AppStepDto;
import vn.com.unit.workflow.repository.JpmStepRepository;

/**
 * AppStepRepository
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppStepRepository extends JpmStepRepository {
	
    /**
     * find List<AppStepDto> by processId and deleteBy is null
     * get data :step, status, button, function
     * 
     * @param processId
     * 			type Long
     * @return List<AppStepDto>
     * @author KhoaNA
     */
    List<AppStepDto> findJpmStepDtoDetailByProcessId(@Param("processId") Long processId, @Param("lang") String lang, @Param("constantStatusType") String constantStatusType);

    /**
     * getListStepDefaultFreeform
     * @param appSystem
     * @return List<AppStepDto>
     * @author KhuongTH
     */
    List<AppStepDto> getListStepDefaultFreeform(@Param("appSystem")String appSystem);
}
