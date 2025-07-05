// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "ERS_QUESTION_INTERVIEW")
public class ErsQuestionInterview {

	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_ERS_QUESTION_INTERVIEW")
	@Column(name = "ID")
	private Long id;

//	private Long no;

	private String typeQuestion;

	private String applyForPosition;

	private String content;

	private int orderOnForm;

	private String statusItem;

	private String createdBy;

	private Date createdDate;

	private String updatedBy;

	private Date updatedDate;

	private String deletedBy;

	private Date deletedDate;

	private int deletedFlag;

}
