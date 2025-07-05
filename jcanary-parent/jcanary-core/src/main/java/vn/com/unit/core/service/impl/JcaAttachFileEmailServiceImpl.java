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
import vn.com.unit.core.dto.JcaAttachFileEmailDto;
import vn.com.unit.core.dto.JcaAttachFileEmailSearchDto;
import vn.com.unit.core.entity.JcaAttachFileEmail;
import vn.com.unit.core.repository.JcaAttachFileEmailRepository;
import vn.com.unit.core.service.JcaAttachFileEmailService;

/**
 * JcaAttachFileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAttachFileEmailServiceImpl implements JcaAttachFileEmailService {

    @Autowired
    private JcaAttachFileEmailRepository jcaAttachFileEmailRepository;

    @Autowired
    private JCommonService commonService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#getJcaAttachFileEmailDtoById(java.lang.Long)
     */
    @Override
    public JcaAttachFileEmailDto getJcaAttachFileEmailDtoById(Long id) {
        return jcaAttachFileEmailRepository.getJcaAttachFileEmailDtoById(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#saveJcaAttachFileEmailDto(vn.com.unit.core.dto.JcaAttachFileEmailDto)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAttachFileEmailDto saveJcaAttachFileEmailDto(JcaAttachFileEmailDto JcaAttachFileEmailDto) {
        Long attachFileId = JcaAttachFileEmailDto.getAttachFileId();
        Date sysDate = commonService.getSystemDate();
        Long userId = CoreConstant.SYSTEM_ID;
        JcaAttachFileEmail attachFile = new JcaAttachFileEmail();
        if (null != attachFileId) {
            attachFile = jcaAttachFileEmailRepository.findOne(attachFileId);
            CommonObjectUtil.copyPropertiesNonNull(JcaAttachFileEmailDto, attachFile);
            attachFile.setUpdatedId(userId);
            attachFile.setUpdatedDate(sysDate);
            jcaAttachFileEmailRepository.update(attachFile);
        } else {
            attachFile = objectMapper.convertValue(JcaAttachFileEmailDto, JcaAttachFileEmail.class);
            attachFile.setCreatedId(userId);
            attachFile.setCreatedDate(sysDate);
            attachFile.setUpdatedId(userId);
            attachFile.setUpdatedDate(sysDate);
            jcaAttachFileEmailRepository.create(attachFile);
        }
        JcaAttachFileEmailDto.setAttachFileId(attachFile.getId());
        return JcaAttachFileEmailDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#countBySearchCondition(vn.com.unit.core.dto.JcaAttachFileEmailSearchDto)
     */
    @Override
    public int countBySearchCondition(JcaAttachFileEmailSearchDto searchDto) {
        return jcaAttachFileEmailRepository.countBySearchCondition(searchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#getJcaAttachFileEmailDtoListByCondition(vn.com.unit.core.dto.JcaAttachFileEmailSearchDto,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public List<JcaAttachFileEmailDto> getJcaAttachFileEmailDtoListByCondition(JcaAttachFileEmailSearchDto searchDto, Pageable pageable) {
        return jcaAttachFileEmailRepository.getJcaAttachFileEmailDtoPageListByCondition(searchDto, pageable).getContent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#updateReferenceId(java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateReferenceId(String referenceKey, Long referenceId) {
        JcaAttachFileEmailSearchDto searchDto = new JcaAttachFileEmailSearchDto();
        searchDto.setReferenceKey(referenceKey);
        List<JcaAttachFileEmailDto> attachFileDtoList = jcaAttachFileEmailRepository.getJcaAttachFileEmailDtoListByCondition(searchDto);
        if (CommonCollectionUtil.isNotEmpty(attachFileDtoList)) {
            for (JcaAttachFileEmailDto JcaAttachFileEmailDto : attachFileDtoList) {
                JcaAttachFileEmailDto.setReferenceId(referenceId);
                this.saveJcaAttachFileEmailDto(JcaAttachFileEmailDto);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#getJcaAttachFileEmailDtoListByReferenceId(java.lang.String)
     */
    @Override
    public List<JcaAttachFileEmailDto> getJcaAttachFileEmailDtoListByReferenceId(Long referenceId, String attachType) {
        JcaAttachFileEmailSearchDto searchDto = new JcaAttachFileEmailSearchDto();
        searchDto.setReferenceId(referenceId);
        searchDto.setAttachType(attachType);
        return jcaAttachFileEmailRepository.getJcaAttachFileEmailDtoListByCondition(searchDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.core.service.JcaAttachFileService#deleteJcaAttachFileById(java.lang.Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaAttachFileById(Long id) {
        JcaAttachFileEmail jcaAttachFile = jcaAttachFileEmailRepository.findOne(id);
        if (null != jcaAttachFile) {
            jcaAttachFileEmailRepository.delete(jcaAttachFile);
        }
    }

}
