/*******************************************************************************
 * Class        ：AttachedFileDto
 * Created date ：2019/02/20
 * Lasted date  ：2019/02/20
 * Author       ：VinhLT
 * Change log   ：2019/02/20：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.ep2p.core.entity.AttachedFile;

//import vn.com.unit.jcanary.entity.AttachedFile;

/**
 * AttachedFileDto
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public class AttachedFileDto {

	private String title;

	private String notes;

	private MultipartFile[] uploadingFiles;

	private String reference;

	private String businessCode;

	private List<AttachedFile> lstAttachedFile;

	private Long attachedFileId;

	/**
	 * Get title
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 * 
	 * @param title type String
	 * @return
	 * @author VinhLT
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get uploadingFiles
	 * 
	 * @return MultipartFile[]
	 * @author VinhLT
	 */
	public MultipartFile[] getUploadingFiles() {
		return uploadingFiles != null ? uploadingFiles.clone() : null;
	}

	/**
	 * Set uploadingFiles
	 * 
	 * @param uploadingFiles type MultipartFile[]
	 * @return
	 * @author VinhLT
	 */
	public void setUploadingFiles(MultipartFile[] uploadingFiles) {
		this.uploadingFiles = uploadingFiles != null ? uploadingFiles.clone() : null;
	}

	/**
	 * Get reference
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Set reference
	 * 
	 * @param reference type String
	 * @return
	 * @author VinhLT
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * Get businessCode
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getBusinessCode() {
		return businessCode;
	}

	/**
	 * Set businessCode
	 * 
	 * @param businessCode type String
	 * @return
	 * @author VinhLT
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * Get lstAttachedFile
	 * 
	 * @return List<AttachedFile>
	 * @author VinhLT
	 */
	protected List<AttachedFile> getLstAttachedFile() {
		return lstAttachedFile != null ? new ArrayList<AttachedFile>(lstAttachedFile) : null;
	}

	/**
	 * Set lstAttachedFile
	 * 
	 * @param lstAttachedFile type List<AttachedFile>
	 * @return
	 * @author VinhLT
	 */
	protected void setLstAttachedFile(List<AttachedFile> lstAttachedFile) {
		this.lstAttachedFile = lstAttachedFile != null ? new ArrayList<AttachedFile>(lstAttachedFile) : null;
	}

	/**
	 * Get attachedFileId
	 * 
	 * @return Long
	 * @author VinhLT
	 */
	public Long getAttachedFileId() {
		return attachedFileId;
	}

	/**
	 * Set attachedFileId
	 * 
	 * @param attachedFileId type Long
	 * @return
	 * @author VinhLT
	 */
	public void setAttachedFileId(Long attachedFileId) {
		this.attachedFileId = attachedFileId;
	}

	/**
	 * Get notes
	 * 
	 * @return String
	 * @author VinhLT
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Set notes
	 * 
	 * @param notes type String
	 * @return
	 * @author VinhLT
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
