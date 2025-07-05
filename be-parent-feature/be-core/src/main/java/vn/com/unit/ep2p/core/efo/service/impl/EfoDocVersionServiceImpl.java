/*******************************************************************************
 * Class        ：EfoDocVersionServiceImpl
 * Created date ：2020/12/03
 * Lasted date  ：2020/12/03
 * Author       ：Tan Tai
 * Change log   ：2020/12/03：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoDocVersionDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.entity.EfoDocVersion;
import vn.com.unit.ep2p.core.efo.repository.EfoDocVersionRepository;
import vn.com.unit.ep2p.core.efo.service.EfoDocVersionService;

/**
 * <p>EfoDocVersionServiceImpl</p>.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoDocVersionServiceImpl implements EfoDocVersionService {

    /** The efo oz doc version repository. */
    @Autowired
    private EfoDocVersionRepository efoDocVersionRepository;

    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /** The common service. */
    @Autowired
    private JCommonService commonService;
    
    /** The sql manager service. */
    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;

    /* (non-Javadoc)
     * @see vn.com.unit.mbal.core.efo.service.EfoOzDocVersionService#saveFromEfoOzDoc(vn.com.unit.mbal.core.efo.entity.EfoOzDoc)
     */
    @Override
    public EfoDocVersion saveFromEfoDoc(EfoDoc efoDoc) {
    	EfoDocVersion efoDocVersion = objectMapper.convertValue(efoDoc, EfoDocVersion.class);

        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();

//        efoDocVersion.setDocId(efoDoc.getId());
        efoDocVersion.setCreatedId(userId);
        efoDocVersion.setCreatedDate(sysDate);
        efoDocVersion.setUpdatedId(userId);
        efoDocVersion.setUpdatedDate(sysDate);
        return this.save(efoDocVersion);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.DocumentService#generateId()
     */
    @Override
    public Long generateId() {
        // get next id from sequence
        return sqlManagerService.getNextValBySeqName(CoreConstant.SEQ.concat(AppCoreConstant.TABLE_EFO_DOC_VERSION));
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.DocumentService#save(vn.com.unit.core.entity.AbstractDocument)
     */
    @Override
    public EfoDocVersion save(EfoDocVersion entity) {
        return efoDocVersionRepository.create(entity);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.DocumentService#save(vn.com.unit.core.dto.AbstractDocumentDto)
     */
    @Override
    public EfoDocVersion save(EfoDocVersionDto objectDto) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.DocumentService#updateVersion(vn.com.unit.core.dto.AbstractDocumentDto, boolean)
     */
    @Override
    public EfoDocVersion updateVersion(EfoDocVersionDto documentDto, boolean isMajor) {
        // TODO Auto-generated method stub
        return null;
        
    }

}
