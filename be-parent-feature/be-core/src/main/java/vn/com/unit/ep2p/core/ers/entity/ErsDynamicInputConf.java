package vn.com.unit.ep2p.core.ers.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name="ERS_DYNAMIC_INPUT_CONF")
public class ErsDynamicInputConf extends AbstractTracking{
    
    @Id
    @PrimaryKey(generationType =GenerationType.SEQUENCE, generator = "SEQ_ERS_DYNAMIC_INPUT_CONF")
    @Column(name = "ID")
    private Long id;    
    
    @Column(name="CHANNEL")
    private String channel;
    
    @Column(name="ID_INPUT_BOX")
    private Integer idInputBox;
    
    @Column(name="TITLE_INPUT_BOX")
    private String titleInputBox;
    
    @Column(name="WIDTH_CHAR")
    private Integer widthChar;
    
    @Column(name="TYPE_INPUT_BOX")
    private String typeInputBox;
    
    @Column(name="CONTENT_DEFAULT")
    private String contentDefault;
    
    @Column(name="STATUS_INPUT_BOX")
    private String statusInputBox;

}