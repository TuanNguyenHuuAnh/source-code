package vn.com.unit.ep2p.adp.service;

import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.adp.dto.ContactHistoryDto;

public interface ContactHistoryService {	
	public ContactHistoryDto addContactHistory(ContactHistoryDto dto);
	public ContactHistoryDto updateContactHistory(ContactHistoryDto dto) throws DetailException;
}
