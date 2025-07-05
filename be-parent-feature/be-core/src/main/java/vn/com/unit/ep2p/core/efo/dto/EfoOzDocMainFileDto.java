/*******************************************************************************
 * Class        ：OZDocMainFileDto
 * Created date ：2019/08/07
 * Lasted date  ：2019/08/07
 * Author       ：NhanNV
 * Change log   ：2019/08/07：01-00 NhanNV create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * OZDocMainFileDto
 * 
 * @version 01-00
 * @since 01-00
 * @author NhanNV
 */
@Getter
@Setter
public class EfoOzDocMainFileDto {

	private Long id;
	private Long docId;
	private Long ozDocId;
	private Long formId;
	private String mainFileName;
	private String mainFileNameView;
	private Long mainFileRepoId;
	private String mainFilePath;
	private String ozInputJson;
	private String ozValidJson;
	private String ozAppendFilePath;
	private Long majorVersion;
	private Long minorVersion;
	private Long pdfMajorVersion;
	private Long pdfMinorVersion;
	private Long pdfRepositoryId;
	private String pdfFilePath;
	private String pdfTypeSize;   
    private Long docIdTmp;   
    private String docCode;

    /** file ozd */
    private List<String> fileStream;
    private List<String> fileStreamName;
    
    private Long companyId;

}
