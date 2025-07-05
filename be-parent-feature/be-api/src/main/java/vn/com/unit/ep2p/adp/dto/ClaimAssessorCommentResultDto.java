package vn.com.unit.ep2p.adp.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClaimAssessorCommentResultDto {
	public String assessorComment;
    public String documentType;
    public Date requestDate;
}
