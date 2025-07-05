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
public class AbstractAuditTracking extends AbstractCreateTracking {

	@JsonIgnore
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@JsonIgnore
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
}
