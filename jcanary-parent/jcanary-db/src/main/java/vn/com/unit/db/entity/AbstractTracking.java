package vn.com.unit.db.entity;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractTracking extends AbstractAuditTracking {

    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    @Column(name = "DELETED_ID")
    private Long deletedId  = 0L;
}
