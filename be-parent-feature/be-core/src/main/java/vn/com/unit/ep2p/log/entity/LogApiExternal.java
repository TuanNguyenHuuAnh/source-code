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
@Table(name = "M_LOG_API_EXTERNAL")
public class LogApiExternal {

    @Id
    @Column(name = "ID")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_LOG_API_EXTERNAL")
    private Long id;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "URL")
    private String url;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_DATE")
    private Date createdDate;
   
    @Column(name = "JSON_INPUT")
    private String jsonInput;
    
    @Column(name = "RESPONSE_JSON")
    private String responseJson;

    @Column(name = "TOTAL_ACTION_TIME")
    private Long tats;
               
}

