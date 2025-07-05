/*******************************************************************************
 * Class        ：ObjectDataRes
 * Created date ：2020/11/30
 * Lasted date  ：2020/11/30
 * Author       ：taitt
 * Change log   ：2020/11/30：01-00 taitt create a new
 ******************************************************************************/
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
public class NewsEventResp<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7076000478980050515L;
	protected Object hotNews;
    protected List<T> eventNews;
}
