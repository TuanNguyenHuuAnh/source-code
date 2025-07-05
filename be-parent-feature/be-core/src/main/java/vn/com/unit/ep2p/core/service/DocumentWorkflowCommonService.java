package vn.com.unit.ep2p.core.service;

import java.util.Locale;

import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.res.dto.DocumentSaveReq;

@SuppressWarnings("rawtypes")
public interface DocumentWorkflowCommonService<SAVE_DTO extends DocumentSaveReq, EDIT_DTO extends DocumentActionReq>
        extends DocumentAppService {

    public EDIT_DTO getEdit(Long id, String customerAlias, Locale locale);
}
