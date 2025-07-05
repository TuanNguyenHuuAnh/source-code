package vn.com.unit.ep2p.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentAllowExportTaxCommitmentDto {
    private String agentCode; // null if not allow
    private String taxCode;
}
