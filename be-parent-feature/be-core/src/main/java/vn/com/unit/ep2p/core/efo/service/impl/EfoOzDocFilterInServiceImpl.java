/*******************************************************************************
 * Class        ：EfoOzDocFilterInServiceImpl
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

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.core.dto.DocumentDataResultDto;
import vn.com.unit.core.dto.EfoDocumentFilterSearchDto;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterIn;
import vn.com.unit.ep2p.core.efo.repository.EfoOzDocFilterInRepository;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterInService;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterRefService;
import vn.com.unit.ep2p.core.utils.ChannelUtils;

/**
 * EfoOzDocFilterInServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoOzDocFilterInServiceImpl implements EfoOzDocFilterInService{

    @Autowired
    EfoOzDocFilterInRepository efoDocFilterInRepository;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    EfoOzDocFilterRefService efoOzDocFilterRefService;
    
    @Autowired
    JCommonService commonService;
    
    @Override
    @Transactional
    public void saveDocFilterIn(EfoOzDocFilterIn efoOzDocFilterIn) {
        Date sysDate = commonService.getSystemDate();
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        
        Long id = efoOzDocFilterIn.getId();
        if(null != id) {
            EfoOzDocFilterIn oldEfoOzDocFilterIn =  efoDocFilterInRepository.findOne(id);
            if (null !=oldEfoOzDocFilterIn) {
                efoOzDocFilterIn.setCreatedDate(oldEfoOzDocFilterIn.getCreatedDate());
                efoOzDocFilterIn.setCreatedId(oldEfoOzDocFilterIn.getCreatedId());
                efoOzDocFilterIn.setUpdatedDate(sysDate);
                efoOzDocFilterIn.setUpdatedId(accountId);
                efoDocFilterInRepository.update(efoOzDocFilterIn);
            }
            
        }else {
            efoOzDocFilterIn.setCreatedDate(sysDate);
            efoOzDocFilterIn.setCreatedId(accountId);
            efoDocFilterInRepository.create(efoOzDocFilterIn);
        }
    }
    
    @Override
    @Transactional
    public void deleteRefInByJpmTaskId(Long jpmTaskId, Long docId) {
        efoDocFilterInRepository.deleteRefInByJpmTaskId(jpmTaskId, docId);
    }
    
    @Override
    public List<EfoOzDocFilterIn> getListEfoOzDocFilterInByJpmTaskId(Long jpmTaskId){
        return efoDocFilterInRepository.getListEfoOzDocFilterInByJpmTaskId(jpmTaskId);
    }
    
    @Override
    public List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, Pageable pagable) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoDocFilterInRepository.getDocumentDataResultDtoByFilterIn(accountId,searchDto,pagable,lang).getContent();
    }
    
    @Override
    public int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        searchDto.setChannel(ChannelUtils.getUserChannel());

        return efoDocFilterInRepository.countDocumentDataResultDtoByFilterIn(accountId, searchDto);
    }
}
