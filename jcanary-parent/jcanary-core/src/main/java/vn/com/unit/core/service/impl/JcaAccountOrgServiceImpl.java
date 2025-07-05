/*******************************************************************************
 * Class        :JcaAccountOrgServiceImpl
 * Created date :2020/12/16
 * Lasted date  :2020/12/16
 * Author       :SonND
 * Change log   :2020/12/16 01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.utils.CommonCollectionUtil;
import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.JcaAccountOrgDto;
import vn.com.unit.core.dto.JcaAccountOrgSearchDto;
import vn.com.unit.core.entity.JcaAccountOrg;
import vn.com.unit.core.repository.JcaAccountOrgRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountOrgService;

/**
 * JcaAccountOrgServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class JcaAccountOrgServiceImpl implements JcaAccountOrgService {

    @Autowired
    JcaAccountOrgRepository jcaAccountOrgRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Override
    public int countJcaAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto) {
        return jcaAccountOrgRepository.countJcaAccountOrgDtoByCondition(jcaAccountOrgSearchDto);
    }

    @Override
    public List<JcaAccountOrgDto> getJcaAccountOrgDtoByCondition(JcaAccountOrgSearchDto jcaAccountOrgSearchDto, Pageable pageable) {
        return jcaAccountOrgRepository.getJcaAccountOrgDtoByCondition(jcaAccountOrgSearchDto, pageable);
    }

    @Override
    public JcaAccountOrg getJcaAccountOrgById(Long id) {
        return jcaAccountOrgRepository.findOne(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JcaAccountOrg saveJcaAccountOrg(JcaAccountOrg jcaAccountOrg) {
//        Date sysDate = CommonDateUtil.getSystemDateTime();
//        Long id = jcaAccountOrg.getId();
//        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
//        if(null != id) {
//            JcaAccountOrg oldJcaAccountOrg =  jcaAccountOrgRepository.findOne(id);
//            if (null != oldJcaAccountOrg) {
//                jcaAccountOrg.setCreatedDate(oldJcaAccountOrg.getCreatedDate());
//                jcaAccountOrg.setCreatedId(oldJcaAccountOrg.getCreatedId());
//                jcaAccountOrg.setUpdatedDate(sysDate);
//                jcaAccountOrg.setUpdatedId(userId);
//                jcaAccountOrgRepository.update(jcaAccountOrg);
//            }
//            
//        }else {
//            jcaAccountOrg.setCreatedDate(sysDate);
//            jcaAccountOrg.setCreatedId(userId);
//            jcaAccountOrg.setUpdatedDate(sysDate);
//            jcaAccountOrg.setUpdatedId(userId);
//            jcaAccountOrgRepository.create(jcaAccountOrg);
//        }
        return jcaAccountOrg;
    }

    @Override
    public JcaAccountOrg saveJcaAccountOrgDto(JcaAccountOrgDto jcaAccountOrgDto) {
        JcaAccountOrg jcaAccountOrg = objectMapper.convertValue(jcaAccountOrgDto, JcaAccountOrg.class);
        //jcaAccountOrg.setId(jcaAccountOrgDto.getAccountOrgId());
        jcaAccountOrg.setAccountId(jcaAccountOrgDto.getUserId());
        // save data
        jcaAccountOrg = this.saveJcaAccountOrg(jcaAccountOrg);
        
        // update id
        //jcaAccountOrgDto.setAccountOrgId(jcaAccountOrg.getId());
        jcaAccountOrgDto.setUserId(jcaAccountOrg.getAccountId());
        
        return jcaAccountOrg;
    }

    @Override
    public JcaAccountOrgDto getJcaAccountOrgDtoById(Long id) {
        return jcaAccountOrgRepository.getJcaAccountOrgDtoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJcaAccountOrgById(Long id) {
        Long userId = UserProfileUtils.getUserPrincipal().getAccountId();
        Date sysDate = CommonDateUtil.getSystemDateTime();
        if(null != id) {
            JcaAccountOrg jcaAccountOrg =  jcaAccountOrgRepository.findOne(id);
            if (null != jcaAccountOrg) {
//                jcaAccountOrg.setDeletedDate(sysDate);
//                jcaAccountOrg.setDeletedId(userId);
                jcaAccountOrgRepository.update(jcaAccountOrg);
            }
        }
    }

    /* (non-Javadoc)
     * @see vn.com.unit.core.service.JcaAccountOrgService#getJcaAccountOrgDtoByAccountId(java.lang.Long)
     */
    @Override
    public JcaAccountOrgDto getJcaAccountOrgDtoByAccountId(Long accountId) {
        List<JcaAccountOrgDto> jcaAccountOrgDtos = jcaAccountOrgRepository.getJcaAccountOrgDtoByAccountId(accountId);
        if( CommonCollectionUtil.isNotEmpty(jcaAccountOrgDtos)) {
            return jcaAccountOrgDtos.stream().filter(item -> Boolean.TRUE.equals(item.getMainFlag())).findFirst().orElse(null);
        }
        return null;
    }
    
    @Override
    public List<JcaAccountOrgDto> getListJcaAccountOrgDtoByAccountId(Long accountId) {
        return jcaAccountOrgRepository.getJcaAccountOrgDtoByAccountId(accountId);
    }

    @Override
    public JcaAccountOrgDto getMainJcaAccountOrgDtoByAccountId(Long accountId) {
        return jcaAccountOrgRepository.getMainJcaAccountOrgDtoByAccountId(accountId);
    }
   
}
