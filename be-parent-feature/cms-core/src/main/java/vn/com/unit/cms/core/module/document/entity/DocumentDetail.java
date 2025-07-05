/*******************************************************************************
 * Class        ：Document
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.document.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.entity.AbstractTracking;

/**
 * Document
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Table(name = "m_document_detail")
@Getter
@Setter
public class DocumentDetail extends AbstractTracking {
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_DOCUMENT_DETAIL")
    private Long id;

    @Column(name = "m_document_id")
    private Long documentId;

    @Column(name = "version")
    private int version;

    @Column(name = "version_current")
    private boolean currentVersion;

    @Column(name = "physical_file_name")
    private String physicalFileName;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Double fileSize;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "share_token_hash")
    private byte[] tokenHash;

    @Column(name = "share_token_timestamp")
    private String tokenTimeStamp;
}
