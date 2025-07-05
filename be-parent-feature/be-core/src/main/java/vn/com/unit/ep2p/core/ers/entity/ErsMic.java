package vn.com.unit.ep2p.core.ers.entity;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Table(name="ERS_MIC")
public class ErsMic {
	
	@Id
	@PrimaryKey(generationType =GenerationType.SEQUENCE, generator = "SEQ_ERS_MIC")
	@Column(name = "ID")
	private Long id;	
	
	@Column(name="UNIT_CODE")
    private String unitCode;
    
    @Column(name="UNIT_NAME")
    private String unitName;
    
    @Column(name="AREA_NAME")
    private String areaName;
    
    @Column(name="REGION_NAME")
    private String regionName;

    @Column(name="CREATED_BY")
    private String createdBy;
    
    @Column(name="CREATED_DATE")
    private Date createdDate;
    
    @Column(name="UPDATED_BY")
    private String updatedBy;
    
    @Column(name="UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name="DELETED_FLAG")
    private int deletedFlag;
    
    @Column(name="DELETED_DATE")
    private Date deletedDate;
    
    @Column(name="DELETED_BY")
    private String deletedBy;
    
	@Transient
	private Byte[] versionData;
	
}
