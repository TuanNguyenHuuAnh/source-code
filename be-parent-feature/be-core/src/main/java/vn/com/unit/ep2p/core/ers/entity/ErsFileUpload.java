package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name="ERS_FILE_UPLOAD")
@Getter
@Setter
public class ErsFileUpload extends AbstractTracking {
	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_FILE_UPLOAD")
	@Column(name="ID")
	private Long id;
	
	@Column(name="CHANNEL")
	private String channel;
	
	@Column(name="CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name="FILE_UPLOAD_CODE")
	private String fileUploadCode;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="FILE_DISPLAY_ORDER")
	private Integer fileDisplayOrder;
	
	@Column(name="FILE_TYPE")
	private Integer fileType;
	
	
	public String getFileNameAndFileType() {
		return this.fileName+"_"+this.fileType;
	}

}
