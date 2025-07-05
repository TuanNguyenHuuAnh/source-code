package vn.com.unit.cms.admin.all.service.impl;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.admin.all.dto.DocumentTypeDto;
import vn.com.unit.cms.admin.all.service.DocumentTypeService;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.AccountDetailDto;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DocumentTypeServiceImpl extends DocumentWorkflowCommonServiceImpl<DocumentTypeDto, DocumentTypeDto>
        implements DocumentTypeService {

    @Override
    public DocumentTypeDto getEdit(Long id, String customerAlias, Locale locale) {
        // TODO
        return new DocumentTypeDto();
    }

    @Override
    public DocumentActionReq actionBusiness(DocumentActionReq action, EfoDocDto efoDocDto, Locale locale)
            throws Exception {
        DocumentTypeDto editDto = (DocumentTypeDto) action;
        // this.saveUpdateDocumentStatus(editDto, null);

        // ACTION SAVE OR UPDATE OBJECT WHEN CLICK BUTTON OF PROCESS

        return editDto;
    }

    @Override
    public void sendMailProcess(DocumentActionReq abstractProcessDto, Integer nextStepNo, String nextStatusCode,
            Integer curStepNo, AccountDetailDto accountAction, HttpServletRequest httpServletRequest, Locale locale)
            throws Exception {
        super.sendMailProcess(abstractProcessDto, nextStepNo, nextStatusCode, curStepNo, accountAction,
                httpServletRequest, locale);

    }
}
