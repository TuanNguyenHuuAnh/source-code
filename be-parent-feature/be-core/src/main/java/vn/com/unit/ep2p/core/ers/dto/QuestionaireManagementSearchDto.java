// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionaireManagementSearchDto { // extends ErsAbstract

	private String typeQuestion;

	private String applyForPosition;

//	private String content;

//	private int orderOnForm;
//
	private String statusItem;

	private int deletedFlag;

}
