package vn.com.unit.ep2p.dto;

import java.util.List;

public class PPLStepSurveyGroup {
	
	private String stepName;
	
	private Long stepId;
	
	List<PPLJpmStepSurveyDto> surveys;

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public List<PPLJpmStepSurveyDto> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<PPLJpmStepSurveyDto> surveys) {
		this.surveys = surveys;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

}
