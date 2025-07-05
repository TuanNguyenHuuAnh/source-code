/*******************************************************************************
 * Class        ：ReqDownloadDocumentAPI
 * Created date ：2019/08/01
 * Lasted date  ：2019/08/01
 * Author       ：KhuongTH
 * Change log   ：2019/08/01：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import javax.validation.constraints.NotNull;

/**
 * ReqDownloadDocumentAPI
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ReqDownloadDocumentAPI {

	private Long documentId;

	@NotNull
	private Long repositoryId;
	
	@NotNull
	private String pathFile;

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
	

}
