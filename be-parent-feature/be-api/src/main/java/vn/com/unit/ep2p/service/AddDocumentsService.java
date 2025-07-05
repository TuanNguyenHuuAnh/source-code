package vn.com.unit.ep2p.service;


import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.document.dto.DocumentInformationSearchDto;

public interface AddDocumentsService {

	
	CmsCommonPagination<DocumentInformationSearchDto> getListInformation(DocumentInformationSearchDto dto);
	
	CmsCommonPagination<DocumentInformationSearchDto> getListDocumentAdd(DocumentInformationSearchDto dto);

	CmsCommonPagination<DocumentInformationSearchDto> getListDocumentSubmit(DocumentInformationSearchDto dto);



}
