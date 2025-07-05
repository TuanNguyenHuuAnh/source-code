package vn.com.unit.ep2p.service.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.dto.CmsCommonPagination;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationDto;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationParam;
import vn.com.unit.cms.core.module.customer.dto.CustomerInformationSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentInformationDto;
import vn.com.unit.cms.core.module.document.dto.DocumentInformationSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchParam;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.ep2p.service.AddDocumentsService;

@Service
@Transactional(transactionManager = "transactionManagerDb2", readOnly = true, rollbackFor = Exception.class)
public class AddDocumentsServiceImpl implements AddDocumentsService{
	
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;

	@Autowired
	private ServletContext servletContext;
	
	private static final String SP_INFOMATION_DOCUMENT_COMMOM = " ";
	private static final String SP_LIST_ADD_DOCUMENT = " ";
	private static final String sp_LIST_ADD_DOCUMENT_SUBMIT = " ";

	@Override
	public CmsCommonPagination<DocumentInformationSearchDto> getListInformation(DocumentInformationSearchDto dto) {
		DocumentSearchParam param = new DocumentSearchParam();
		param.agentCode = dto.getAgentCode();
		param.docNo = dto.getDocNo();
		param.policyNo = dto.getPolicyNo();
		sqlManagerDb2Service.call(SP_INFOMATION_DOCUMENT_COMMOM, param);
		List<DocumentInformationSearchDto> datas = param.data;
		CmsCommonPagination<DocumentInformationSearchDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

	@Override
	public CmsCommonPagination<DocumentInformationSearchDto> getListDocumentAdd(DocumentInformationSearchDto dto) {
		DocumentSearchParam param = new DocumentSearchParam();
		param.docNo = dto.getDocNo();
		param.policyNo = dto.getPolicyNo();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		sqlManagerDb2Service.call(SP_LIST_ADD_DOCUMENT, param);
		List<DocumentInformationSearchDto> datas = param.data;
		CmsCommonPagination<DocumentInformationSearchDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}
	@Override
	public CmsCommonPagination<DocumentInformationSearchDto> getListDocumentSubmit(DocumentInformationSearchDto dto) {
		DocumentSearchParam param = new DocumentSearchParam();
		param.docNo = dto.getDocNo();
		param.policyNo = dto.getPolicyNo();
		param.page = dto.getPage();
		param.pageSize = dto.getPageSize();
		sqlManagerDb2Service.call(sp_LIST_ADD_DOCUMENT_SUBMIT, param);
		List<DocumentInformationSearchDto> datas = param.data;
		CmsCommonPagination<DocumentInformationSearchDto> resultData = new CmsCommonPagination<>();
		resultData.setData(datas);
		resultData.setTotalData(param.totalData);
		return resultData;
	}

}
