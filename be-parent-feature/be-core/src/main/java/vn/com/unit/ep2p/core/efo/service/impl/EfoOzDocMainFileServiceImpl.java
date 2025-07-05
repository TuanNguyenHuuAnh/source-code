/*******************************************************************************
 * Class        ：EfoOzDocMainFileServiceImpl
 * Created date ：2020/11/18
 * Lasted date  ：2020/11/18
 * Author       ：NhanNV
 * Change log   ：2020/11/18：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.enumdef.DocumentActionFlag;
import vn.com.unit.core.enumdef.DocumentState;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocMainFile;
import vn.com.unit.ep2p.core.efo.repository.EfoOzDocMainFileRepository;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocMainFileService;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocMainFileVersionService;
import vn.com.unit.workflow.activiti.dto.JpmProcessInstActDto;
import vn.com.unit.workflow.activiti.service.JpmProcessInstActService;

/**
 * EfoOzDocMainFileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoOzDocMainFileServiceImpl implements EfoOzDocMainFileService {

    @Autowired
    private EfoOzDocMainFileRepository efoOzDocMainFileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private EfoOzDocMainFileVersionService efoOzDocMainFileVersionService;
    
    @Autowired
    private JpmProcessInstActService jpmProcessInstActService;

    @Override
    public EfoOzDocMainFileDto saveEfoOzDocMainFile(EfoOzDocMainFile saveObj, DocumentActionFlag actionFlag, boolean isIncreaseVersion,
            boolean isMajor) throws Exception {

        Long docId = saveObj.getDocId();

        int refType = 1;
        JpmProcessInstActDto instActDto = jpmProcessInstActService.getJpmProcessInstActDtoByReference(docId, refType);
        String docState = (instActDto == null) ? DocumentState.DRAFT.toString() : instActDto.getCommonStatusCode();

        if (actionFlag == DocumentActionFlag.CREATE_DATA) {
            efoOzDocMainFileRepository.create(saveObj);
        } else {
            efoOzDocMainFileRepository.update(saveObj);
        }

        EfoOzDocMainFileDto resDto = objectMapper.convertValue(saveObj, EfoOzDocMainFileDto.class);

        /** === save history === */
        if (isIncreaseVersion && !DocumentState.DRAFT.toString().equals(docState)) {
            efoOzDocMainFileVersionService.saveFromEfoOzDocMainFile(saveObj, actionFlag);
        }

        return resDto;
    }

    @Override
    public String generateDocFileName(Long docId, Long majorVersion, Long minorVersion, boolean isFinal, boolean isOzd) {
        String version = "FN";
        String ext = CoreConstant.EXTEND_PDF;

        if (isOzd) {
            ext = CoreConstant.EXTEND_OZD;
        }

        if (!isFinal) {
            version = "v".concat(String.valueOf(majorVersion)).concat(CoreConstant.DOT).concat(String.valueOf(minorVersion));
        }

        return commonService.generateCodeFromId(docId).concat("-ToTrinh-").concat(version).concat(ext);
    }

    @Override
    public EfoOzDocMainFileDto getEfoOzDocMainFileDtoById(Long mainFileId) {
        EfoOzDocMainFile efoOzDocMainFile = efoOzDocMainFileRepository.findOne(mainFileId);
        if (null != efoOzDocMainFile) {
            return objectMapper.convertValue(efoOzDocMainFile, EfoOzDocMainFileDto.class);
        }
        return null;
    }

    @Override
    public EfoOzDocMainFile getEfoOzDocMainFileById(Long id) {
        return efoOzDocMainFileRepository.findOne(id);
    }

    @Override
    public EfoOzDocMainFileDto getEfoOzDocMainFileDtoByDocId(Long docId) {
        return efoOzDocMainFileRepository.getEfoOzDocMainFileDtoByDocId(docId);
    }
    
    @Override
    public EfoOzDocMainFileDto getEfoOzDocMainFileDtoByVersionId(Long docMainFileVersionId) {
        return efoOzDocMainFileRepository.getEfoOzDocMainFileDtoByVersionId(docMainFileVersionId);
    }

}
