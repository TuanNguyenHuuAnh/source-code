package vn.com.unit.ep2p.admin.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import vn.com.unit.ep2p.admin.dto.ConfirmDecreeDto;
import vn.com.unit.ep2p.admin.dto.GADOfficeDto;
import vn.com.unit.ep2p.admin.repository.ConfirmDecreeRepository;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.admin.service.ScannerUpdateDBService;
import vn.com.unit.ep2p.core.utils.RetrofitUtils;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ScannerUpdateDBServiceImpl implements ScannerUpdateDBService {
		
    @Autowired
    Db2ApiService db2ApiService;

        @Autowired
    ConfirmDecreeRepository confirmDecreeRepository;

	private Logger logger = LoggerFactory.getLogger(getClass());    
	
	@Override
	public void updateCandiateInOfficeInactive() {
		
		// GET ODS DATA
		GADOfficeDto officeDto = db2ApiService.getOfficeInActive();
		if(officeDto.getOfficeCodes() != null) {			
			try {
				RetrofitUtils.updateCandidateInOfficeInActive(officeDto.getOfficeCodes());
			} catch (Exception ex) {
				logger.error("##updateCandiateInOfficeInactive##", ex.getMessage());
			}	
		}
		
	}
    	
	@Override
	public void updateAgentTERConfirmedDecree() {
		// GET ODS DATA
		List<ConfirmDecreeDto> listDecree = db2ApiService.getDecreeOfAgentTerminate();
		if(CollectionUtils.isEmpty(listDecree)) { return; }
		// update confirm decree by  username
        for (ConfirmDecreeDto decree : listDecree) {
        	confirmDecreeRepository.updateConfirmDecreeByUserName(decree.getUserName());
        }
	}
	
	@Override
	public void updateCandidateOverDueToExpired() {
		try {
			RetrofitUtils.updateCandidateOverDueToExpired();
		} catch (Exception ex) {
			logger.error("##updateCandidateOverDueToExpired##", ex.getMessage());
		}	
	}

}
