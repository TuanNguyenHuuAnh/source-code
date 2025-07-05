package vn.com.unit.ep2p.core.ds.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.constant.CoreConstant;
import vn.com.unit.db.entity.AbstractTracking;
import vn.com.unit.ep2p.core.ds.constant.DsTableConstant;

/**
 * @author TaiTM
 **/
@Table(name = "JCA_ACCOUNT_QUESTION")
@Getter
@Setter
public class DsAccountQuestion extends AbstractTracking {
    @Id
    @Column(name = "ID")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = CoreConstant.SEQ +  DsTableConstant.TABLE_DS_ACCOUNT_QUESTION)
    private Long id;
    
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "QUESTION_CODE")
    private String questionCode;

    @Column(name = "QUESTION")
    private String question;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "COMPANY_ID")
    private Long companyId;
}
