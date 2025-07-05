/*******************************************************************************
 * Class        ：EfoOzDocMainFileVersion
 * Created date ：2019/08/07
 * Lasted date  ：2019/08/07
 * Author       ：KhuongTH
 * Change log   ：2019/08/07：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.efo.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * EfoOzDocMainFileVersion
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@Setter
@Table(name = CoreConstant.TABLE_EFO_OZ_DOC_MAIN_FILE_VERSION)
public class EfoOzDocMainFileVersion extends AbstractTracking {

    /** Column: ID type VARCHAR2(18) NOT NULL */
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ + CoreConstant.TABLE_EFO_OZ_DOC_MAIN_FILE_VERSION)
    private Long id;

	/** Column: DOC_ID type NUMBER(20,0) null */
	@Column(name = "DOC_ID")
	private Long docId;

	
	/** Column: OZ_DOC_ID type NUMBER(20,0) null */
	@Column(name = "OZ_DOC_ID")
	private Long ozDocId;

	
	/** Column: FORM_ID type NUMBER(20,0) null */
	@Column(name = "FORM_ID")
	private Long formId;

	
	/** Column: MAIN_FILE_NAME type NVARCHAR2(255,0) null */
	@Column(name = "MAIN_FILE_NAME")
	private String mainFileName;

	
	/** Column: MAIN_FILE_NAME_VIEW type NVARCHAR2(255,0) null */
	@Column(name = "MAIN_FILE_NAME_VIEW")
	private String mainFileNameView;

	
	/** Column: MAIN_FILE_REPO_ID type NUMBER(20,0) null */
	@Column(name = "MAIN_FILE_REPO_ID")
	private Long mainFileRepoId;

	
	/** Column: MAIN_FILE_PATH type VARCHAR2(255,0) null */
	@Column(name = "MAIN_FILE_PATH")
	private String mainFilePath;

	
	/** Column: OZ_INPUT_JSON type NCLOBnull */
	@Column(name = "OZ_INPUT_JSON")
	private String ozInputJson;

	
	/** Column: OZ_VALID_JSON type NCLOBnull */
	@Column(name = "OZ_VALID_JSON")
	private String ozValidJson;

	
	/** Column: OZ_APPEND_FILE_PATH type VARCHAR2(255,0) null */
	@Column(name = "OZ_APPEND_FILE_PATH")
	private String ozAppendFilePath;

	
	/** Column: MAJOR_VERSION type NUMBER(20,0) null */
	@Column(name = "MAJOR_VERSION")
	private Long majorVersion;

	
	/** Column: MINOR_VERSION type NUMBER(20,0) null */
	@Column(name = "MINOR_VERSION")
	private Long minorVersion;

	
	/** Column: PDF_MAJOR_VERSION type NUMBER(20,0) null */
	@Column(name = "PDF_MAJOR_VERSION")
	private Long pdfMajorVersion;

	
	/** Column: PDF_MINOR_VERSION type NUMBER(20,0) null */
	@Column(name = "PDF_MINOR_VERSION")
	private Long pdfMinorVersion;

	
	/** Column: PDF_REPOSITORY_ID type NUMBER(20,0) null */
	@Column(name = "PDF_REPOSITORY_ID")
	private Long pdfRepositoryId;

	
	/** Column: PDF_FILE_PATH type VARCHAR2(255,0) null */
	@Column(name = "PDF_FILE_PATH")
	private String pdfFilePath;

	
	/** Column: PDF_TYPE_SIZE type VARCHAR2(255,0) null */
	@Column(name = "PDF_TYPE_SIZE")
	private String pdfTypeSize;

}
