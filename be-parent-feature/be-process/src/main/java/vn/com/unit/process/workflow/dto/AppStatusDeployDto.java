package vn.com.unit.process.workflow.dto;

import java.util.List;

import vn.com.unit.workflow.dto.JpmStatusLangDeployDto;

public class AppStatusDeployDto {
    private Long id;
    private Long processId;
    private String statusCode;
    private String statusName;
    private List<JpmStatusLangDeployDto> listJpmStatusLang;
    private boolean isSystem;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<JpmStatusLangDeployDto> getListJpmStatusLang() {
        return listJpmStatusLang;
    }

    public void setListJpmStatusLang(List<JpmStatusLangDeployDto> listJpmStatusLang) {
        this.listJpmStatusLang = listJpmStatusLang;
    }
    
    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    @Override
    public int hashCode() {
    	int hashCode = 0;
    	
    	if( id != null ) {
    		hashCode = id.hashCode();
    	}
    	
    	if( processId != null ) {
    		hashCode = hashCode + processId.hashCode();
    	}
    	
    	if( statusCode != null ) {
    		hashCode = hashCode + statusCode.hashCode();
    	}
    	
    	return hashCode;
    }
    
    @Override
    public boolean equals(Object obj) {
    	boolean result = true;
    	
//    	if( obj != null && obj instanceof AppStatusDto ) {
//    		AppStatusDto appStatusDto = (AppStatusDto) obj;
//    		
//    		boolean checkId = false;
//    		if( id != null ) {
//    			checkId = id.equals(appStatusDto.getId());
//    		}
//    		
//    		boolean checkProcessId = false;
//    		if( processId != null && checkId == true ) {
//    			checkProcessId = processId.equals(appStatusDto.getProcessId());
//    		}
//    		
//    		if( statusCode != null && checkId == true && checkProcessId == true ) {
//    			result = statusCode.equals(appStatusDto.getStatusCode());
//    		}
//    	} else {
//    		result = false;
//    	}
    	
    	return result;
    }
    
    @Override
    public String toString() {
    	String result = "id: " + id;
    	result = result + " processId: " + processId;
    	result = result + " statusCode: " + statusCode;
    	result = result + " statusName: " + statusName;
    	return result;
    }
}
