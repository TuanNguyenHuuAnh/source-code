/*******************************************************************************
 * Class        ：ResDownloadDocumentDto
 * Created date ：2019/08/01
 * Lasted date  ：2019/08/01
 * Author       ：KhuongTH
 * Change log   ：2019/08/01：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * ResDownloadDocumentDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class ResDownloadDocumentDto {

	private Long documentId;
	
    private byte[] file;

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
        
}
