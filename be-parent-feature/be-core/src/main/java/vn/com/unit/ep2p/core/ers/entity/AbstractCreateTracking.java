package vn.com.unit.ep2p.core.ers.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Setter;

import lombok.Getter;
/**
 * 
 * @author PhatLT
 *
 */
@Getter
@Setter
public class AbstractCreateTracking {
	
	@JsonIgnore
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@JsonIgnore
	@Column(name="CREATED_DATE")
	private Date createdDate;
}
