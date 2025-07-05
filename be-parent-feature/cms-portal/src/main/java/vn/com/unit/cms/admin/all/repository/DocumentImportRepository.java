package vn.com.unit.cms.admin.all.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.document.dto.DocumentImportDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface DocumentImportRepository extends ImportExcelInterfaceRepository<DocumentImportDto>{

    String getMaxCode(@Param("tableName") String tableName,@Param("prefix") String prefix);
    
    public Document findDocumentByCategoryAndCode(@Param("categoryId") Long categoryId,@Param("docCode") String docCode);
    public Document findDocumentByCode(@Param("docCode") String docCode);
    
}
