package vn.com.unit.cms.core.module.notify.dto;

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
public class ObjectDataResNotify<T>  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected int totalMessageUnread;
	protected int totalData;
    protected List<T> datas;
}
