package vn.com.unit.cms.core.module.agent.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LetterAgentDto {
//	private Long id;
	private Integer no;
//	private String letterCategory;//loai thu
	private Date releaseDate;//ngay xuat ban
//	private String oldAgentType;//vi tri cu
//	private String newAgentType;//vi tri moi
//	private String linkLetter;
	private boolean share;
	private boolean download;
	private String agentCode; 			// MA_DAI_LY
	private String letterCategory; 		// MA_LOAI_THU
	private String letterCategoryName;	// TEN_THU
	private Date createdDate;			// NGAY_PHAT_HANH
	private Date effectiveDate;			// NGAY_HIEU_LUC
	private Date endDate;				// NGAY_HET_HAN
	private String oldAgentType;		// CHUC_VU_CU
	private String newAgentType;		// CHUC_VU_MOI
	private String soHdbh;				// SO_HDBH
	private String insuranceBuyName;		// TEN_BEN_MUA_BAO_HIEM
	private String formOfDistribution;	// HINH_THUC_PHAN_CONG
	private String nameFileLetter;		// TEN_FILE_THU
	private String movementType;		
	private Long id;
	private String type;
	private String templateType;
	private String docId;
	private String tenThu;
}
