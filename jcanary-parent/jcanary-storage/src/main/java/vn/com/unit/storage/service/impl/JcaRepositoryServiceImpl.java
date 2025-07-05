/*******************************************************************************
 * Class        ：JcaRepositoryServiceImpl
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.constant.CommonConstant;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonPasswordUtil;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.storage.dto.JcaRepositoryDto;
import vn.com.unit.storage.dto.JcaRepositorySearchDto;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.repository.JcaRepositoryRepository;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * JcaRepositoryServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaRepositoryServiceImpl implements JcaRepositoryService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JcaRepositoryRepository jcaRepositoryRepository;

    @Autowired
    private JCommonService commonService;

    private static final Long USER_lOGIN_ID = 1L;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRepository saveJcaRepositoryDto(JcaRepositoryDto objectDto) throws DetailException {
        JcaRepository entity = objectMapper.convertValue(objectDto, JcaRepository.class);
        String password = null;
        try {
            String encryptedString = objectDto.getPassword();
            if(CommonStringUtil.isNotBlank(encryptedString)) {
                // encrypt password
                password = CommonPasswordUtil.encryptString(encryptedString);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DetailException("Encrypt password fail.");
        }
        entity.setPassword(password);
        return this.saveJcaRepository(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaRepository saveJcaRepository(JcaRepository objectEntity) {
        Date sysDate = commonService.getSystemDate();
        Long id = objectEntity.getId();

        if (null != id) {
            JcaRepository oldJcaRepository = this.getJcaRepositoryById(id);
            if (null != oldJcaRepository) {
                objectEntity.setCreatedDate(oldJcaRepository.getCreatedDate());
                objectEntity.setCreatedId(oldJcaRepository.getCreatedId());
                objectEntity.setUpdatedDate(sysDate);
                objectEntity.setUpdatedId(USER_lOGIN_ID);
                jcaRepositoryRepository.update(objectEntity);
            }
        } else {
            objectEntity.setCreatedDate(sysDate);
            objectEntity.setCreatedId(USER_lOGIN_ID);
            objectEntity.setUpdatedDate(sysDate);
            objectEntity.setUpdatedId(USER_lOGIN_ID);
            jcaRepositoryRepository.create(objectEntity);
        }
        return objectEntity;
    }

    @Override
    public JcaRepository getJcaRepositoryById(Long id) {
        return jcaRepositoryRepository.findOne(id);
    }

    @Override
    public JcaRepositoryDto getJcaRepositoryDtoById(Long id) {

        JcaRepositoryDto dto = null;
        JcaRepository fileRepository = this.getJcaRepositoryById(id);
        if (null != fileRepository) {
            dto = objectMapper.convertValue(fileRepository, JcaRepositoryDto.class);
            // decrypt password
            String encryptedPassword = dto.getPassword();
            if(CommonStringUtil.isNotBlank(encryptedPassword)) {
                String password = CommonPasswordUtil.decryptString(encryptedPassword);
                dto.setPassword(password);
            }
        }
        return dto;
    }

    @Override
    public int countListJcaRepositoryByCondition(JcaRepositorySearchDto jcaRepositorySearchDto) {
        return jcaRepositoryRepository.countListJcaRepositoryByCondition(jcaRepositorySearchDto);
    }

    @Override
    public List<JcaRepositoryDto> getListJcaRepositoryDtoByCondition(JcaRepositorySearchDto jcaRepositorySearchDto, Pageable pageable) {
        long offset = pageable.getOffset();
        int pageSize = pageable.getPageSize();
        return jcaRepositoryRepository.getListJcaRepositoryDtoByCondition(jcaRepositorySearchDto, offset, pageSize);
    }

    @Override
    public boolean checkGroupCodeExists(String code, Long companyId) {
        return jcaRepositoryRepository.countJcaRepositoryByCode(code, companyId) > 0;
    }
    @Override
    public List<JcaRepositoryDto> getListJcaRepositoryDtoByParam(JcaRepositorySearchDto searchDto) {
    	return jcaRepositoryRepository.getListJcaRepositoryDtoByParam(searchDto);
    }

    @Override
    public JcaRepositoryDto getJcaRepositoryDto(String code, Long companyId) {
        if (companyId == null) {
            companyId = CommonConstant.COMPANY_DEFAULT;
        }
        return jcaRepositoryRepository.findJcaRepositoryByCode(code, companyId);
    }

}
