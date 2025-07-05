package vn.com.unit.cms.core.module.favorite.dto;

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
public class ObjectDataResFavoriteByAgentCode<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	protected List<T> datas;

}
