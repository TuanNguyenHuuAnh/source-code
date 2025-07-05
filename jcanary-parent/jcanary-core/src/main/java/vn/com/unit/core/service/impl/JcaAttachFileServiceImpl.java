/*******************************************************************************
 * Class        ：JcaAttachFileServiceImpl
 * Created date ：2021/02/01
 * Lasted date  ：2021/02/01
 * Author       ：TrieuVD
 * Change log   ：2021/02/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonObjectUtil;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.core.dto.JcaAttachFileDto;
import vn.com.unit.core.dto.JcaAttachFileSearchDto;
import vn.com.unit.core.entity.JcaAttachFile;
import vn.com.unit.core.repository.JcaAttachFileRepository;
import vn.com.unit.core.service.JcaAttachFileService;

/**
 * JcaAttachFileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAttachFileServiceImpl implements JcaAttachFileService {

    @Autowired
    private JcaAttachFileRepository jcaAttachFileRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#getJcaAttachFileDtoById(java.lang.Long)
     */
    @Override
    public JcaAttachFileDto getJcaAttachFileDtoById(Long id) {
        return jcaAttachFileRepository.getJcaAttachFileDtoById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#saveJcaAttachFileDto(vn.com.unit.core.dto.JcaAttachFileDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAttachFileDto saveJcaAttachFileDto(JcaAttachFileDto jcaAttachFileDto) {
        Long attachFileId = jcaAttachFileDto.getAttachFileId();
        Date sysDate = commonService.getSystemDate();
        Long userId = CoreConstant.SYSTEM_ID;
        JcaAttachFile attachFile = new JcaAttachFile();
        if (null != attachFileId) {
            attachFile = jcaAttachFileRepository.findOne(attachFileId);
            CommonObjectUtil.copyPropertiesNonNull(jcaAttachFileDto, attachFile);
            attachFile.setUpdatedId(userId);
            attachFile.setUpdatedDate(sysDate);
            jcaAttachFileRepository.update(attachFile);
        } else {
            attachFile = objectMapper.convertValue(jcaAttachFileDto, JcaAttachFile.class);
            attachFile.setCreatedId(userId);
            attachFile.setCreatedDate(sysDate);
            attachFile.setUpdatedId(userId);
            attachFile.setUpdatedDate(sysDate);
            jcaAttachFileRepository.create(attachFile);
        }
        jcaAttachFileDto.setAttachFileId(attachFile.getId());
        return jcaAttachFileDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#countBySearchCondition(vn.com.unit.core.dto.JcaAttachFileSearchDto)
     */
    @Override
    public int countBySearchCondition(JcaAttachFileSearchDto searchDto) {
        return jcaAttachFileRepository.countBySearchCondition(searchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#getJcaAttachFileDtoListByCondition(vn.com.unit.core.dto.JcaAttachFileSearchDto,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public List<JcaAttachFileDto> getJcaAttachFileDtoListByCondition(JcaAttachFileSearchDto searchDto, Pageable pageable) {
        return jcaAttachFileRepository.getJcaAttachFileDtoPageListByCondition(searchDto, pageable).getContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#updateReferenceId(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReferenceId(String referenceKey, Long referenceId) {
        JcaAttachFileSearchDto searchDto = new JcaAttachFileSearchDto();
        searchDto.setReferenceKey(referenceKey);
        List<JcaAttachFileDto> attachFileDtoList = jcaAttachFileRepository.getJcaAttachFileDtoListByCondition(searchDto);
        if (CommonCollectionUtil.isNotEmpty(attachFileDtoList)) {
            for (JcaAttachFileDto jcaAttachFileDto : attachFileDtoList) {
                jcaAttachFileDto.setReferenceId(referenceId);
                this.saveJcaAttachFileDto(jcaAttachFileDto);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#getJcaAttachFileDtoListByReferenceId(java.lang.String)
     */
    @Override
    public List<JcaAttachFileDto> getJcaAttachFileDtoListByReferenceId(Long referenceId, String attachType) {
        JcaAttachFileSearchDto searchDto = new JcaAttachFileSearchDto();
        searchDto.setReferenceId(referenceId);
        searchDto.setAttachType(attachType);
        return jcaAttachFileRepository.getJcaAttachFileDtoListByCondition(searchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#deleteJcaAttachFileById(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaAttachFileById(Long id) {
        JcaAttachFile jcaAttachFile = jcaAttachFileRepository.findOne(id);
        if (null != jcaAttachFile) {
            jcaAttachFileRepository.delete(jcaAttachFile);
        }
    }

}
