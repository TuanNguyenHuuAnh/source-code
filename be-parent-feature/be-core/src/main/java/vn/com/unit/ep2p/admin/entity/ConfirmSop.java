package vn.com.unit.ep2p.admin.entity;
import java.util.Date;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.Table;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Table(name="M_CONFIRM_SOP")
public class ConfirmSop {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_CONFIRM_SOP")
    private Long id;
    
    @Column(name = "USER_NAME")
    private String userName;
    
    @Column(name = "CONFIRM_TIME")
    private Date confirmTime;
}
