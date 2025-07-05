package vn.com.unit.ep2p.log.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "M_LOG_DB")
public class LogDB {

    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_LOG_DB")
    private Long id;

    @Column(name = "API_ID")
    private Long apiID;

    @Column(name = "TOTAL_DB_ACTION_TIME")
    private Long tats;
    
    @Column(name = "STORE_NAME")
    private String storeName;
    
    @Column(name = "PARAM")
    private String param;
    
    @Column(name = "EXCEPTION")
    private String exception;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
}

