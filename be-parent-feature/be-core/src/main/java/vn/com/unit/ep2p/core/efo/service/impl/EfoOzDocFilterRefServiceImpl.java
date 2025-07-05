/*******************************************************************************
 * Class        ：EfoOzDocFilterRefServiceImpl
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：taitt
 * Change log   ：2021/01/13：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterRef;
import vn.com.unit.ep2p.core.efo.repository.EfoOzDocFilterRefRepository;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterRefService;

/**
 * EfoOzDocFilterRefServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoOzDocFilterRefServiceImpl implements EfoOzDocFilterRefService{

    @Autowired
    private EfoOzDocFilterRefRepository efoOzDocFilterRefRepository;
    
    @Override
    @Transactional
    public void saveDocFilterRef(EfoOzDocFilterRef efoOzDocFilterRef) {
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        
        Long id = efoOzDocFilterRef.getId();
        if(null != id) {
            EfoOzDocFilterRef oldEfoOzDocFilterRef =  efoOzDocFilterRefRepository.findOne(id);
            if (null !=oldEfoOzDocFilterRef) {
                efoOzDocFilterRef.setCreatedDate(oldEfoOzDocFilterRef.getCreatedDate());
                efoOzDocFilterRef.setCreatedId(oldEfoOzDocFilterRef.getCreatedId());
                efoOzDocFilterRef.setUpdatedDate(sysDate);
                efoOzDocFilterRef.setUpdatedId(accountId);
                efoOzDocFilterRefRepository.update(efoOzDocFilterRef);
            }
            
        }else {
            efoOzDocFilterRef.setCreatedDate(sysDate);
            efoOzDocFilterRef.setCreatedId(accountId);
            efoOzDocFilterRefRepository.create(efoOzDocFilterRef);
        }
    }
    
    @Override
    public List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, Pageable pagable, List<String> refTypes) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoOzDocFilterRefRepository.getDocumentDataResultDtoByFilterRef(accountId,refTypes,searchDto,pagable,lang).getContent();
    }
    
    @Override
    public int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, List<String> refTypes) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        return efoOzDocFilterRefRepository.countDocumentDataResultDtoByFilterRef(accountId,refTypes,searchDto);
    }
}
