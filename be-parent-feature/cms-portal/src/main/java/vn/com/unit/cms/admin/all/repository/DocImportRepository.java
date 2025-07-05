package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.entity.DocumentImport;
import vn.com.unit.db.repository.DbRepository;

public interface DocImportRepository  extends DbRepository<DocumentImport, Long>{
    
    List<DocumentImport> findListDataImport(@Param("sessionKey") String sessionKey);
}
