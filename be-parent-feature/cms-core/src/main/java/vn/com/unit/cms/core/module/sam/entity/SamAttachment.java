package vn.com.unit.cms.core.module.sam.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "SAM_ATTACHMENT")
@Getter
@Setter
public class SamAttachment {

	@Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "ACT_ID")
	private Long actId;
	
	@Column(name = "DETAIL_NO")
	private Integer detailNo;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "ATTACHMENT")
	private String attachment;
	
	@Column(name = "ATTACHMENT_PHYSICAL")
    private String attachmentPhysical;
	
}
