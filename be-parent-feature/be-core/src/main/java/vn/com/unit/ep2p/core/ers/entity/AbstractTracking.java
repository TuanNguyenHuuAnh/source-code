package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author PhatLT
 *
 */
@Getter
@Setter
public class AbstractTracking extends AbstractAuditTracking {
	
	@JsonIgnore
	@Column(name="DELETED_BY")
	private String deletedBy;
	
	@JsonIgnore
	@Column(name="DELETED_DATE")
	private Date deletedDate;
	
	@JsonIgnore
	@Column(name="DELETED_FLAG")
	private int deletedFlag;
}
