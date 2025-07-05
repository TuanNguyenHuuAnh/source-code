/*******************************************************************************
 * Class        ：EfoOzDocFilterOutServiceImpl
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
import vn.com.unit.ep2p.core.efo.entity.EfoOzDocFilterOut;
import vn.com.unit.ep2p.core.efo.repository.EfoOzDocFilterOutRepository;
import vn.com.unit.ep2p.core.efo.service.EfoOzDocFilterOutService;
import vn.com.unit.ep2p.core.utils.ChannelUtils;

/**
 * EfoOzDocFilterOutServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EfoOzDocFilterOutServiceImpl implements EfoOzDocFilterOutService{

    @Autowired
    private EfoOzDocFilterOutRepository efoOzDocFilterOutRepository;
    
    @Override
    @Transactional
    public void saveDocFilterOut(EfoOzDocFilterOut efoOzDocFilterOut) {   
        Date sysDate = CommonDateUtil.getSystemDateTime();
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        Long id = efoOzDocFilterOut.getId();
        if(null != id) {
            EfoOzDocFilterOut oldEfoOzDocFilterOut =  efoOzDocFilterOutRepository.findOne(id);
            if (null !=oldEfoOzDocFilterOut) {
                efoOzDocFilterOut.setCreatedDate(oldEfoOzDocFilterOut.getCreatedDate());
                efoOzDocFilterOut.setCreatedId(oldEfoOzDocFilterOut.getCreatedId());
                efoOzDocFilterOut.setUpdatedDate(sysDate);
                efoOzDocFilterOut.setUpdatedId(accountId);
                efoOzDocFilterOutRepository.update(efoOzDocFilterOut);
            }
            
        }else {
            efoOzDocFilterOut.setCreatedDate(sysDate);
            efoOzDocFilterOut.setCreatedId(accountId);
            efoOzDocFilterOutRepository.create(efoOzDocFilterOut);
        }
    }

	@Override
    public EfoOzDocFilterOut getEfoOzDocFilterOutByTaskId(Long taskId) {
    	return efoOzDocFilterOutRepository.getEfoOzDocFilterOutByTaskId(taskId);
    }
    
    
    
    @Override
    public List<DocumentDataResultDto> getDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto, Pageable pagable) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String lang = UserProfileUtils.getLanguage();
        return efoOzDocFilterOutRepository.getDocumentDataResultDtoByFilterOut(accountId,searchDto,pagable,lang).getContent();
    }

    @Override
    public int countDocumentDataResultDto(EfoDocumentFilterSearchDto searchDto) {
        Long accountId = UserProfileUtils.getUserPrincipal().getAccountId();
        String actionUser = UserProfileUtils.getUserPrincipal().getUsername();
        searchDto.setActionUser(actionUser);
        searchDto.setChannel(ChannelUtils.getUserChannel());

        return efoOzDocFilterOutRepository.countDocumentDataResultDtoByFilterOut(accountId, searchDto);
    }
}
