package vn.com.unit.ep2p.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.cms.core.module.agentbank.entity.AgentBankUpdateHistory;
import vn.com.unit.common.dto.SelectItem;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.AgentBankDto;
import vn.com.unit.ep2p.admin.dto.BankInfoUpdateHistoryDto;

public interface ApiAgentBankService {
	AgentBankDto getAgentBankInfo(String agentCode) throws DetailException;
	List<SelectItem> getListBanks();
	List<SelectItem> getListBankBranchs(String bankCode);
	boolean sendOtp(String agentCode) throws Exception;
	String getOcrInfo(List<MultipartFile> documentList) throws Exception;
	AgentBankUpdateHistory updateBankInfo(AgentBankDto data, List<MultipartFile> listFile) throws Exception;
	List<BankInfoUpdateHistoryDto> getBankInfoUpdHistorys(String agentCode);
	ResponseEntity<byte[]> getDocument(BankInfoUpdateHistoryDto data) throws Exception;
	boolean sendMailSuccess(AgentBankDto data) throws Exception;
}
