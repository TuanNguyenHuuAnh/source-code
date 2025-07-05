package vn.com.unit.cms.core.module.favorite.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "M_MENU_FAVORITE")
@Getter
@Setter
public class Favorite {
	@Id
	@Column(name = "id")
	@PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_M_MENU_FAVORITE")
	private Long id;

	@Column(name = "agent_code")
	private String agentCode;
	
	@Column(name = "type")
	private String type;

	@Column(name = "title")
	private String title;
	
	@Column(name = "link")
	private String link;
	
	@Column(name = "named")
	private String named;
	
	@Column(name = "icon")
	private String icon;
	
	@Column(name = "DEFAULT_FLAG")
	private Integer defaultFlag;

	@Column(name = "FUNCTION_CODE")
	private String functionCode;
}
