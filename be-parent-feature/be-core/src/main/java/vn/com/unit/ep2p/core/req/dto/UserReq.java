package vn.com.unit.ep2p.core.req.dto;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;

@Table(name = "users")
public class UserReq {

	@Id
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SQ_TEST")
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	@JsonIgnore
	private String username;

	@Column(name = "password")
	@JsonIgnore
	private String password;

	@Column(name = "notes")
	private String notes;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}