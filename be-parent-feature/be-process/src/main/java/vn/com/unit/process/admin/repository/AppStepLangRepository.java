package vn.com.unit.process.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.entity.JpmStepLang;

/**
 * Created by QuangND on 8/1/2019.
 */
public interface AppStepLangRepository extends DbRepository<JpmStepLang, Long> {

    public List<JpmStepLang> getListJpmStepLangByStepIdAndListLang(@Param("stepId") Long stepId,
                                                        @Param("listCodeLanguage") List<String> listCodeLanguage);

    public List<JpmStepLang> getListJpmStepLangByIds(@Param("existingIds") List<Long> existingIds);
    
    public List<JpmStepLang> getListJpmStepLangByProcessId(@Param("processId") Long processId);
    
    public List<JpmStepLang> getListJpmStepLangByStepId(@Param("stepId") Long stepId);
    
}
