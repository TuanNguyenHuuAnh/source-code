package vn.com.unit.ep2p.adp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalProposalDto {
    private int totalReceived;
    private int totalPending;
    private int totalRelease;
    private int totalRejected;
}
