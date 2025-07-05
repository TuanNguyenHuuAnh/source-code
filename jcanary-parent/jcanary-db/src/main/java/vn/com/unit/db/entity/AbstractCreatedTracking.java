package vn.com.unit.db.entity;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractCreatedTracking {

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_ID")
    private Long createdId;

}
