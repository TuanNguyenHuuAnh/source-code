package vn.com.unit.ep2p.dto;

import java.util.Date;

import jp.sf.amateras.mirage.annotation.Transient;

public class DelegationPendingTaskDto {

	private Long id;
	private String transactionNo;
	private String transactionType;
	private String statusName;
	private Date createdDate;

	@Transient
	private String url;

	@Transient
	private String nameTodo;

	@Transient
	private Integer rowNum;

	@Transient
	private String createdDateStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNameTodo() {
		return nameTodo;
	}

	public void setNameTodo(String nameTodo) {
		this.nameTodo = nameTodo;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

}
