/*******************************************************************************
 * Class        ：AppProcessDeployLangRepository
 * Created date ：2020/01/15
 * Lasted date  ：2020/01/15
 * Author       ：KhuongTH
 * Change log   ：2020/01/15：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.LanguageMapDto;
import vn.com.unit.workflow.entity.JpmProcessDeployLang;


/**
 * AppProcessDeployLangRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface AppProcessDeployLangRepository extends DbRepository<JpmProcessDeployLang, Long> {
    List<JpmProcessDeployLang> findByProcessDeployId(@Param("processId")Long processId);
    
    JpmProcessDeployLang getByProcessDeployIdAndLang(@Param("processId")Long processId, @Param("lang")String lang);
    
    /**
     * @param processId
     * @return
     */
    List<LanguageMapDto> findProcessNameListById(@Param("processId") Long processId);
}
