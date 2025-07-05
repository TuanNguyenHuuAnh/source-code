/*******************************************************************************
 * Class        ：AbstractRest
 * Created date ：2020/12/04
 * Lasted date  ：2020/12/04
 * Author       ：taitt
 * Change log   ：2020/12/04：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
//import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.ep2p.core.ers.annotation.IesTableHeader;
import vn.com.unit.ep2p.ers.dto.ErsTableHeaderDto;

/**
 * AbstractRest
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class AbstractRest {

	@Autowired
	protected ErrorHandler errorHandler;

	@Autowired
	protected SuccessHandler successHandler;

	@Autowired
	protected ObjectMapper objectMapper;
	
    @Autowired
    private MessageSource messageSource;

	// 2021-04-18 LocLT
	public <T extends Object> List<ErsTableHeaderDto> getTableHeader(Class<T> t, String language) {
		List<ErsTableHeaderDto> rs = new ArrayList<>();

		List<Field> lstField = new ArrayList<>();

		lstField.addAll(Arrays.asList(t.getDeclaredFields()));
		lstField.addAll(Arrays.asList(t.getSuperclass().getDeclaredFields()));

		for (Field f : lstField) {
			IesTableHeader h = f.getAnnotation(IesTableHeader.class);
			if (null != h) {
				ErsTableHeaderDto th = new ErsTableHeaderDto();
				String title = h.value();
				try {
					title = messageSource.getMessage(title, null, title, new Locale(language));
				} catch (Exception e) {
				}
				th.setTitle(title);
				th.setDataIndex(f.getName());
				th.setWidth(h.width());
				th.setAlign(h.align());
				th.setFormat(h.format());

				rs.add(th);
			}
		}

		return rs;
	}
}
