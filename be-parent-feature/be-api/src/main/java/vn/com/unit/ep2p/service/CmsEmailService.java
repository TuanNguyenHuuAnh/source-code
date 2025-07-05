package vn.com.unit.ep2p.service;

import java.io.IOException;

import vn.com.unit.common.dto.EmailResultDto;

public interface CmsEmailService {
	public EmailResultDto sendMailOtp(String agentCode, String email) throws IOException;
}
