package vn.com.unit.cms.admin.all.repository;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.core.module.faqs.dto.FaqsCategoryImportDto;
import vn.com.unit.cms.core.module.faqs.entity.Faqs;
import vn.com.unit.imp.excel.repository.ImportExcelInterfaceRepository;

public interface FaqsCategoryImportRepository extends ImportExcelInterfaceRepository<FaqsCategoryImportDto>{

    String getMaxCode(@Param("tableName") String tableName,@Param("prefix") String prefix);
    
    public Faqs findFaqsByCategoryAndCode(@Param("categoryId") Long categoryId,@Param("faqsCode") String faqsCode);
    public Faqs findFaqsByCode(@Param("faqsCode") String faqsCode);
    
}
