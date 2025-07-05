package vn.com.unit.cms.core.module.contract.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.admin.dto.ContractBusinessHistoryDto;

@Getter
@Setter
public class ContractBusinessHistoryResponseDto {

	public List<ContractBusinessHistoryDto> contractBusinessHistory;
	
	
}

