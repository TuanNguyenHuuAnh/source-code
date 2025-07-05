package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.FaqsCategoryImport;
import vn.com.unit.db.repository.DbRepository;

public interface FaqsImportRepository  extends DbRepository<FaqsCategoryImport, Long>{
    
    List<FaqsCategoryImport> findListDataImport(@Param("sessionKey") String sessionKey);
}
