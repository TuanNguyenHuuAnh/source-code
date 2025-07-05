package vn.com.unit.cms.core.module.news.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsHomeResp<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5581009939951489186L;
	protected Object hotNews;
	protected int totalData;
    protected List<T> datas;

}
