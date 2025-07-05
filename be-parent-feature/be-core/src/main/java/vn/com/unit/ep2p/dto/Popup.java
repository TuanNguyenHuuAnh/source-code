package vn.com.unit.ep2p.dto;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Table(name = "M_POPUP")
public class Popup {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = PrimaryKey.GenerationType.SEQUENCE, generator = "SEQ_M_POPUP")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TEMPLATE_CONTENT")
    private String templateContent;

    @Column(name = "COMPANY_ID")
    private BigDecimal companyId;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_ID")
    private Long createdId;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "UPDATED_ID")
    private Long updatedId;

    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    @Column(name = "DELETED_ID")
    private Long deletedId;
}
