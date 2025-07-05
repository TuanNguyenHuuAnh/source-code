package vn.com.unit.cms.admin.all.service;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.admin.all.dto.DocumentManagementDto;
import vn.com.unit.cms.admin.all.entity.DocumentManagement;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

public interface DocumentManagementService
		extends DocumentWorkflowCommonService<DocumentManagementDto, DocumentManagementDto> {

	PageWrapper<DocumentManagementDto> search(int page, DocumentManagementDto condition, String lang);

	void delete(Long id) throws Exception;

	void createFolder(Long parentId, String name) throws Exception;

	void uploadFile(Long id, MultipartFile multipartFile) throws Exception;

	String getDocumentManagementPhysicalPathById(Long id);

	DocumentManagement findOne(Long id);

}
