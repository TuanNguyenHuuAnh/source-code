package vn.com.unit.ep2p.adp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.cms.core.module.contactHistory.entity.ContactHistory;
import vn.com.unit.cms.core.module.contactHistory.repository.ContactHistoryRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.db.service.SqlManagerDb2Service;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.adp.dto.ContactHistoryDto;
import vn.com.unit.ep2p.core.service.AbstractCommonService;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ContactHistoryServiceImpl extends AbstractCommonService implements ContactHistoryService {

	@Autowired
    ContactHistoryRepository contactHistoryRepository;
	
	@Autowired
	@Qualifier("sqlManageDb2Service")
	private SqlManagerDb2Service sqlManagerDb2Service;
	
	@Override
	public ContactHistoryDto addContactHistory(ContactHistoryDto dto) {
		ContactHistory entity = new ContactHistory();
		entity.setPolicyNo(dto.getPolicyNo());
		entity.setDueDate(dto.getDueDate());
		entity.setContactStage(dto.getContactStage());
		entity.setContactStageDays(dto.getContactStageDays());
		entity.setContactMethod(dto.getContactMethod());
		entity.setContactResult(dto.getContactResult());
		entity.setContactDate(dto.getContactDate());
		entity.setCreatedBy(UserProfileUtils.getFaceMask());
		entity.setCreatedDate(new Date());
		entity.setNotes(dto.getNotes());
		
		contactHistoryRepository.save(entity);
		dto.setId(entity.getId());
		
		return dto;
	}
	
	@Override
	public ContactHistoryDto updateContactHistory(ContactHistoryDto dto) throws DetailException {		
		ContactHistory entity = contactHistoryRepository.findOne(dto.getId());
		entity.setContactMethod(dto.getContactMethod());
		entity.setContactResult(dto.getContactResult());
		entity.setContactDate(dto.getContactDate());
		entity.setUpdatedBy(UserProfileUtils.getFaceMask());
		entity.setUpdatedDate(new Date());
		entity.setNotes(dto.getNotes());
		
		contactHistoryRepository.save(entity);
		
		return dto;
	}
}
