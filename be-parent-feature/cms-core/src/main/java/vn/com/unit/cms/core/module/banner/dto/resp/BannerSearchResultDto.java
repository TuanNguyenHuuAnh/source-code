package vn.com.unit.cms.core.module.banner.dto.resp;

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
public class BannerSearchResultDto<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7587658903090340680L;
	protected String animationSlider;
	protected String slideTime;
    protected List<T> datas;
}
