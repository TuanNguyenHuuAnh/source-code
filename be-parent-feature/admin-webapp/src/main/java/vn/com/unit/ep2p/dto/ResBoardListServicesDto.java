package vn.com.unit.ep2p.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * SvcBoardListServicesDto
 * @author TaiTT
 * 9-7-2019
 */

public class ResBoardListServicesDto {

	 private Long  serviceId;
	 
	 private String serviceName;
	 
	 private String serviceType;
	 
	 private String pathFile;
	 
	 private String imagePath;
	 
	 private Long imageRepoId;
//	 
//	 private Long templateRepoId;
//	 
//	 private Long templateId;
	 
	 private String businessProcessName;
	 
	 private Long businessProcessId;
	 
	 @JsonInclude(Include.NON_NULL)
	 private Long totalDocuments;
	 
	 private boolean allowCreate;
	 
	 private String integUrl;
	 
	 @JsonInclude(Include.NON_NULL)
	 private String processType;


    /**
     * Get serviceName
     * @return String
     * @author taitt
     */
    public String getServiceName() {
        return serviceName;
    }

    
    /**
     * Set serviceName
     * @param   serviceName
     *          type String
     * @return
     * @author  taitt
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    

    
    /**
     * Get imagePath
     * @return String
     * @author taitt
     */
    public String getImagePath() {
        return imagePath;
    }

    
    /**
     * Set imagePath
     * @param   imagePath
     *          type String
     * @return
     * @author  taitt
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    
    /**
     * Get imageRepoId
     * @return Long
     * @author taitt
     */
    public Long getImageRepoId() {
        return imageRepoId;
    }

    
    /**
     * Set imageRepoId
     * @param   imageRepoId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setImageRepoId(Long imageRepoId) {
        this.imageRepoId = imageRepoId;
    }


    
    public String getPathFile() {
        return pathFile;
    }


    
    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

//
//    
//    public Long getTemplateRepoId() {
//        return templateRepoId;
//    }
//
//
//    
//    public void setTemplateRepoId(Long templateRepoId) {
//        this.templateRepoId = templateRepoId;
//    }
//
//
//    
//    public Long getTemplateId() {
//        return templateId;
//    }
//
//
//    
//    public void setTemplateId(Long templateId) {
//        this.templateId = templateId;
//    }


    
    public Long getTotalDocuments() {
        return totalDocuments;
    }


    
    public void setTotalDocuments(Long totalDocuments) {
        this.totalDocuments = totalDocuments;
    }


    
    public boolean isAllowCreate() {
        return allowCreate;
    }


    
    public void setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
    }


    
    /**
     * Get serviceId
     * @return Long
     * @author taitt
     */
    public Long getServiceId() {
        return serviceId;
    }


    
    /**
     * Set serviceId
     * @param   serviceId
     *          type Long
     * @return
     * @author  taitt
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }


	/**
	 * Get businessProcessName
	 * @return String
	 * @author taitt
	 */
	public String getBusinessProcessName() {
		return businessProcessName;
	}


	/**
	 * Set businessProcessName
	 * @param   businessProcessName
	 *          type String
	 * @return
	 * @author  taitt
	 */
	public void setBusinessProcessName(String businessProcessName) {
		this.businessProcessName = businessProcessName;
	}


	/**
	 * Get businessProcessId
	 * @return Long
	 * @author taitt
	 */
	public Long getBusinessProcessId() {
		return businessProcessId;
	}


	/**
	 * Set businessProcessId
	 * @param   businessProcessId
	 *          type Long
	 * @return
	 * @author  taitt
	 */
	public void setBusinessProcessId(Long businessProcessId) {
		this.businessProcessId = businessProcessId;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getIntegUrl() {
		return integUrl;
	}


	public void setIntegUrl(String integUrl) {
		this.integUrl = integUrl;
	}


	public String getProcessType() {
		return processType;
	}


	public void setProcessType(String processType) {
		this.processType = processType;
	}

	 
	
}
