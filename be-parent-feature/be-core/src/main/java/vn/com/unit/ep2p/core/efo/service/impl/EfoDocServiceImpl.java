/*******************************************************************************
 * Class        ：EfoDocServiceImpl
 * Created date ：2020/11/13
 * Lasted date  ：2020/11/13
 * Author       ：Tan Tai
 * Change log   ：2020/11/13：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.core.dto.EfoOzDocStatisticsDto;
import vn.com.unit.core.dto.EfoOzDocStatisticsSearchDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;
import vn.com.unit.ep2p.core.constant.AppCoreConstant;
import vn.com.unit.ep2p.core.efo.dto.EfoDocDto;
import vn.com.unit.ep2p.core.efo.dto.EfoOzDocMainFileDto;
import vn.com.unit.ep2p.core.efo.entity.EfoDoc;
import vn.com.unit.ep2p.core.efo.repository.EfoDocRepository;
import vn.com.unit.ep2p.core.efo.service.EfoDocService;
import vn.com.unit.ep2p.core.efo.service.EfoDocVersionService;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.core.utils.ChannelUtils;

/**
 * <p>
 * EfoDocServiceImpl
 * </p>
 * .
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoDocServiceImpl implements EfoDocService {

//    private static final String BANCAS = "";
//    private static final String AGENCY = "";

    /** The efo doc repository. */
    @Autowired
    EfoDocRepository efoDocRepository;

    /** The efo doc version service. */
    @Autowired
    EfoDocVersionService efoDocVersionService;

    /** The common service. */
    @Autowired
    private JCommonService commonService;

    /** The sql manager service. */
    @Autowired
    @Qualifier("sqlManagerServicePr")
    private SqlManagerServiceImpl sqlManagerService;

    /** The object mapper. */
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.DocumentService#generateId()
     */
    @Override
    public Long generateId() {
        // get next id from sequence
        return sqlManagerService.getNextValBySeqName(CoreConstant.SEQ.concat(AppCoreConstant.TABLE_EFO_DOC));
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.DocumentService#save(vn.com.unit.core.entity.
     * AbstractDocument)
     */
    @Override
    public EfoDoc save(EfoDoc entity) {
        Long id = entity.getId();

        if (null == id) {
            efoDocRepository.create(entity);
        } else {
            efoDocRepository.update(entity);
        }

        return entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.DocumentService#save(vn.com.unit.core.dto.
     * AbstractDocumentDto)
     */
    @Override
    public EfoDoc save(EfoDocDto objectDto) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = objectDto.getDocId();
        EfoDoc objectSave = new EfoDoc();
        objectSave = efoDocRepository.getEfoDocById(id);

        if (null == objectSave) { // Add new
            objectSave = objectMapper.convertValue(objectDto, EfoDoc.class);
            objectSave.setId(objectDto.getDocId());
            objectSave.setCreatedId(userId);
            objectSave.setCreatedDate(sysDate);
            objectSave.setUpdatedId(userId);
            objectSave.setUpdatedDate(sysDate);
            objectSave.setOwnerId(userId);
            objectSave.setMajorVersion(Long.valueOf(CoreConstant.STR_ONE));
            objectSave.setMinorVersion(Long.valueOf(CoreConstant.STR_ZERO));
            String docTitleCode = objectDto.generateDocTitleFull();
            objectSave.setDocTitle(docTitleCode);

            efoDocRepository.create(objectSave);

            // Do không dùng eForm nên Lưu form id = id của dòng dữ liệu
            if (objectSave.getId() != null) {
                objectSave.setFormId(objectSave.getId());
                efoDocRepository.update(objectSave);
            }
        } else { // Update
            // copy from dto to save
            CommonObjectUtil.copyPropertiesNonNull(objectDto, objectSave);

//            // Check and upVersion
            if (objectDto.isMajor()) {
                Long majorVersion = objectSave.getMajorVersion() + 1;
                objectSave.setMajorVersion(majorVersion);
                objectSave.setMinorVersion(Long.valueOf(CoreConstant.NUMBER_ZERO));
            } else {
                Long minorVersion = objectSave.getMinorVersion() + 1;
                objectSave.setMinorVersion(minorVersion);
            }

            // manual handling set data
            objectSave.setUpdatedId(userId);
            objectSave.setUpdatedDate(sysDate);

            // Do không dùng eForm nên Lưu form id = id của dòng dữ liệu
            if (objectSave.getFormId() == null) {
                objectSave.setFormId(objectSave.getId());
            }
            efoDocRepository.update(objectSave);
        }

        id = objectSave.getId();
        objectDto.setId(id);
        objectSave.setFormId(id);

        // Save EfoOzDocVersion
        efoDocVersionService.saveFromEfoDoc(objectSave);

        return objectSave;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.core.service.DocumentService#updateVersion(vn.com.unit.core.dto.
     * AbstractDocumentDto, boolean)
     */
    @Override
    public EfoDoc updateVersion(EfoDocDto documentDto, boolean isMajor) {
        EfoDoc objSave = null;
        if (null != documentDto) {
            objSave = efoDocRepository.findOne(documentDto.getDocId());
            if (objSave != null) {
                if (isMajor) {
                    Long majorVersion = objSave.getMajorVersion() + 1;
                    objSave.setMajorVersion(majorVersion);
                    objSave.setMinorVersion(Long.valueOf(CoreConstant.NUMBER_ZERO));
                } else {
                    Long minorVersion = objSave.getMinorVersion() + 1;
                    objSave.setMinorVersion(minorVersion);
                }
                efoDocRepository.update(objSave);

                // Save EfoOzDocVersion
                efoDocVersionService.saveFromEfoDoc(objSave);
            }
        }
        return objSave;
    }

    /**
     * <p>
     * Get efo oz doc by id.
     * </p>
     *
     * @author Tan Tai
     * @param id type {@link Long}
     * @return {@link EfoDoc}
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.core.efo.service.EfoDocService#getEfoOzDocById(java.lang.
     * Long)
     */
    @Override
    public EfoDoc getEfoDocById(Long id) {
        if (Objects.isNull(id)) {
            return null;
        } else {
            return efoDocRepository.findOne(id);
        }
    }

    @Override
    public EfoDocDto getEfoDocDtoById(Long id) {
        if (Objects.isNull(id)) {
            return null;
        } else {
            return efoDocRepository.getEfoDocDtoById(id);
        }
    }

    /**
     * <p>
     * Update efo oz doc by oz doc main file.
     * </p>
     *
     * @author Tan Tai
     * @param ozDocMainFileDto type {@link EfoOzDocMainFileDto}
     * @return {@link EfoDoc}
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.core.efo.service.EfoDocService#updateEfoOzDocByOzDocMainFile
     * (vn.com.unit.core.dto.EfoOzDocMainFileDto)
     */
    @Override
    public EfoDoc updateEfoDocByOzDocMainFile(EfoOzDocMainFileDto ozDocMainFileDto) {
        EfoDoc resObj = null;
        if (null != ozDocMainFileDto) {
            resObj = efoDocRepository.findOne(ozDocMainFileDto.getDocId());
            if (resObj != null) {
                // TODO tantm OzDoc chua co properties
                // resObj.setDocInputJson(ozDocMainFileDto.getDocInputJson());
                // resObj.setFileNamePdf(ozDocMainFileDto.getFileNamePdf());
                // resObj.setFormFileName(ozDocMainFileDto.getFormFileName());
                // resObj.setEcmRepositoryId(ozDocMainFileDto.getRepositoryId());
                // resObj.setDocName(ozDocMainFileDto.getFileNameView());
                efoDocRepository.update(resObj);
            }
        }
        return resObj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.core.efo.service.EfoDocService#getDetailDocumentByRole(java.
     * util.List, java.util.List, java.util.List, java.lang.Long)
     */
    @Override
    public DocumentDataResultDto getDetailDocumentByRole(List<Long> posIds, List<Long> orgIds, List<String> refTypes,
            Long documentId) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoDocRepository.getDetailDocumentByRole(orgIds, posIds, accountId, refTypes, documentId, lang);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.core.efo.service.EfoDocService#
     * getDocumentDataResultDtoByStatusDraft(vn.com.unit.core.dto.
     * EfoDocumentFilterSearchDto, org.springframework.data.domain.Pageable)
     */
    @Override
    public List<DocumentDataResultDto> getDocumentDataResultDtoByStatusDraft(EfoDocumentFilterSearchDto searchDto,
            Pageable pagable) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoDocRepository.getDocumentDataResultDtoByDraft(accountId, searchDto, pagable, lang).getContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.core.efo.service.EfoDocService#
     * countDocumentDataResultDtoByStatusDraft(vn.com.unit.core.dto.
     * EfoDocumentFilterSearchDto)
     */
    @Override
    public int countDocumentDataResultDtoByStatusDraft(EfoDocumentFilterSearchDto searchDto) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String actionUser = UserProfileUtils.getUserPrincipal().getUsername();
        searchDto.setActionUser(actionUser);
        searchDto.setChannel(ChannelUtils.getUserChannel());

        return efoDocRepository.countDocumentDataResultDtoByDraft(accountId, searchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.core.efo.service.EfoDocService#getDocumentDataResultDto(vn.
     * com.unit.core.dto.EfoDocumentFilterSearchDto, java.util.List, java.util.List,
     * java.util.List, org.springframework.data.domain.Pageable)
     */
    @Override
    public List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, List<Long> posIds,
            List<Long> orgIds, List<String> refTypes, Pageable pagable) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoDocRepository
                .getDocumentDataResultDtoByMyDocument(orgIds, posIds, accountId, searchDto, refTypes, pagable, lang)
                .getContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.mbal.core.efo.service.EfoDocService#countDocumentDataResultDto(vn
     * .com.unit.core.dto.EfoDocumentFilterSearchDto, java.util.List,
     * java.util.List, java.util.List)
     */
    public int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, List<Long> posIds, List<Long> orgIds,
            List<String> refTypes) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        return efoDocRepository.countDocumentDataResultDtoByMyDocument(orgIds, posIds, accountId, searchDto, refTypes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.mbal.core.efo.service.EfoDocService#
     * countStatisticsDocByMyDocument(vn.com.unit.core.dto.
     * EfoOzDocStatisticsSearchDto, java.util.List, java.util.List, java.util.List)
     */
    @Override
    public EfoOzDocStatisticsDto countStatisticsDocByMyDocument(EfoOzDocStatisticsSearchDto searchDto,
            List<Long> posIds, List<Long> orgIds, List<String> refTypes) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        return efoDocRepository.countStatisticsDocByMyDocument(orgIds, posIds, accountId, searchDto, refTypes);
    }

    @Override
    public DocumentActionReq getDetailDocumentCurrentStep(Long docId, String stepCode, String buttonText) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoDocRepository.getDetailDocumentCurrentStep(docId, stepCode, buttonText, accountId, lang);
    }
}
