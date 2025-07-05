package vn.com.unit.cms.core.module.emulate.dto.resp;

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
public class EmulateRespData<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7423435525917170149L;
	protected int totalData;
	protected T focus;
    protected List<T> datas;
}
