package vn.com.unit.db.entity;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractAuditTracking extends AbstractCreatedTracking {

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "UPDATED_ID")
    private Long updatedId;
}
