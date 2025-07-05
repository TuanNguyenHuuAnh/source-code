package vn.com.unit.cms.admin.all.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.cms.admin.all.dto.DocumentManagementDto;
import vn.com.unit.cms.admin.all.entity.DocumentManagement;
import vn.com.unit.db.repository.DbRepository;

public interface DocumentManagementRepository extends DbRepository<DocumentManagement, Long> {

	List<DocumentManagementDto> search(@Param("startIndex") int startIndex, @Param("sizeOfPage") int sizeOfPage,
			@Param("searchDto") DocumentManagementDto searchDto, @Param("lang") String lang);

	int count(@Param("searchDto") DocumentManagementDto searchDto, @Param("lang") String lang);

	String getPathById(@Param("id") Long id);

	DocumentManagement getDocumentManagementByCodeAndParentId(@Param("code") String code, @Param("parentId") Long parentId);

}
