package vn.com.unit.ep2p.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import vn.com.unit.cms.core.module.contact.dto.CmsContactDto;

public interface CmsContactService {

	CmsContactDto create(CmsContactDto dto, HttpServletRequest request) throws IOException;

}
