package vn.com.unit.core.entity;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Table(name = "JCA_AUTH")
public class JcaAuth {

    /** Column: ID type NUMBER(22,0) NULL */
    @Column(name = "TOKEN")
    private String token;
    
    @Column(name = "CREATE_DATE")
    private Date createDate;
}
