package vn.com.unit.cms.admin.all.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.cms.admin.all.dto.ContactCommentDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailEditDto;
//import vn.com.unit.cms.admin.all.dto.ContactEmailSearchDto;
import vn.com.unit.cms.admin.all.dto.ContactEmailUpdateItemDto;
import vn.com.unit.cms.core.module.contact.dto.ContactEmailSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.common.dto.SearchKeyDto;

public interface ContactEmailService {

	List<ContactEmailDto> getEmailList();

	ContactEmailDto getEmailDetail(Long emailId);

	List<SearchKeyDto> genSearchKeyList(Locale locale);

	PageWrapper<ContactEmailDto> getEmailList(ContactEmailSearchDto model, int page);
	
	ContactEmailDto updateEmailToProcessing(ContactEmailEditDto emailEditModel, Long emailId, Locale locale);
	
	ContactEmailDto updateEmailDone(ContactEmailEditDto emailEditModel, Long emailId, Locale locale);
	
	boolean deleteEmail(Long emailId);

	List<ContactEmailDto> getFullEmailList(ContactEmailSearchDto searchDto);

	List<ContactCommentDto> getCommentOptions();

	List<ContactEmailUpdateItemDto> getUpdateHistory(Long emailId);

	ContactEmailDto rejectEmail(ContactEmailEditDto emailEditModel, Long emailId, Locale locale);
}
