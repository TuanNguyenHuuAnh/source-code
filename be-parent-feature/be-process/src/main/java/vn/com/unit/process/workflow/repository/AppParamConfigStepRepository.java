/*******************************************************************************
 * Class        ：AppParamConfigStepRepository
 * Created date ：2019/11/29
 * Lasted date  ：2019/11/29
 * Author       ：KhuongTH
 * Change log   ：2019/11/29：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.workflow.dto.JpmParamConfigDto;
import vn.com.unit.workflow.repository.JpmParamConfigRepository;


/**
 * AppParamConfigStepRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppParamConfigStepRepository extends JpmParamConfigRepository {
    List<JpmParamConfigDto> getConfigsByParamId(@Param("paramId") Long paramId, @Param("processId") Long processId);
    
    @Modifying
    void deleteByParamId(@Param("paramId") Long paramId, @Param("user") String user, @Param("sysDate") Date sysDate);
}
